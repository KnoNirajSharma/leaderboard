package com.knoldus.leader_board.infrastructure

import java.sql.Connection

import com.knoldus.leader_board.{DatabaseConnection, KnolderBlog}
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
  override def fetchKnoldersWithBlogs: List[KnolderBlog] = {
    logger.info("Fetching details of knolders with blogs.")
    SQL("SELECT blog.id, blog.wordpress_id FROM blog INNER JOIN knolder ON knolder.wordpress_id = blog.wordpress_id")
      .map(rs => KnolderBlog(rs.int("id"), rs.string("wordpress_id"))).list().apply()
  }
}
