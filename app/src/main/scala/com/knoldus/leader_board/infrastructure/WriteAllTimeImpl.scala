package com.knoldus.leader_board.infrastructure

import java.sql.Connection

import com.knoldus.leader_board.{BlogCount, DatabaseConnection}
import com.typesafe.scalalogging._
import scalikejdbc.{DB, DBSession, SQL}

class WriteAllTimeImpl(databaseConnection: DatabaseConnection) extends WriteAllTime with LazyLogging {
  implicit val connection: Connection = databaseConnection.connection
  implicit val session: DBSession = DB.autoCommitSession()

  /**
   * Stores blog count of knolder in all_time table.
   *
   * @param blogCount Number of blogs per Knolder.
   * @return Integer which displays the status of query execution.
   */
  override def insertAllTimeData(blogCount: BlogCount): Int = {
    logger.info("Querying all time table to insert blog count of each knolder.")
    SQL("INSERT INTO all_time(knolder_id, number_of_blogs) VALUES (?,?)")
      .bind(blogCount.knolderId, blogCount.numberOfBlogs).update().apply()
  }

  /**
   * Updates blog count of knolder in all_time table.
   *
   * @param blogCount Number of blogs per Knolder.
   * @return Integer which displays the status of query execution.
   */
  override def updateAllTimeData(blogCount: BlogCount): Int = {
    logger.info("Querying all time table to update blog count of each knolder.")
    SQL("UPDATE all_time SET number_of_blogs = ? WHERE knolder_id = ?")
      .bind(blogCount.numberOfBlogs, blogCount.knolderId).update().apply()
  }
}
