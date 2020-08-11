package com.knoldus.leader_board.infrastructure

import java.sql.{Connection, Timestamp}

import com.knoldus.leader_board.DatabaseConnection
import com.typesafe.config.Config
import com.typesafe.scalalogging.LazyLogging
import scalikejdbc._

class FetchTechHubImpl(config: Config) extends FetchTechHub with LazyLogging {
  implicit val connection: Connection = DatabaseConnection.connection(config)
  implicit val session: DBSession = DB.readOnlySession()

  /**
   * Fetches maximum delivered date of techhub present in techhub table.
   *
   * @return Maximum delivered date of techhub wrapped in option.
   */
  override def getLastUpdatedDateForTechHub: Option[Timestamp] = {
    logger.info("Querying techhub table to fetch maximum delivered date of techhub.")
    SQL(
      """SELECT
      MAX(uploaded_on)
      FROM techhub""").map(rs => rs.timestamp(1)).single().apply()
  }
}
