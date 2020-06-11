package com.knoldus.leader_board.infrastructure

import java.sql.Connection

import com.knoldus.leader_board.{DatabaseConnection, Reputation}
import com.typesafe.config.Config
import com.typesafe.scalalogging.LazyLogging
import scalikejdbc.{DB, DBSession, SQL}

class FetchReputationImpl(config: Config) extends FetchReputation with LazyLogging {
  implicit val connection: Connection = DatabaseConnection.connection(config)
  implicit val session: DBSession = DB.readOnlySession()

  /**
   * Fetching reputation of each knolder.
   *
   * @return List of reputation of knolders.
   */
  override def fetchReputation: List[Reputation] = {
    logger.info("Fetching reputation of each knolder.")
    SQL("SELECT knolder.id, knolder.full_name, all_time_reputation.score AS allTimeScore, all_time_reputation.rank " +
      "AS allTimeRank, quarterly_reputation.streak AS quarterlyStreak, monthly_reputation.score AS monthlyScore, " +
      "monthly_reputation.rank AS monthlyRank from knolder INNER JOIN all_time_reputation ON knolder.id = " +
      "all_time_reputation.knolder_id INNER JOIN monthly_reputation ON knolder.id = monthly_reputation.knolder_id " +
      "INNER JOIN quarterly_reputation ON knolder.id = quarterly_reputation.knolder_id WHERE knolder.active_status = " +
      "true ORDER BY monthly_reputation.score DESC")
      .map(rs => Reputation(rs.int("id"), rs.string("full_name"),
        rs.int("allTimeScore"), rs.int("allTimeRank"), rs.string("quarterlyStreak"),
        rs.int("monthlyScore"), rs.int("monthlyRank"))).list().apply()
  }
}
