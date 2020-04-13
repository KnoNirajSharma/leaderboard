package com.knoldus.leader_board.store_blogs

import java.sql.SQLException

import com.knoldus.leader_board.{BlogAuthor, Constants, DatabaseConnection}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class BlogsAndAuthors(blogsDataFromAPI: BlogsDataFromAPI, databaseConnection: DatabaseConnection) {
  /**
   * Calculates total number of pages of wordpress API which needs to be hit.
   * Maintains a FLAG which specifies whether API is being hit for first time or not, i.e 0 specifies API being hit
   * for first time.
   *
   * @return BlogAuthor case class object which contains list of all blogs and knolders.
   */
  def getAllBlogsAndAuthors: Future[BlogAuthor] = {
    try {
      val totalNoOfPosts: Future[Int] = blogsDataFromAPI.getTotalNoOfPosts
      totalNoOfPosts.flatMap { totalNoOfPosts =>
        val totalPosts = totalNoOfPosts
        if (Constants.FLAG > 0) {
          val page = 1
          blogsDataFromAPI.getAllBlogs(page)
        }
        else {
          val page = (totalPosts.toFloat / 20).ceil.toInt
          blogsDataFromAPI.getAllBlogs(page)
        }
      }
    }
    catch {
      case ex: SQLException => throw new SQLException(ex)
      case ex: Exception => throw new Exception(ex)
    }
  }
}
