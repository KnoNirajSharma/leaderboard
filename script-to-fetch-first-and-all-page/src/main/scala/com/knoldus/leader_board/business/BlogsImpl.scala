package com.knoldus.leader_board.business

import java.sql.Timestamp
import java.time.{ZoneOffset, ZonedDateTime}

import com.knoldus.leader_board.Blog
import com.knoldus.leader_board.infrastructure.FetchData
import com.typesafe.config.Config
import com.typesafe.scalalogging._
import net.liftweb.json.{DefaultFormats, parse}

class BlogsImpl(fetchData: FetchData, URLResponse: URLResponse, config: Config) extends Blogs with LazyLogging {
  implicit val formats: DefaultFormats.type = DefaultFormats

  /**
   * Calls method to get total number of posts available on wordpress API.
   * Calculates total number of pages of wordpress API which needs to be hit.
   * Calls method to get all blogs present on specified number of pages of wordpress API.
   *
   * @return List of blogs.
   */
  override def getAllBlogsFromAPI: List[Blog] = {
    val totalNoOfPosts: Int = getTotalNoOfPosts
    logger.info("Retrieving total number of blogs available on Wordpress API.")
    val page = (totalNoOfPosts.toFloat / 20).ceil.toInt
    logger.info(s"Extracting blogs from $page pages of Wordpress API.")
    getAllBlogs(page)
  }

  /**
   * Extracts found meta data from wordpress API which specifies total number of posts available on wordpress API.
   *
   * @return Total number of posts to be fetched from wordpress API.
   */
  override def getTotalNoOfPosts: Int = {
    val blogsData = URLResponse.getResponse(config.getString("urlForAllBlogs") + "?page=1")
    val parsedBlogsData = parse(blogsData)
    (parsedBlogsData \ "found").extract[Int]
  }

  /**
   * Hits wordpress API page wise and unmarshalls the response.
   *
   * @param lastPage Specifies last page upto which wordpress API needs to be hit from first page.
   * @return List of blogs.
   */
  override def getAllBlogs(lastPage: Int): List[Blog] = {
    logger.info(s"Hitting Wordpress API to pull blog details in JSON format from $lastPage pages of the API.")

    @scala.annotation.tailrec
    def getBlogs(blogsList: List[Blog], currentPage: Int, lastPage: Int): List[Blog] = {
      if (currentPage > lastPage) {
        blogsList
      } else {
        val unParsedBlogs = URLResponse.getResponse(config.getString("urlForAllBlogs") + "?page=" + currentPage)
        getBlogs(blogsList ::: getListOfAllBlogs(unParsedBlogs), currentPage + 1, lastPage)
      }
    }

    getBlogs(List.empty, 1, lastPage)
  }

  /**
   * Parse blogs data from wordpress API.
   *
   * @param unparsedBlogs JSON string of blogs data fetched from wordpress API.
   * @return List of blogs.
   */
  override def getListOfAllBlogs(unparsedBlogs: String): List[Blog] = {
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
      logger.info("Modelling blogs information from JSON format to case class object.")
      Blog(blogId, wordpressId, publishedOn, title)
    }
    blogs.filter(blog => blog.blogId.isDefined && blog.wordpressId.isDefined)
  }

  /**
   * Calls method to get latest blogs present on first page of wordpress API.
   *
   * @return List of blogs.
   */
  override def getLatestBlogsFromAPI: List[Blog] = {
    logger.info("Latest blogs will be extracted from first page of Wordpress API.")
    val fetchMaxDate = fetchData.fetchMaxBlogPublicationDate.getOrElse("0000-00-00 00:00:00").toString
      .replace(' ', 'T')
    getListOfLatestBlogs(URLResponse.getResponse(config.getString("urlForLatestBlogs") +
      s"?per_page=100&after=$fetchMaxDate&_embed=author"))
  }

  /**
   * Parse blogs data from wordpress API.
   *
   * @param unparsedBlogs JSON string of blogs data fetched from wordpress API.
   * @return List of blogs.
   */
  override def getListOfLatestBlogs(unparsedBlogs: String): List[Blog] = {
    logger.info("Parsing JSON string of blogs information.")
    val parsedBlogs = parse(unparsedBlogs)
    val blogs = parsedBlogs.children map { parsedBlog =>
      val blogId = (parsedBlog \ "id").extract[Option[Int]]
      val wordpressId = (parsedBlog \ "_embedded" \ "author").children map { author =>
        (author \ "slug").extract[Option[String]]
      }
      val date = Timestamp.valueOf((parsedBlog \ "date").extract[String].replace('T', ' '))
      val title = (parsedBlog \ "title" \ "rendered").extract[Option[String]]
      logger.info("Modelling blogs information from JSON format to case class object.")
      wordpressId.map(id => Blog(blogId, id, date, title))
    }
    blogs.flatten.filter(blog => blog.blogId.isDefined && blog.wordpressId.isDefined)
  }
}
