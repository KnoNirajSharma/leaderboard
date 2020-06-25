package com.knoldus.leader_board.business

import java.sql.Timestamp
import java.text.{ParseException, SimpleDateFormat}

import com.knoldus.leader_board.Blog
import com.typesafe.config.Config
import com.typesafe.scalalogging._
import net.liftweb.json.{DefaultFormats, parse}

class BlogsImpl(URLResponse: URLResponse, config: Config) extends Blogs with LazyLogging {
  implicit val formats: DefaultFormats.type = DefaultFormats
  val formatOne = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
  val formatTwo = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
  val formatThree = new SimpleDateFormat("yyyy-MM-dd")

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
    val page = (totalNoOfPosts.toFloat / 100).ceil.toInt
    logger.info(s"Extracting blogs from $page pages of Wordpress API.")
    getAllBlogs(page)
  }

  /**
   * Extracts found meta data from wordpress API which specifies total number of posts available on wordpress API.
   *
   * @return Total number of posts to be fetched from wordpress API.
   */
  override def getTotalNoOfPosts: Int = {
    val blogsData = URLResponse.getResponse(config.getString("urlForTotalBlogs") + "?page=1")
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
        val unParsedBlogs = URLResponse.getResponse(config.getString("urlForAllBlogs") +
          s"?per_page=100&page=$currentPage&_embed=author")
        getBlogs(blogsList ::: getListOfBlogs(unParsedBlogs), currentPage + 1, lastPage)
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
  override def getListOfBlogs(unparsedBlogs: String): List[Blog] = {
    logger.info("Parsing JSON string of blogs information.")
    val parsedBlogs = parse(unparsedBlogs)
    val blogs = parsedBlogs.children map { parsedBlog =>
      val blogId = (parsedBlog \ "id").extract[Option[Int]]
      val wordpressId = (parsedBlog \ "_embedded" \ "author").children map { author =>
        (author \ "slug").extract[Option[String]]
      }
      val date = (parsedBlog \ "date").extract[String]
      val publishedOn = try {
        val formatDate = formatOne.parse(date)
        new Timestamp(formatDate.getTime)
      }
      catch {
        case _: ParseException => try {
          val formatDate = formatTwo.parse(date)
          new Timestamp(formatDate.getTime)
        }
        catch {
          case _: ParseException => val formatDate = formatThree.parse(date)
            new Timestamp(formatDate.getTime)
        }
      }
      val title = (parsedBlog \ "title" \ "rendered").extract[Option[String]]
      logger.info("Modelling blogs information from JSON format to case class object.")
      wordpressId.map(id => Blog(blogId, id, publishedOn, title))
    }
    blogs.flatten.filter(blog => blog.blogId.isDefined && blog.wordpressId.isDefined)
  }
}
