package com.knoldus.leader_board.infrastructure

import java.sql.{Connection, Timestamp}

import com.knoldus.leader_board.{DatabaseConnection, IndianTime, Reputation, ReputationCountAndReputation}
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
  override def fetchReputation: Option[ReputationCountAndReputation] = {
    val currentMonth = Timestamp.valueOf(IndianTime.currentTime
      .withDayOfMonth(1).toLocalDate.atStartOfDay())
    val nextMonth = Timestamp.valueOf(IndianTime.currentTime
      .withDayOfMonth(1).toLocalDate.plusMonths(1).atStartOfDay())
    logger.info("Fetching reputation of each knolder with monthly and total count of each type.")
    val reputation = SQL("SELECT knolder.id, knolder.full_name, all_time_reputation.score AS allTimeScore, all_time_reputation.rank " +
      "AS allTimeRank, quarterly_reputation.streak AS quarterlyStreak, monthly_reputation.score AS monthlyScore, " +
      "monthly_reputation.rank AS monthlyRank from knolder INNER JOIN all_time_reputation ON knolder.id = " +
      "all_time_reputation.knolder_id INNER JOIN monthly_reputation ON knolder.id = monthly_reputation.knolder_id " +
      "INNER JOIN quarterly_reputation ON knolder.id = quarterly_reputation.knolder_id WHERE knolder.active_status = " +
      "true ORDER BY monthly_reputation.score DESC")
      .map(rs => Reputation(rs.int("id"), rs.string("full_name"),
        rs.int("allTimeScore"), rs.int("allTimeRank"), rs.string("quarterlyStreak"),
        rs.int("monthlyScore"), rs.int("monthlyRank"))).list().apply()
    SQL("select (select count(*) from blog where published_on>= ? And published_on < ?) as monthly_blog_count," +
      "(select count(*) from knolx  where delivered_on>= ? And delivered_on < ?) as monthly_knolx_count," +
      "(select count(*) from blog) as total_blog_count,(select count(*) from knolx) as total_knolx_count;")
      .bind(currentMonth, nextMonth, currentMonth, nextMonth)
      .map(rs => ReputationCountAndReputation(rs.int("monthly_blog_count"), rs.int("monthly_knolx_count"),
        rs.int("total_blog_count"), rs.int("total_knolx_count"), reputation)).single().apply()

  }
}
