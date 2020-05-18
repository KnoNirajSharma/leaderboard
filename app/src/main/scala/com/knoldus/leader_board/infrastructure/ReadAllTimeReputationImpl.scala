package com.knoldus.leader_board.infrastructure

import java.sql.Connection

import com.knoldus.leader_board.{DatabaseConnection, GetReputation}
import com.typesafe.config.Config
import com.typesafe.scalalogging._
import scalikejdbc.{DB, DBSession, SQL}

class ReadAllTimeReputationImpl(config: Config) extends ReadAllTimeReputation with LazyLogging {
  implicit val connection: Connection = DatabaseConnection.connection(config)
  implicit val session: DBSession = DB.readOnlySession()

  /**
   * Queries all_time_reputation table to get all time reputation data of each knolder.
   *
   * @return List of all time reputation data of each knolder.
   */
  override def fetchAllTimeReputationData: List[GetReputation] = {
    logger.info("Fetching all time reputation details of each knolder.")
    SQL("SELECT full_name, score, rank FROM all_time_reputation ORDER BY score DESC")
      .map(rs => GetReputation(rs.string("full_name"), rs.int("score"),
        rs.int("rank"))).list.apply()
  }

  /**
   * Fetches foreign key i.e. knolder id in all_time_reputation table.
   *
   * @param knolderId Knolder id.
   * @return Knolder id wrapped in option.
   */
  override def fetchKnolderIdFromAllTimeReputation(knolderId: Int): Option[Int] = {
    logger.info("Fetching knolder id of knolder from all time reputation table.")
    SQL("SELECT knolder_id FROM all_time_reputation WHERE knolder_id = ?").bind(knolderId)
      .map(rs => rs.int("knolder_id")).single().apply()
  }
}
