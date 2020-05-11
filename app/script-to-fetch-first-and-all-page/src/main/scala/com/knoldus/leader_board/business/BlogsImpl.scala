package com.knoldus.leader_board.business

import java.sql.Timestamp
import java.time.{ZoneOffset, ZonedDateTime}

import com.knoldus.leader_board.Blog
import com.knoldus.leader_board.infrastructure.FetchData
import com.typesafe.config.Config
import com.typesafe.scalalogging._
import net.liftweb.json.{DefaultFormats, parse}
import org.apache.commons.io.IOUtils
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.HttpClientBuilder

class BlogsImpl(fetchData: FetchData, config: Config) extends Blogs with LazyLogging {
  implicit val formats: DefaultFormats.type = DefaultFormats

  /**
   * @param url takes string of url to request from that url
   * @return respaonse entity in form of string
   */
  override def getResponse(url: String): String = {
    val request = new HttpGet(url)
    val client = HttpClientBuilder.create().build()
    val response = client.execute(request)
    IOUtils.toString(response.getEntity.getContent)
  }

  /**
   * Calls method to get total number of posts available on wordpress API.
   * Calculates total number of pages of wordpress API which needs to be hit.
   * Calls method to get all blogs present on specified number of pages of wordpress API.
   *
   * @return List of blogs.
   */
  override def getAllPagesBlogsFromAPI: List[Blog] = {
    val totalNoOfPosts: Int = getTotalNoOfPosts
    logger.info("Retrieving total number of blogs are available on Wordpress API.")

    val page = (totalNoOfPosts.toFloat / 20).ceil.toInt
    logger.info(s"Blogs will be extracted from $page page of Wordpress API.")
    getAllBlogs(page)
  }

  /**
   * Extracts found meta data from wordpress API which specifies total number of posts available on wordpress API.
   *
   * @return Total number of posts to be fetched from wordpress API.
   */
  override def getTotalNoOfPosts: Int = {
    val blogsData = getResponse(config.getString("uri") + "?page=1")
    val parsedBlogsData = parse(blogsData)
    (parsedBlogsData \ "found").extract[Int]
  }

  /**
   * Calls method to get all blogs present on first page of wordpress API.
   *
   * @return List of blogs.
   */
  override def getFirstPageBlogsFromAPI: List[Blog] = {
    logger.info(s"Blogs will be extracted from 1 page of Wordpress API.")
    getListOfBlogs(getResponse(config.getString("uri") + "?page=1"))
  }

  /**
   * Hits wordpress API page wise and unmarshalls the response.
   *
   * @param lastPage Specifies last page upto which wordpress API needs to be hit from first page.
   * @return List of blogs.
   */
  override def getAllBlogs(lastPage: Int): List[Blog] = {
    logger.info(s"Wordpress API is being hit to pull blogs details from $lastPage pages of Wordpress API.")

    @scala.annotation.tailrec
    def getBlogs(blogsList: List[Blog], currentPage: Int, lastPage: Int): List[Blog] = {
      if (currentPage > lastPage) {
        blogsList
      } else {
        val unParsedBlogs = getResponse(config.getString("uri") + "?page=" + currentPage)
        getBlogs(blogsList ::: getListOfBlogs(unParsedBlogs), currentPage + 1, lastPage)
      }
    }
    getBlogs(List.empty, 1, lastPage)
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
    val fetchMaxDate = fetchData.fetchMaxBlogPublicationDate

    val parsedBlogs = parse(unparsedBlogs)
    val blogs = (parsedBlogs \ "posts").children map { parsedBlog =>
      val blogId = (parsedBlog \ "ID").extract[Option[Int]]
      val wordpressId = (parsedBlog \ "author" \ "login").extract[Option[String]]
      val date = (parsedBlog \ "date").extract[String]
      val odtInstanceAtOffset = ZonedDateTime.parse(date)
      val odtInstanceAtUTC = odtInstanceAtOffset.withZoneSameInstant(ZoneOffset.UTC)
      val publishedOn = Timestamp.from(odtInstanceAtUTC.toInstant)
      val title = (parsedBlog \ "title").extract[Option[String]]
      logger.info("Modelling blogs information from JSON format to case class object.")
      fetchMaxDate match {
        case Some(value) if publishedOn.compareTo(value) == 1 => Blog(blogId, wordpressId, publishedOn, title)
        case Some(value) if publishedOn.compareTo(value) != 1 => Blog(None, None, publishedOn, None)
        case None => Blog(blogId, wordpressId, publishedOn, title)
      }
    }
    blogs.filter(blog => blog.blogId.isDefined && blog.wordpressId.isDefined)
  }
}
