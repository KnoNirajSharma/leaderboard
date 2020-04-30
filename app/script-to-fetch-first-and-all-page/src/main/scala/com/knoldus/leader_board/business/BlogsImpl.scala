package com.knoldus.leader_board.business

import java.sql.Timestamp
import java.time.{Instant, ZoneOffset, ZonedDateTime}

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpRequest, HttpResponse}
import akka.http.scaladsl.settings.ClientConnectionSettings
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.stream.scaladsl.{Flow, Sink, Source}
import com.knoldus.leader_board.Blog
import com.knoldus.leader_board.infrastructure.FetchData
import com.typesafe.config.Config
import com.typesafe.scalalogging._
import net.liftweb.json.{DefaultFormats, parse}

import scala.concurrent.duration._
import scala.concurrent.{ExecutionContextExecutor, Future}

class BlogsImpl(fetchData: FetchData, config: Config)
               (implicit system: ActorSystem, executionContext: ExecutionContextExecutor)
  extends Blogs with LazyLogging {
  implicit val formats: DefaultFormats.type = DefaultFormats
  val connectionFlow: Flow[HttpRequest, HttpResponse, Future[Http.OutgoingConnection]] =
    Http().outgoingConnectionHttps(config.getString("host"),
      settings = ClientConnectionSettings(system).withIdleTimeout(5.minutes).withConnectingTimeout(3.minutes))
  /**
   * Calls method to get total number of posts available on wordpress API.
   * Calculates total number of pages of wordpress API which needs to be hit.
   * Calls method to get all blogs present on specified number of pages of wordpress API.
   *
   * @return List of blogs.
   */

  override def getAllPagesBlogsFromAPI: Future[List[Blog]] = {
    val totalNoOfPosts: Future[Int] = getTotalNoOfPosts
    logger.info("Retrieving total number of blogs are available on Wordpress API.")
    totalNoOfPosts.flatMap { totalNoOfPosts =>
      val page = (totalNoOfPosts.toFloat / 20).ceil.toInt
      logger.info(s"Blogs will be extracted from $page page of Wordpress API.")
      getAllBlogs(page)
    }
    }.recoverWith {
    case ex: Exception => Future.failed(new Exception("Service failed", ex))
  }

  /**
   * Extracts found meta data from wordpress API which specifies total number of posts available on wordpress API.
   *
   * @return Total number of posts to be fetched from wordpress API.
   */
  override def getTotalNoOfPosts: Future[Int] = {
    logger.info("Wordpress API is being hit to pull total number of blogs available on Wordpress API.")
    val response: Future[HttpResponse] = dispatchRequest(
      HttpRequest(uri = config.getString("uri") + "?page=1"))
    response.flatMap(res =>
      if (res.status.isSuccess()) {
        Unmarshal(res.entity).to[String].map { blogsData =>
          val parsedBlogsData = parse(blogsData)
          (parsedBlogsData \ "found").extract[Int]
        }
      }
      else {
        throw new RuntimeException(s"Service failed with status: ${res.status}")
      })
    }.recoverWith {
    case ex: Exception => Future.failed(new Exception("Service failed", ex))
  }

  /**
   * Calls method to get all blogs present on first page of wordpress API.
   *
   * @return List of blogs.
   */
  override def getFirstPageBlogsFromAPI: Future[List[Blog]] = {
    val page = 1
    logger.info(s"Blogs will be extracted from $page page of Wordpress API.")
    getAllBlogs(page)
  }.recoverWith {
    case ex: Exception => Future.failed(new Exception("Service failed", ex))
  }

  /**
   * Hits wordpress API page wise and unmarshalls the response.
   *
   * @param lastPage Specifies last page upto which wordpress API needs to be hit from first page.
   * @return List of blogs.
   */
  override def getAllBlogs(lastPage: Int): Future[List[Blog]] = {
    logger.info(s"Wordpress API is being hit to pull blogs details from $lastPage pages of Wordpress API.")

    @scala.annotation.tailrec
    def getBlogs(blogsList: Future[List[Blog]], currentPage: Int, lastPage: Int): Future[List[Blog]] = {
      if (currentPage > lastPage) {
        blogsList
      }
      else {
        val response: Future[HttpResponse] = dispatchRequest(
          HttpRequest(uri = config.getString("uri") + "?page=" + currentPage))
        getBlogs(blogsList.flatMap(blogs =>
          response.flatMap(res => Unmarshal(res.entity).to[String])
            .map(unparsedBlogs =>
              blogs ::: getListOfBlogs(unparsedBlogs))), currentPage + 1, lastPage)
      }
    }

    val blogsList = getBlogs(Future {
      List.empty
    }, 1, lastPage)
    blogsList
      .recoverWith {
        case _: Exception =>
          val blogsList = getBlogs(Future {
            List.empty
          }, 1, lastPage)
          blogsList
      }
  }

  /**
   * Parse blogs data from wordpress API.
   * Parse only those blogs which are not yet added in blog table.
   *
   * @param unparsedBlogs JSON string of blogs data fetched from wordpress API.
   * @return List of blogs.
   */
  override def getListOfBlogs(unparsedBlogs: String): List[Blog] = {
    logger.info("Parsing JSON string of blogs information.")
    val parsedBlogs = parse(unparsedBlogs)
    val blogs = (parsedBlogs \ "posts").children map { parsedBlog =>
      val blogId = (parsedBlog \ "ID").extract[Option[Int]]
      val wordpressId = (parsedBlog \ "author" \ "login").extract[Option[String]]
      val date = (parsedBlog \ "date").extract[String]
      val odtInstanceAtOffset = ZonedDateTime.parse(date)
      val odtInstanceAtUTC = odtInstanceAtOffset.withZoneSameInstant(ZoneOffset.UTC)
      val publishedOn = Timestamp.from(odtInstanceAtUTC.toInstant)
      val title = (parsedBlog \ "title").extract[Option[String]]
      val fetchMaxDate = fetchData.fetchMaxBlogPublicationDate
      val minimumTimeStamp = Instant.parse("0000-04-01T09:37:10Z")
      val parsedDate = Timestamp.from(minimumTimeStamp)
      logger.info("Modelling blogs information from JSON format to case class object.")
      fetchMaxDate match {
        case Some(value) if publishedOn.compareTo(value) == 1 => Blog(blogId, wordpressId, publishedOn, title)
        case Some(value) if publishedOn.compareTo(value) != 1 => Blog(None, None, parsedDate, None)
        case None if publishedOn.compareTo(parsedDate) == 1 => Blog(blogId, wordpressId, publishedOn, title)
        case None if publishedOn.compareTo(parsedDate) != 1 => Blog(None, None, parsedDate, None)
      }
    }
    blogs.filter(blog => blog.blogId.isDefined && blog.wordpressId.isDefined)
  }

  private def dispatchRequest(request: HttpRequest): Future[HttpResponse] = {
    Source.single(request)
      .via(connectionFlow)
      .runWith(Sink.head)
  }
}
