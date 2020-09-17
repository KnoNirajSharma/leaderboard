package com.knoldus.leader_board.infrastructure

import java.sql.Connection

import com.knoldus.leader_board.{DatabaseConnection, GetReputation}
import com.typesafe.config.Config
import com.typesafe.scalalogging.LazyLogging
import scalikejdbc.{DB, DBSession, SQL}

class FetchAllTimeReputationImpl(config: Config) extends LazyLogging with FetchAllTimeReputation {
  implicit val connection: Connection = DatabaseConnection.connection(config)
  implicit val session: DBSession = DB.readOnlySession()

  /**
   * Fetching all time reputation of each knolder.
   *
   * @return List of get reputation of knolders.
   */
  override def fetchAllTimeReputation: List[GetReputation] = {
    logger.info("Fetching all time reputation of each knolder.")

    SQL(
      """SELECT knolder.id,
                knolder.full_name,
                all_time_reputation.score AS allTimeScore,
                all_time_reputation.rank AS allTimeRank
                from knolder
                INNER JOIN all_time_reputation
                ON knolder.id = all_time_reputation.knolder_id
         """.stripMargin)
      .map(rs => GetReputation(rs.int("id"), rs.string("full_name"),
        rs.int("allTimeScore"), rs.int("allTimeRank"))).list().apply()
  }
}
