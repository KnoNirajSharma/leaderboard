package com.knoldus.leader_board.business

import java.net.URLEncoder
import java.sql.Timestamp

import com.knoldus.leader_board.Blog
import com.knoldus.leader_board.infrastructure.FetchBlogs
import com.typesafe.config.Config
import com.typesafe.scalalogging._
import net.liftweb.json.{DefaultFormats, parse}

class BlogsImpl(fetchData: FetchBlogs, URLResponse: URLResponse, config: Config) extends Blogs with LazyLogging {
  implicit val formats: DefaultFormats.type = DefaultFormats

  /**
   * Calls method to get latest blogs present on first page of wordpress API.
   *
   * @return List of blogs.
   */
  override def getLatestBlogsFromAPI: List[Blog] = {
    logger.info("Latest blogs will be extracted from first page of Wordpress API.")
    val fetchMaxDate = fetchData.fetchMaxBlogPublicationDate.getOrElse("0000-00-00 00:00:00").toString
      .replace(' ', 'T')
    getListOfLatestBlogs(URLResponse.getResponse(config.getString("urlForLatestBlogs"),fetchMaxDate))
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
