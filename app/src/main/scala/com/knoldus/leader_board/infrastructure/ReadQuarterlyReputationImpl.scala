package com.knoldus.leader_board.infrastructure

import java.sql.Connection

import com.knoldus.leader_board.DatabaseConnection
import com.typesafe.config.Config
import com.typesafe.scalalogging.LazyLogging
import scalikejdbc.{DB, DBSession, SQL}

class ReadQuarterlyReputationImpl(config: Config) extends LazyLogging with ReadQuarterlyReputation {
  implicit val connection: Connection = DatabaseConnection.connection(config)
  implicit val session: DBSession = DB.readOnlySession()

  /**
   * Fetches foreign key i.e. knolder id in quarterly_reputation table.
   *
   * @param knolderId Knolder id.
   * @return Knolder id wrapped in option.
   */
  override def fetchKnolderIdFromQuarterlyReputation(knolderId: Int): Option[Int] = {
    logger.info("Fetching knolder id of knolder from quarterly reputation table.")
    SQL("SELECT knolder_id FROM quarterly_reputation WHERE knolder_id = ?").bind(knolderId)
      .map(rs => rs.int("knolder_id")).single().apply()
  }
}
