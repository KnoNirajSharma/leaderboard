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
    SQL(
      """
      SELECT
      knolder.id, knolder.full_name, COUNT(DISTINCT blog.id) AS blog_count, COUNT(DISTINCT knolx.id) AS knolx_count, COUNT(DISTINCT webinar.id) AS webinar_count
      ,COUNT(DISTINCT techhub.id) AS techhub_count
      FROM
    knolder
    LEFT JOIN
      blog
    ON knolder.wordpress_id = blog.wordpress_id
    LEFT JOIN
      knolx
    ON knolder.email_id = knolx.email_id
    LEFT JOIN
      webinar
    ON knolder.email_id = webinar.email_id
    LEFT JOIN
      techhub
    ON knolder.email_id = techhub.email_id
    WHERE
    knolder.active_status = true
    GROUP BY
      knolder.id, knolder.wordpress_id, knolder.email_id, knolder.full_name""")
      .map(rs => GetCount(rs.int("id"), rs.string("full_name"),
        rs.int("blog_count"), rs.int("knolx_count"), rs.int("webinar_count"), rs.int("techhub_count"))).list().apply()
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
    SQL(
      """
      SELECT
      knolder.id,
      knolder.full_name,
      COUNT(DISTINCT blog.id) AS blog_count, COUNT(DISTINCT knolx.id) AS knolx_count, COUNT(DISTINCT webinar.id) AS webinar_count,
      COUNT(DISTINCT techhub.id) AS techhub_count
    FROM
    knolder
    LEFT JOIN
      blog
    ON knolder.wordpress_id = blog.wordpress_id
    AND published_on >= ?
    AND published_on < ?
    LEFT JOIN
      techhub
    ON knolder.email_id = techhub.email_id
    AND techhub.uploaded_on >= ?
    AND techhub.uploaded_on < ?
    LEFT JOIN
      knolx
    ON knolder.email_id = knolx.email_id
    AND knolx.delivered_on >= ?
    AND knolx.delivered_on < ?
    LEFT JOIN
      webinar
    ON knolder.email_id = webinar.email_id
    AND webinar.delivered_on >= ?
    AND webinar.delivered_on < ?
    WHERE
    knolder.active_status = true
    GROUP BY
      knolder.id,
    knolder.wordpress_id,
    knolder.email_id,
    knolder.full_name""")
      .bind(currentMonth, nextMonth, currentMonth, nextMonth, currentMonth, nextMonth, currentMonth, nextMonth)
      .map(rs => GetCount(rs.int("id"), rs.string("full_name"),
        rs.int("blog_count"), rs.int("knolx_count"), rs.int("webinar_count"), rs.int("techhub_count"))).list.apply()
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
    SQL(
      """
      SELECT
      knolder.id,
      knolder.full_name,
      COUNT(DISTINCT blog.id) AS blog_count,COUNT(DISTINCT knolx.id) AS knolx_count,
      COUNT(DISTINCT webinar.id) AS webinar_count,COUNT(DISTINCT techhub.id) AS techhub_count
    FROM
    knolder
    LEFT JOIN
      blog
    ON knolder.wordpress_id = blog.wordpress_id
    AND published_on >= ?
    AND published_on < ?
    LEFT JOIN
      techhub
    ON knolder.email_id = techhub.email_id
    AND techhub.uploaded_on >= ?
    AND techhub.uploaded_on < ?
      LEFT JOIN
      knolx
    ON knolder.email_id = knolx.email_id
    AND knolx.delivered_on >= ?
    AND knolx.delivered_on  < ?
    LEFT JOIN
      webinar
    ON knolder.email_id = webinar.email_id
    AND webinar.delivered_on >= ?
    AND webinar.delivered_on < ?
    WHERE
    knolder.active_status = true
    GROUP BY
      knolder.id,
    knolder.wordpress_id,
    knolder.email_id,
    knolder.full_name""")
      .bind(firstMonth, nextMonth, firstMonth, nextMonth, firstMonth, nextMonth, firstMonth, nextMonth)
      .map(rs => GetCount(rs.int("id"), rs.string("full_name"),
        rs.int("blog_count"), rs.int("knolx_count"), rs.int("webinar_count"), rs.int("techhub_count"))).list.apply()
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
    SQL(
      """
      SELECT
      knolder.id,
      knolder.full_name,
      COUNT(DISTINCT blog.id) AS blog_count, COUNT(DISTINCT knolx.id) AS knolx_count,
       COUNT(DISTINCT webinar.id) AS webinar_count,COUNT(DISTINCT techhub.id) AS techhub_count
    FROM
    knolder
    LEFT JOIN
      blog
    ON knolder.wordpress_id = blog.wordpress_id
    AND published_on >= ?
      AND published_on < ?
    LEFT JOIN
      techhub
    ON knolder.email_id = techhub.email_id
    AND techhub.uploaded_on >= ?
    AND techhub.uploaded_on < ?
      LEFT JOIN
      knolx
    ON knolder.email_id = knolx.email_id
    AND knolx.delivered_on >= ?
    AND knolx.delivered_on < ?
    LEFT JOIN
      webinar
    ON knolder.email_id = webinar.email_id
    AND webinar.delivered_on >= ?
    AND webinar.delivered_on < ?
    WHERE
    knolder.active_status = true
    GROUP BY
      knolder.id,
    knolder.wordpress_id,
    knolder.email_id,
    knolder.full_name""")
      .bind(secondMonth, nextMonth, secondMonth, nextMonth, secondMonth, nextMonth, secondMonth, nextMonth)
      .map(rs => GetCount(rs.int("id"), rs.string("full_name"),
        rs.int("blog_count"), rs.int("knolx_count"), rs.int("webinar_count"), rs.int("techhub_count"))).list.apply()
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
    SQL(
      """
        SELECT
        knolder.id,
      knolder.full_name,
      COUNT(DISTINCT knolx.id) AS knolx_count,COUNT(DISTINCT blog.id) AS blog_count,
      COUNT(DISTINCT webinar.id) AS webinar_count,COUNT(DISTINCT techhub.id) AS techhub_count
    FROM
    knolder
    LEFT JOIN
      blog
    ON knolder.wordpress_id = blog.wordpress_id
    AND published_on >= ?
      AND published_on < ?
    LEFT JOIN
      techhub
    ON knolder.email_id = techhub.email_id
    AND techhub.uploaded_on >= ?
    AND techhub.uploaded_on < ?
      LEFT JOIN
      knolx
    ON knolder.email_id = knolx.email_id
    AND knolx.delivered_on >= ?
    AND knolx.delivered_on < ?
    LEFT JOIN
      webinar
    ON knolder.email_id = webinar.email_id
    AND webinar.delivered_on >= ?
    AND webinar.delivered_on < ?
    WHERE
    knolder.active_status = true
    GROUP BY
      knolder.id,
    knolder.wordpress_id,
    knolder.email_id,
    knolder.full_name"""
    ).bind(thirdMonth, nextMonth, thirdMonth, nextMonth, thirdMonth, nextMonth, thirdMonth, nextMonth)
      .map(rs => GetCount(rs.int("id"), rs.string("full_name"),
        rs.int("blog_count"), rs.int("knolx_count"), rs.int("webinar_count"), rs.int("techhub_count"))).list.apply()
  }

  /**
   * fetching score of given month and year of specific knolder.
   *
   * @return score of the month of specific knolder.
   */

  override def fetchKnoldersWithTwelveMonthContributions(month: Int, year: Int, knolderId: Int): Option[Int] = {
    logger.info("Fetching score of specific month of knolder.")

    SQL(
      s"""
      SELECT
      COUNT(DISTINCT blog.title) * ${config.getInt("scorePerBlog")} + COUNT(DISTINCT knolx.title) * ${config.getInt("scorePerKnolx")}
      + COUNT(DISTINCT webinar.title) * ${config.getInt("scorePerWebinar")} + COUNT(DISTINCT techhub.title) * ${config.getInt("scorePerTechHub")} AS score
    FROM knolder
    LEFT JOIN blog
    ON knolder.wordpress_id = blog.wordpress_id AND EXTRACT(month FROM blog.published_on) = ?
    AND EXTRACT(year FROM blog.published_on) = ?
    LEFT JOIN knolx
    ON knolder.email_id = knolx.email_id AND EXTRACT(month FROM knolx.delivered_on) = ?
    AND EXTRACT(year FROM knolx.delivered_on) = ?
    LEFT JOIN webinar
    ON knolder.email_id = webinar.email_id AND EXTRACT(month FROM webinar.delivered_on) = ?
    AND EXTRACT(year FROM knolx.delivered_on) = ?
    LEFT JOIN techhub
    ON knolder.email_id = techhub.email_id AND EXTRACT(month FROM techhub.uploaded_on) = ?
    AND EXTRACT(year FROM techhub.uploaded_on) = ?
    WHERE knolder.id = ? """)
      .bind(month, year, month, year, month, year, month, year, knolderId)
      .map(rs => rs.int("score"))
      .single().apply()
  }
}
