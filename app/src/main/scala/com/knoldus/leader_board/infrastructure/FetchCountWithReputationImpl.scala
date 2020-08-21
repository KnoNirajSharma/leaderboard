package com.knoldus.leader_board.infrastructure

import java.sql.{Connection, Timestamp}

import com.knoldus.leader_board.{DatabaseConnection, IndianTime, ReputationWithCount}
import com.typesafe.config.Config
import com.typesafe.scalalogging.LazyLogging
import scalikejdbc.{DB, DBSession, SQL}

class FetchCountWithReputationImpl(config: Config, fetchReputation: FetchReputation) extends FetchCountWithReputation with LazyLogging {

  implicit val connection: Connection = DatabaseConnection.connection(config)
  implicit val session: DBSession = DB.readOnlySession()

  /**
   * Fetch reputation of knolders with all time and monthly count of each type of contribution.
   *
   * @return reputation count of each type with reputation of each knolder.
   */


  override def allTimeAndMonthlyContributionCountWithReputation: Option[ReputationWithCount] = {
    logger.info("Fetching reputation of each knolder with monthly and total count of each type.")
    val currentMonth = Timestamp.valueOf(IndianTime.currentTime
      .withDayOfMonth(1).toLocalDate.atStartOfDay())
    val nextMonth = Timestamp.valueOf(IndianTime.currentTime
      .withDayOfMonth(1).toLocalDate.plusMonths(1).atStartOfDay())
    SQL(
      """select
      (select count(*)
      from blog
      where published_on>= ? And published_on < ?) as monthly_blog_count,
      (select count(*)
      from knolx
      where knolx.delivered_on>= ? And knolx.delivered_on < ?) as monthly_knolx_count,
      (select count(*)
      from webinar
      where webinar.delivered_on>= ? And webinar.delivered_on < ?) as monthly_webinar_count,
      (select count(*)
      from techhub
      where uploaded_on>= ? And uploaded_on < ?) as monthly_techhub_count,
      (select count(*)
      from oscontribution
      where contributed_on>= ? And contributed_on < ?) as monthly_oscontribution_count,
      (select count(*)
      from blog) as total_blog_count,
      (select count(*) from
      webinar) as total_webinar_count,
      (select count(*)
      from knolx) as total_knolx_count,
      (select count(*)
      from techhub) as total_techhub_count,
      (select count(*)
      from oscontribution) as total_oscontribution_count;""")
      .bind(currentMonth, nextMonth, currentMonth, nextMonth, currentMonth, nextMonth, currentMonth, nextMonth, currentMonth, nextMonth)
      .map(rs => ReputationWithCount(rs.int("monthly_blog_count"), rs.int("monthly_knolx_count"),
        rs.int("monthly_webinar_count"), rs.int("monthly_techhub_count"), rs.int("monthly_oscontribution_count"),
        rs.int("total_blog_count"), rs.int("total_knolx_count"), rs.int("total_webinar_count"),
        rs.int("total_techhub_count"), rs.int("total_oscontribution_count"), fetchReputation.fetchReputation)).single().apply()
  }
}
