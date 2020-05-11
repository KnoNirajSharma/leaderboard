package com.knoldus.leader_board.infrastructure

import java.sql.Connection

import com.knoldus.leader_board.{BlogCount, DatabaseConnection}
import com.typesafe.config.Config
import com.typesafe.scalalogging._
import scalikejdbc.{DB, DBSession, SQL}

class ReadBlogImpl(config: Config) extends ReadBlog with LazyLogging {
  implicit val connection: Connection = DatabaseConnection.connection(config)
  implicit val session: DBSession = DB.readOnlySession()

  /**
   * Fetches Knolders who have written blogs entered in blog table.
   *
   * @return List of knolder with its wordpress id.
   */
  override def fetchKnoldersWithBlogs: List[BlogCount] = {
    logger.info("Fetching details of knolders with blogs.")
    SQL("SELECT knolder.id, knolder.wordpress_id, knolder.full_name, COUNT(blog.id) AS count FROM blog inner JOIN knolder ON " +
      "knolder.wordpress_id = blog.wordpress_id GROUP BY knolder.id, knolder.wordpress_id, knolder.full_name")
      .map(rs => BlogCount(rs.int("id"), rs.string("wordpress_id"),
        rs.string("full_name"), rs.int("count"))).list().apply()
  }
}
