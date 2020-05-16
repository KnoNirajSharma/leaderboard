package com.knoldus.leader_board.infrastructure

import java.sql.Connection

import com.knoldus.leader_board.{DatabaseConnection, GetAllTimeCount}
import com.typesafe.config.Config
import com.typesafe.scalalogging._
import scalikejdbc.{DB, DBSession, SQL}

class ReadAllTimeImpl(config: Config) extends ReadAllTime with LazyLogging {
  implicit val connection: Connection = DatabaseConnection.connection(config)
  implicit val session: DBSession = DB.readOnlySession()

  /**
   * Queries all_time table to get all time data of each knolder.
   *
   * @return List of all time data of each knolder.
   */
  override def fetchAllTimeData: List[GetAllTimeCount] = {
    logger.info("Fetching all time reputation details of each knolder.")
    SQL("SELECT knolder.id, full_name, number_of_blogs FROM all_time RIGHT JOIN knolder " +
      "ON all_time.knolder_id = knolder.id")
      .map(rs => GetAllTimeCount(rs.int("id"), rs.string("full_name"),
        rs.intOpt("number_of_blogs"))).list.apply()
  }

  /**
   * Fetches foreign key i.e. knolder id in all_time table.
   *
   * @param knolderId Knolder id.
   * @return Knolder id wrapped in option.
   */
  override def fetchKnolderIdFromAllTime(knolderId: Int): Option[Int] = {
    SQL("SELECT knolder_id FROM all_time WHERE knolder_id = ?").bind(knolderId)
      .map(rs => rs.int("knolder_id")).single().apply()
  }
}
