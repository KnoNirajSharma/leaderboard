package com.knoldus.leader_board.infrastructure

import java.sql.Connection

import com.knoldus.leader_board.{Blog, DatabaseConnection}
import com.typesafe.config.Config
import com.typesafe.scalalogging.LazyLogging
import scalikejdbc._


class StoreDataImpl(config: Config) extends StoreData with LazyLogging {
  implicit val connection: Connection = DatabaseConnection.connection(config)
  implicit val session: DBSession = DB.autoCommitSession()

  /**
   * Stores list of blogs in blog table.
   *
   * @param listOfBlogs List of blogs.
   * @return List of Integer which displays the status of query execution.
   */
  override def insertBlog(listOfBlogs: List[Blog]): List[Int] = {
    logger.info("Querying blog table to insert blog details.")
    listOfBlogs.map { blog =>
      SQL("INSERT INTO blog(id , wordpress_id, published_on, title)" +
        " SELECT ?, ?, ?, ? WHERE EXISTS (SELECT id FROM knolder WHERE wordpress_id = ?)")
        .bind(blog.blogId, blog.wordpressId, blog.publishedOn, blog.title, blog.wordpressId)
        .update().apply()
    }
  }
}
