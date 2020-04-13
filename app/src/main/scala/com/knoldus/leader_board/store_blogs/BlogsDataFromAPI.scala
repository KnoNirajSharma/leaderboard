package com.knoldus.leader_board.store_blogs

import java.sql.{SQLException, Timestamp}
import java.time.{Instant, ZoneOffset, ZonedDateTime}

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpRequest, HttpResponse}
import akka.http.scaladsl.settings.ClientConnectionSettings
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Flow, Sink, Source}
import com.knoldus.leader_board.{Author, Blog, BlogAuthor, DatabaseConnection}
import com.typesafe.config.ConfigFactory
import net.liftweb.json.{DefaultFormats, parse}
import scala.concurrent.duration._

import scala.concurrent.{ExecutionContextExecutor, Future}

class BlogsDataFromAPI(databaseConnection: DatabaseConnection) {
  implicit val formats: DefaultFormats.type = DefaultFormats
  implicit val system: ActorSystem = ActorSystem("myactor", ConfigFactory.load.getConfig("akka.http"))
  implicit val materializer: ActorMaterializer = ActorMaterializer()
  implicit val executionContext: ExecutionContextExecutor = system.dispatcher
  val connectionFlow: Flow[HttpRequest, HttpResponse, Future[Http.OutgoingConnection]] =
    Http().outgoingConnectionHttps("public-api.wordpress.com",
      settings = ClientConnectionSettings(system.settings.config).withIdleTimeout(5.minutes))

  /**
   * Extracts found meta data from wordpress API, which specifies total number of posts available on wordpress API.
   *
   * @return Total number of posts to be fetched from wordpress API.
   */
  def getTotalNoOfPosts: Future[Int] = {
    val response: Future[HttpResponse] = dispatchRequest(
      HttpRequest(uri = "/rest/v1.1/sites/24082517/posts?page=1"))
    response.flatMap(res =>
      if (res.status.isSuccess()) {
        Unmarshal(res.entity).to[String].map { count =>
          val parsedCount = parse(count)
          val totalPosts = (parsedCount \ "found").extract[Int]
          totalPosts
        }
      } else {
        val ex = new RuntimeException(s"Service failed with status: ${res.status}")
        throw ex
      }).recoverWith {
      case ex: Exception => Future.failed(new Exception("Service failed", ex))
    }
  }.recoverWith {
    case ex: Exception => Future.failed(new Exception("Service failed", ex))
  }

  def dispatchRequest(request: HttpRequest): Future[HttpResponse] = {
    Source.single(request)
      .via(connectionFlow)
      .runWith(Sink.head)
  }.recoverWith {
    case ex: Exception => Future.failed(new Exception("Service failed", ex))
  }

  /**
   * Hits wordpress API page wise and unmarshalls the response.
   *
   * @param lastPage Specifies end page number upto which wordpress API needs to be hit from first page.
   * @return BlogAuthor case class object which contains list of all blogs and knolders.
   */
  def getAllBlogs(lastPage: Int): Future[BlogAuthor] = {
    @scala.annotation.tailrec
    def getBlogs(blogsAndAuthorsList: Future[BlogAuthor], currentPage: Int, end: Int): Future[BlogAuthor] = {
      if (currentPage > end) {
        blogsAndAuthorsList
      } else {
        val response: Future[HttpResponse] = dispatchRequest(
          HttpRequest(uri = "/rest/v1.1/sites/24082517/posts?page=" + currentPage))
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
  def getListOfBlogs(blogs: String): List[Blog] = {
    try {
      val connection = databaseConnection.connection
      val parsedBlogs = parse(blogs)
      val listOfBlogs = (parsedBlogs \ "posts").children map { blog =>
        val blogId = (blog \ "ID").extract[Option[Int]]
        val authorLogin = (blog \ "author" \ "login").extract[Option[String]]
        val date = (blog \ "date").extract[String]
        val odtInstanceAtOffset = ZonedDateTime.parse(date)
        val odtInstanceAtUTC = odtInstanceAtOffset.withZoneSameInstant(ZoneOffset.UTC)
        val publishedOn = Timestamp.from(odtInstanceAtUTC.toInstant)
        val title = (blog \ "title").extract[Option[String]]
        val fetchMaxDate = connection.createStatement()
          .executeQuery(" SELECT MAX(published_on) FROM blog")
        fetchMaxDate.next()
        fetchMaxDate.getTimestamp(1)
        val minimumTime = Instant.parse("0000-04-01T09:37:10Z")
        val parsedDate = Timestamp.from(minimumTime)
        if (fetchMaxDate.wasNull()) {
          if (publishedOn.compareTo(parsedDate) == 1) {
            Blog(blogId, authorLogin, publishedOn, title)
          }
          else {
            Blog(None, None, parsedDate, None)
          }
        }
        else {
          val date = fetchMaxDate.getTimestamp(1)
          if (publishedOn.compareTo(date) == 1) {
            Blog(blogId, authorLogin, publishedOn, title)
          }
          else {
            Blog(None, None, parsedDate, None)
          }
        }
      }
      listOfBlogs.filter(blog => blog.blogId.isDefined && blog.authorLogin.isDefined)
    }
    catch {
      case ex: SQLException => throw new SQLException(ex)
      case ex: Exception => throw new Exception(ex)
    }
  }

  /**
   * Extracts knolders data from wordpress API.
   * Extracts only those knolders which are not yet added in knolder table.
   *
   * @param blogs JSON string of blogs data fetched from wordpress API.
   * @return List of Author case class objects, which specifies name of knolder, wordpress id of knolder.
   */
  def getListOfAuthors(blogs: String): List[Author] = {
    try {
      val connection = databaseConnection.connection
      val parsedBlogs = parse(blogs)
      val listOfAuthors = (parsedBlogs \ "posts").children map { blog =>
        val authorName = (blog \ "author" \ "name").extract[Option[String]]
        val authorLogin = (blog \ "author" \ "login").extract[Option[String]]
        val date = (blog \ "date").extract[String]
        val odtInstanceAtOffset = ZonedDateTime.parse(date)
        val odtInstanceAtUTC = odtInstanceAtOffset.withZoneSameInstant(ZoneOffset.UTC)
        val publishedOn = Timestamp.from(odtInstanceAtUTC.toInstant)
        val fetchMaxDate = connection.createStatement()
          .executeQuery(" SELECT MAX(published_on) FROM blog")
        fetchMaxDate.next()
        fetchMaxDate.getTimestamp(1)
        if (fetchMaxDate.wasNull()) {
          val minimumTime = Instant.parse("0000-04-01T09:37:10Z")
          val parsedDate = Timestamp.from(minimumTime)
          if (publishedOn.after(parsedDate)) {
            Author(authorName, authorLogin)
          }
          else {
            Author(None, None)
          }
        }
        else {
          val date = fetchMaxDate.getTimestamp(1)
          if (publishedOn.compareTo(date) == 1) {
            Author(authorName, authorLogin)
          }
          else {
            Author(None, None)
          }
        }
      }
      listOfAuthors.distinct.filter(author => author.authorLogin.isDefined)
    }
    catch {
      case ex: SQLException => throw new SQLException(ex)
      case ex: Exception => throw new Exception(ex)
    }
    finally {
      databaseConnection.connection.close()
    }
  }
}
