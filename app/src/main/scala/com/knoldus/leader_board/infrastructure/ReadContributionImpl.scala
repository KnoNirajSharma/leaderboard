package com.knoldus.leader_board.infrastructure

import java.sql.{Connection, Timestamp}

import com.knoldus.leader_board.{DatabaseConnection, GetCount, IndianTime}
import com.typesafe.config.Config
import com.typesafe.scalalogging._
import scalikejdbc.{DB, DBSession, SQL}

class ReadContributionImpl(config: Config) extends ReadContribution with LazyLogging {
  implicit val connection: Connection = DatabaseConnection.connection(config)
  implicit val session: DBSession = DB.readOnlySession()

  /**
   * Fetches number of contributions of each knolder.
   *
   * @return List of all time data of each knolder.
   */
  override def fetchKnoldersWithContributions: List[GetCount] = {
    logger.info("Fetching details of knolders with contributions.")
    SQL("SELECT knolder.id, knolder.full_name, COUNT(DISTINCT blog.id) AS blog_count, COUNT(DISTINCT knolx.id) AS " +
      "knolx_count FROM knolder LEFT JOIN blog ON knolder.wordpress_id = blog.wordpress_id LEFT JOIN knolx ON " +
      "knolder.email_id = knolx.email_id WHERE knolder.active_status = true GROUP BY knolder.id, knolder.wordpress_id, " +
      "knolder.email_id, knolder.full_name")
      .map(rs => GetCount(rs.int("id"), rs.string("full_name"),
        rs.int("blog_count"), rs.int("knolx_count"))).list().apply()
  }

  /**
   * Fetches number of contributions of each knolder in current month.
   *
   * @return List of monthly data of each knolder.
   */
  override def fetchKnoldersWithMonthlyContributions: List[GetCount] = {
    logger.info("Fetching details of knolders with contributions of current month.")
    val currentMonth = Timestamp.valueOf(IndianTime.currentTime
      .withDayOfMonth(1).toLocalDate.atStartOfDay())
    val nextMonth = Timestamp.valueOf(IndianTime.currentTime
      .withDayOfMonth(1).toLocalDate.plusMonths(1).atStartOfDay())
    SQL("SELECT knolder.id, knolder.full_name, COUNT(DISTINCT blog.id) AS blog_count, COUNT(DISTINCT knolx.id) AS " +
      "knolx_count FROM knolder LEFT JOIN blog ON knolder.wordpress_id = blog.wordpress_id AND published_on >= ? AND " +
      "published_on < ? LEFT JOIN knolx ON knolder.email_id = knolx.email_id AND delivered_on >= ? AND delivered_on < ? " +
      "WHERE knolder.active_status = true GROUP BY knolder.id, knolder.wordpress_id, knolder.email_id, knolder.full_name")
      .bind(currentMonth, nextMonth, currentMonth, nextMonth)
      .map(rs => GetCount(rs.int("id"), rs.string("full_name"),
        rs.int("blog_count"), rs.int("knolx_count"))).list.apply()
  }

  /**
   * Fetches number of contributions of each knolder in first month of quarter.
   *
   * @return List of quarterly data of each knolder.
   */
  override def fetchKnoldersWithQuarterFirstMonthContributions: List[GetCount] = {
    logger.info("Fetching details of knolders with contributions of first month of quarter.")
    val firstMonth = Timestamp.valueOf(IndianTime.currentTime
      .withDayOfMonth(1).toLocalDate.minusMonths(3).atStartOfDay())
    val nextMonth = Timestamp.valueOf(IndianTime.currentTime
      .withDayOfMonth(1).toLocalDate.minusMonths(2).atStartOfDay())
    SQL("SELECT knolder.id, knolder.full_name, COUNT(DISTINCT blog.id) AS blog_count, COUNT(DISTINCT knolx.id) AS " +
      "knolx_count FROM knolder LEFT JOIN blog ON knolder.wordpress_id = blog.wordpress_id AND published_on >= ? " +
      "AND published_on < ? LEFT JOIN knolx ON knolder.email_id = knolx.email_id AND delivered_on >= ? AND delivered_on " +
      "< ? WHERE knolder.active_status = true GROUP BY knolder.id, knolder.wordpress_id, knolder.email_id, " +
      "knolder.full_name")
      .bind(firstMonth, nextMonth, firstMonth, nextMonth)
      .map(rs => GetCount(rs.int("id"), rs.string("full_name"),
        rs.int("blog_count"), rs.int("knolx_count"))).list.apply()
  }

  /**
   * Fetches number of contributions of each knolder in second month of quarter from blog table.
   *
   * @return List of quarterly data of each knolder.
   */
  override def fetchKnoldersWithQuarterSecondMonthContributions: List[GetCount] = {
    logger.info("Fetching details of knolders with contributions of second month of quarter.")
    val secondMonth = Timestamp.valueOf(IndianTime.currentTime
      .withDayOfMonth(1).toLocalDate.minusMonths(2).atStartOfDay())
    val nextMonth = Timestamp.valueOf(IndianTime.currentTime
      .withDayOfMonth(1).toLocalDate.minusMonths(1).atStartOfDay())
    SQL("SELECT knolder.id, knolder.full_name, COUNT(DISTINCT blog.id) AS blog_count,COUNT(DISTINCT knolx.id) AS " +
      "knolx_count FROM blog RIGHT JOIN knolder ON knolder.wordpress_id = blog.wordpress_id AND published_on >= ? AND " +
      "published_on < ? LEFT JOIN knolx ON knolder.email_id = knolx.email_id AND delivered_on >= ? AND delivered_on < ? " +
      "WHERE knolder.active_status = true GROUP BY knolder.id, knolder.wordpress_id, knolder.email_id, knolder.full_name")
      .bind(secondMonth, nextMonth, secondMonth, nextMonth)
      .map(rs => GetCount(rs.int("id"), rs.string("full_name"),
        rs.int("blog_count"), rs.int("knolx_count"))).list.apply()
  }

  /**
   * Fetches number of contributions of each knolder in third month of quarter.
   *
   * @return List of monthly data of each knolder.
   */
  override def fetchKnoldersWithQuarterThirdMonthContributions: List[GetCount] = {
    logger.info("Fetching details of knolders with contributions of third month of quarter.")
    val thirdMonth = Timestamp.valueOf(IndianTime.currentTime
      .withDayOfMonth(1).toLocalDate.minusMonths(1).atStartOfDay())
    val nextMonth = Timestamp.valueOf(IndianTime.currentTime
      .withDayOfMonth(1).toLocalDate.atStartOfDay())
    SQL("SELECT knolder.id, knolder.full_name, COUNT(DISTINCT blog.id) AS blog_count,COUNT(DISTINCT knolx.id) AS " +
      "knolx_count FROM blog RIGHT JOIN knolder ON knolder.wordpress_id = blog.wordpress_id AND published_on >= ? AND " +
      "published_on < ? LEFT JOIN knolx ON knolder.email_id = knolx.email_id AND delivered_on >= ? AND delivered_on < ? " +
      "WHERE knolder.active_status = true GROUP BY knolder.id, knolder.wordpress_id, knolder.email_id, knolder.full_name")
      .bind(thirdMonth, nextMonth, thirdMonth, nextMonth)
      .map(rs => GetCount(rs.int("id"), rs.string("full_name"),
        rs.int("blog_count"), rs.int("knolx_count"))).list.apply()
  }
}
