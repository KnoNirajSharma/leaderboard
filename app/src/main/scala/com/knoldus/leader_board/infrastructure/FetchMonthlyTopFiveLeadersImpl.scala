package com.knoldus.leader_board.infrastructure

import java.sql.Connection

import com.knoldus.leader_board.{DatabaseConnection, MonthYearWithTopFiveLeaders, MonthlyTopFiveLeaders}
import com.typesafe.config.Config
import com.typesafe.scalalogging.LazyLogging
import scalikejdbc.{DB, DBSession, SQL}


class FetchMonthlyTopFiveLeadersImpl(config: Config) extends FetchMonthlyTopFiveLeaders with LazyLogging {

  implicit val connection: Connection = DatabaseConnection.connection(config)
  implicit val session: DBSession = DB.readOnlySession()

  /**
   * Fetches top five leaders of every month.
   *
   * @return list of month and year with the top five leaders.
   */
  override def fetchMonthlyTopFiveLeaders: List[MonthYearWithTopFiveLeaders] = {
    logger.info("fetching top five leaders of every month from hall of fame table")

    val topFiveLeaders = SQL(
      """select knolder_id, knolder_name, monthly_score, all_time_score, monthly_rank, all_time_rank,month,year
           from halloffame
      """).map(rs => MonthlyTopFiveLeaders(rs.string("month"), rs.int("year"), rs.int("knolder_id"),
      rs.string("knolder_name"), rs.int("monthly_score"), rs.int("monthly_rank")
      , rs.int("all_time_score"), rs.int("all_time_rank"))).list().apply()
    val groupedValue = 5
    topFiveLeaders.grouped(groupedValue).map { listOfTopFiveLeaders =>
      listOfTopFiveLeaders.groupBy(monthlyLeaders => (monthlyLeaders.month, monthlyLeaders.year)).toList
    }.toList.flatten.map(monthAndYearWithTopLeaders =>
      MonthYearWithTopFiveLeaders(monthAndYearWithTopLeaders._1._1, monthAndYearWithTopLeaders._1._2, monthAndYearWithTopLeaders._2))

  }
}
