package com.knoldus.leader_board.infrastructure

import java.sql.Connection

import com.knoldus.leader_board._
import com.typesafe.scalalogging._
import scalikejdbc._

class FetchDataImpl(databaseConnection: DatabaseConnection) extends FetchData with LazyLogging {
  implicit val connection: Connection = databaseConnection.connection
  implicit val session: DBSession = DB.readOnlySession()

  /**
   * Fetches foreign key i.e. knolder id in all_time table.
   *
   * @param knolderId Knolder id wrapped in option.
   * @return Knolder id wrapped in option.
   */
  override def fetchKnolderIdFromAllTime(knolderId: Int): Option[Int] = {
    logger.info("Querying all time table to get knolder id to check whether a knolder exist in all time table or not.")
    SQL("SELECT knolder_id FROM all_time WHERE knolder_id = ?").bind(knolderId)
      .map(rs => rs.int("knolder_id")).single().apply()
  }

  /**
   * Queries all_time table to get all time data of each knolder.
   *
   * @return List of all time data of each knolder.
   */
  override def fetchAllTimeData: List[GetAllTime] = {
    logger.info("Querying all time table joined with knolder table to get blog count and details of each knolder.")
    SQL("SELECT knolder_id, full_name, number_of_blogs FROM all_time RIGHT JOIN knolder " +
      "ON all_time.knolder_id = knolder.id")
      .map(rs => GetAllTime(rs.string("full_name"), rs.intOpt("number_of_blogs"))).list.apply()
  }

  /**
   * Queries knolder table to get details of each Knolder.
   *
   * @return List of knolders.
   */
  override def fetchKnolders: List[Knolder] = {
    logger.info("Querying knolder table to get details of each knolder.")
    SQL("SELECT * FROM knolder").map(rs => Knolder(rs.int("id"), rs.string("full_name"),
      rs.string("wordpress_id"), rs.string("email_id"))).list().apply()
  }

  /**
   * Fetches Knolders who have written blogs entered in blog table.
   *
   * @return List of knolder with its wordpress id.
   */
  override def fetchKnoldersWithBlogs: List[KnolderBlog] = {
    logger.info("Querying blog table joined with knolder table to get details of blogs of each knolder.")
    SQL("SELECT blog.id, blog.wordpress_id FROM blog INNER JOIN knolder ON knolder.wordpress_id = blog.wordpress_id")
      .map(rs => KnolderBlog(rs.int("id"), rs.string("wordpress_id"))).list().apply()
  }
}
