package com.knoldus.leader_board.business

import java.sql.Timestamp
import java.time.{Instant, ZoneOffset, ZonedDateTime}

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpRequest, HttpResponse}
import akka.http.scaladsl.settings.ClientConnectionSettings
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.stream.SystemMaterializer
import akka.stream.scaladsl.{Flow, Sink, Source}
import com.knoldus.leader_board.infrastructure.FetchData
import com.knoldus.leader_board.{Author, Blog, BlogAuthor}
import com.typesafe.config.Config
import net.liftweb.json.{DefaultFormats, parse}

import scala.concurrent.duration._
import scala.concurrent.{ExecutionContextExecutor, Future}

class BlogsImpl(fetchData: FetchData, config: Config)(implicit system: ActorSystem,
                                                      materializer: SystemMaterializer,
                                                      executionContext: ExecutionContextExecutor) extends Blogs {
  implicit val formats: DefaultFormats.type = DefaultFormats
  val connectionFlow: Flow[HttpRequest, HttpResponse, Future[Http.OutgoingConnection]] =
    Http().outgoingConnectionHttps(config.getString("host"),
      settings = ClientConnectionSettings(system).withIdleTimeout(5.minutes))

  /**
   * Calculates total number of pages of wordpress API which needs to be hit.
   * Maintains a FLAG which specifies whether API is being hit for first time or not, i.e 0 specifies API being hit
   * for first time.
   *
   * @return BlogAuthor case class object which contains list of all blogs and knolders.
   */
  override def getAllBlogsAndAuthors: Future[BlogAuthor] = {
    val totalNoOfPosts: Future[Int] = getTotalNoOfPosts
    totalNoOfPosts.flatMap { totalNoOfPosts =>
      val totalPosts = totalNoOfPosts
      if (config.getInt("flag") > 0) {
        val page = 1
        getAllBlogs(page)
      }
      else {
        val page = (totalPosts.toFloat / 20).ceil.toInt
        getAllBlogs(page)
      }
    }
  }.recoverWith {
    case ex: Exception => Future.failed(new Exception("Service failed", ex))
  }

  /**
   * Extracts found meta data from wordpress API, which specifies total number of posts available on wordpress API.
   *
   * @return Total number of posts to be fetched from wordpress API.
   */
  override def getTotalNoOfPosts: Future[Int] = {
    val response: Future[HttpResponse] = dispatchRequest(
      HttpRequest(uri = config.getString("uri") + "?page=1"))
    response.flatMap(res =>
      if (res.status.isSuccess()) {
        Unmarshal(res.entity).to[String].map { count =>
          val parsedCount = parse(count)
          val totalPosts = (parsedCount \ "found").extract[Int]
          totalPosts
        }
      } else {
        throw new RuntimeException(s"Service failed with status: ${res.status}")
      })
  }.recoverWith {
    case ex: Exception => Future.failed(new Exception("Service failed", ex))
  }

  private def dispatchRequest(request: HttpRequest): Future[HttpResponse] = {
    Source.single(request)
      .via(connectionFlow)
      .runWith(Sink.head)
  }

  /**
   * Hits wordpress API page wise and unmarshalls the response.
   *
   * @param lastPage Specifies end page number upto which wordpress API needs to be hit from first page.
   * @return BlogAuthor case class object which contains list of all blogs and knolders.
   */
  override def getAllBlogs(lastPage: Int): Future[BlogAuthor] = {
    @scala.annotation.tailrec
    def getBlogs(blogsAndAuthorsList: Future[BlogAuthor], currentPage: Int, end: Int): Future[BlogAuthor] = {
      if (currentPage > end) {
        blogsAndAuthorsList
      } else {
        val response: Future[HttpResponse] = dispatchRequest(
          HttpRequest(uri = config.getString("uri") + "?page=" + currentPage))
        getBlogs(blogsAndAuthorsList.flatMap(blogsAndAuthorsList =>
          response.flatMap(res => Unmarshal(res.entity).to[String])
            .map(blogs =>
              BlogAuthor(blogsAndAuthorsList.blogs ::: getListOfBlogs(blogs),
                (blogsAndAuthorsList.authors ::: getListOfAuthors(blogs)).distinct))), currentPage + 1, end)
      }
    }

    val blogsAndAuthorsList = getBlogs(Future {
      BlogAuthor(List.empty, List.empty)
    }, 1, lastPage)
    blogsAndAuthorsList
  }.recoverWith {
    case ex: Exception => Future.failed(new Exception("Service failed", ex))
  }

  /**
   * Extracts blogs data from wordpress API.
   * Extracts only those blogs which are not yet added in blog table.
   *
   * @param blogs JSON string of blogs data fetched from wordpress API.
   * @return List of Blog case class objects, which specifies blog id, wordpress id of knolder, date of blog,
   *         title of blog.
   */
  override def getListOfBlogs(blogs: String): List[Blog] = {
    val parsedBlogs = parse(blogs)
    val listOfBlogs = (parsedBlogs \ "posts").children map { blog =>
      val blogId = (blog \ "ID").extract[Option[Int]]
      val authorLogin = (blog \ "author" \ "login").extract[Option[String]]
      val date = (blog \ "date").extract[String]
      val odtInstanceAtOffset = ZonedDateTime.parse(date)
      val odtInstanceAtUTC = odtInstanceAtOffset.withZoneSameInstant(ZoneOffset.UTC)
      val publishedOn = Timestamp.from(odtInstanceAtUTC.toInstant)
      val title = (blog \ "title").extract[Option[String]]
      val fetchMaxDate = fetchData.fetchMaxBlogPublicationDate
      val minimumTime = Instant.parse("0000-04-01T09:37:10Z")
      val parsedDate = Timestamp.from(minimumTime)
      fetchMaxDate match {
        case Some(value) if publishedOn.compareTo(value) == 1 => Blog(blogId, authorLogin, publishedOn, title)
        case Some(value) if publishedOn.compareTo(value) != 1 => Blog(None, None, parsedDate, None)
        case None if publishedOn.compareTo(parsedDate) == 1 => Blog(blogId, authorLogin, publishedOn, title)
        case None if publishedOn.compareTo(parsedDate) != 1 => Blog(None, None, parsedDate, None)
      }
    }
    listOfBlogs.filter(blog => blog.blogId.isDefined && blog.authorLogin.isDefined)
  }

  /**
   * Extracts knolders data from wordpress API.
   * Extracts only those knolders which are not yet added in knolder table.
   *
   * @param blogs JSON string of blogs data fetched from wordpress API.
   * @return List of Author case class objects, which specifies name of knolder, wordpress id of knolder.
   */
  override def getListOfAuthors(blogs: String): List[Author] = {
    val parsedBlogs = parse(blogs)
    val listOfAuthors = (parsedBlogs \ "posts").children map { blog =>
      val authorName = (blog \ "author" \ "name").extract[Option[String]]
      val authorLogin = (blog \ "author" \ "login").extract[Option[String]]
      val date = (blog \ "date").extract[String]
      val odtInstanceAtOffset = ZonedDateTime.parse(date)
      val odtInstanceAtUTC = odtInstanceAtOffset.withZoneSameInstant(ZoneOffset.UTC)
      val publishedOn = Timestamp.from(odtInstanceAtUTC.toInstant)
      val fetchMaxDate = fetchData.fetchMaxBlogPublicationDate
      val minimumTime = Instant.parse("0000-04-01T09:37:10Z")
      val parsedDate = Timestamp.from(minimumTime)
      fetchMaxDate match {
        case Some(value) if publishedOn.compareTo(value) == 1 => Author(authorName, authorLogin)
        case Some(value) if publishedOn.compareTo(value) != 1 => Author(None, None)
        case None if publishedOn.compareTo(parsedDate) == 1 => Author(authorName, authorLogin)
        case None if publishedOn.compareTo(parsedDate) != 1 => Author(None, None)
      }
    }
    listOfAuthors.distinct.filter(author => author.authorLogin.isDefined)
  }
}
