package com.knoldus.leader_board.infrastructure

import java.sql.{Connection, Timestamp}

import com.knoldus.leader_board.{ContributionScore, DatabaseConnection, GetContributionCount, IndianTime}
import com.typesafe.config.Config
import com.typesafe.scalalogging._
import scalikejdbc.{DB, DBSession, SQL}

class ReadContributionImpl(config: Config) extends ReadContribution with LazyLogging {
  implicit val connection: Connection = DatabaseConnection.connection(config)
  implicit val session: DBSession = DB.readOnlySession()

  val queryToFetchMonthlyDetails =
    """
      SELECT
      knolder.id,
      knolder.full_name,
      COUNT(DISTINCT blog.id) AS blog_count, COUNT(DISTINCT knolx.id) AS knolx_count, COUNT(DISTINCT webinar.id) AS webinar_count,
      COUNT(DISTINCT techhub.id) AS techhub_count, COUNT(DISTINCT oscontribution.id) AS OS_contribution_count,
      COUNT(DISTINCT conference.id) AS conference_count
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
      webinar
    ON knolder.email_id = webinar.email_id
    AND webinar.delivered_on >= ?
    AND webinar.delivered_on < ?
    LEFT JOIN
      knolx
    ON knolder.email_id = knolx.email_id
    AND knolx.delivered_on >= ?
    AND knolx.delivered_on < ?
    LEFT JOIN
      oscontribution
    ON knolder.email_id = oscontribution.email_id
    AND oscontribution.contributed_on >= ?
    AND oscontribution.contributed_on < ?
     LEFT JOIN
      conference
    ON knolder.email_id = conference.email_id
    AND conference.delivered_on >= ?
    AND conference.delivered_on < ?
    WHERE
    knolder.active_status = true
    GROUP BY
      knolder.id,
    knolder.wordpress_id,
    knolder.email_id,
    knolder.full_name"""

  /**
   * Fetches number of contributions of each knolder.
   *
   * @return List of all time data of each knolder.
   */
  override def fetchKnoldersWithContributions: List[GetContributionCount] = {
    logger.info("Fetching details of knolders with contributions.")
    SQL(
      """
      SELECT
      knolder.id, knolder.full_name, COUNT(DISTINCT blog.id) AS blog_count, COUNT(DISTINCT knolx.id) AS knolx_count, COUNT(DISTINCT webinar.id) AS webinar_count
      ,COUNT(DISTINCT techhub.id) AS techhub_count , COUNT(DISTINCT oscontribution.id) AS OS_contribution_count ,
       COUNT(DISTINCT conference.id) AS conference_count
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
    LEFT JOIN
      oscontribution
    ON knolder.email_id = oscontribution.email_id
      LEFT JOIN
      conference
    ON knolder.email_id = conference.email_id
    WHERE
    knolder.active_status = true
    GROUP BY
      knolder.id, knolder.wordpress_id, knolder.email_id, knolder.full_name""")
      .map(rs => GetContributionCount(rs.int("id"), rs.string("full_name"), rs.int("blog_count"),
        rs.int("knolx_count"), rs.int("webinar_count"), rs.int("techhub_count"), rs.int("OS_contribution_count"), rs.int("conference_count"))).list().apply()
  }

  /**
   * Fetches number of contributions of each knolder in current month.
   *
   * @return List of monthly data of each knolder.
   */
  override def fetchKnoldersWithMonthlyContributions: List[GetContributionCount] = {
    logger.info("Fetching details of knolders with contributions of current month.")
    val currentMonth = Timestamp.valueOf(IndianTime.currentTime
      .withDayOfMonth(1).toLocalDate.atStartOfDay())
    val nextMonth = Timestamp.valueOf(IndianTime.currentTime
      .withDayOfMonth(1).toLocalDate.plusMonths(1).atStartOfDay())
    SQL(queryToFetchMonthlyDetails)
      .bind(currentMonth, nextMonth, currentMonth, nextMonth,
        currentMonth, nextMonth, currentMonth, nextMonth, currentMonth, nextMonth, currentMonth, nextMonth)
      .map(rs => GetContributionCount(rs.int("id"), rs.string("full_name"), rs.int("blog_count"),
        rs.int("knolx_count"), rs.int("webinar_count"), rs.int("techhub_count"), rs.int("OS_contribution_count"), rs.int("conference_count"))).list.apply()
  }

  /**
   * Fetches number of contributions of each knolder in first month of quarter.
   *
   * @return List of quarterly data of each knolder.
   */
  override def fetchKnoldersWithQuarterFirstMonthContributions: List[GetContributionCount] = {
    logger.info("Fetching details of knolders with contributions of first month of quarter.")
    val firstMonth = Timestamp.valueOf(IndianTime.currentTime
      .withDayOfMonth(1).toLocalDate.minusMonths(3).atStartOfDay())
    val nextMonth = Timestamp.valueOf(IndianTime.currentTime
      .withDayOfMonth(1).toLocalDate.minusMonths(2).atStartOfDay())
    SQL(queryToFetchMonthlyDetails)
      .bind(firstMonth, nextMonth, firstMonth, nextMonth, firstMonth, nextMonth, firstMonth, nextMonth, firstMonth, nextMonth, firstMonth, nextMonth)
      .map(rs => GetContributionCount(rs.int("id"), rs.string("full_name"),
        rs.int("blog_count"), rs.int("knolx_count"), rs.int("webinar_count"), rs.int("techhub_count"), rs.int("OS_contribution_count"),
        rs.int("conference_count"))).list.apply()
  }

  /**
   * Fetches number of contributions of each knolder in second month of quarter from blog table.
   *
   * @return List of quarterly data of each knolder.
   */
  override def fetchKnoldersWithQuarterSecondMonthContributions: List[GetContributionCount] = {
    logger.info("Fetching details of knolders with contributions of second month of quarter.")
    val secondMonth = Timestamp.valueOf(IndianTime.currentTime
      .withDayOfMonth(1).toLocalDate.minusMonths(2).atStartOfDay())
    val nextMonth = Timestamp.valueOf(IndianTime.currentTime
      .withDayOfMonth(1).toLocalDate.minusMonths(1).atStartOfDay())
    SQL(queryToFetchMonthlyDetails)
      .bind(secondMonth, nextMonth, secondMonth, nextMonth, secondMonth, nextMonth, secondMonth, nextMonth, secondMonth, nextMonth, secondMonth, nextMonth)
      .map(rs => GetContributionCount(rs.int("id"), rs.string("full_name"),
        rs.int("blog_count"), rs.int("knolx_count"), rs.int("webinar_count"), rs.int("techhub_count"), rs.int("OS_contribution_count"),
        rs.int("conference_count"))).list.apply()
  }

  /**
   * Fetches number of contributions of each knolder in third month of quarter.
   *
   * @return List of monthly data of each knolder.
   */
  override def fetchKnoldersWithQuarterThirdMonthContributions: List[GetContributionCount] = {
    logger.info("Fetching details of knolders with contributions of third month of quarter.")
    val thirdMonth = Timestamp.valueOf(IndianTime.currentTime
      .withDayOfMonth(1).toLocalDate.minusMonths(1).atStartOfDay())
    val nextMonth = Timestamp.valueOf(IndianTime.currentTime
      .withDayOfMonth(1).toLocalDate.atStartOfDay())
    SQL(queryToFetchMonthlyDetails).bind(thirdMonth, nextMonth, thirdMonth, nextMonth, thirdMonth, nextMonth, thirdMonth, nextMonth, thirdMonth, nextMonth
      , thirdMonth, nextMonth)
      .map(rs => GetContributionCount(rs.int("id"), rs.string("full_name"),
        rs.int("blog_count"), rs.int("knolx_count"), rs.int("webinar_count"), rs.int("techhub_count"), rs.int("OS_contribution_count"),
        rs.int("conference_count"))).list.apply()
  }

  /**
   * fetching score of given month and year of specific knolder.
   *
   * @return score of the month of specific knolder.
   */

  override def fetchKnoldersWithTwelveMonthContributions(month: Int, year: Int, knolderId: Int): Option[ContributionScore] = {
    logger.info("Fetching score of specific month of knolder.")

    SQL(
      s"""
      SELECT
      (COUNT(DISTINCT blog.id) * ${config.getInt("scorePerBlog")}) as blogScore, (COUNT(DISTINCT knolx.id) * ${config.getInt("scorePerKnolx")})
      as knolxScore, (COUNT(DISTINCT webinar.id) * ${config.getInt("scorePerWebinar")}) as webinarScore ,
      (COUNT(DISTINCT techhub.id) * ${config.getInt("scorePerTechHub")}) as techHubScore,
       (COUNT(DISTINCT oscontribution.id) * ${config.getInt("scorePerOsContribution")}) as osContributionScore,
       (COUNT(DISTINCT conference.id) * ${config.getInt("scorePerConference")}) as conferenceScore
    FROM knolder
    LEFT JOIN knolx
    ON knolder.email_id = knolx.email_id AND EXTRACT(month FROM knolx.delivered_on) = ?
    AND EXTRACT(year FROM knolx.delivered_on) = ?
    LEFT JOIN blog
    ON knolder.wordpress_id = blog.wordpress_id AND EXTRACT(month FROM blog.published_on) = ?
    AND EXTRACT(year FROM blog.published_on) = ?
    LEFT JOIN webinar
    ON knolder.email_id = webinar.email_id AND EXTRACT(month FROM webinar.delivered_on) = ?
    AND EXTRACT(year FROM webinar.delivered_on) = ?
    LEFT JOIN techhub
    ON knolder.email_id = techhub.email_id AND EXTRACT(month FROM techhub.uploaded_on) = ?
    AND EXTRACT(year FROM techhub.uploaded_on) = ?
    LEFT JOIN conference
    ON knolder.email_id = conference.email_id AND EXTRACT(month FROM conference.delivered_on) = ?
    AND EXTRACT(year FROM conference.delivered_on) = ?
    LEFT JOIN oscontribution
    ON knolder.email_id = oscontribution.email_id AND EXTRACT(month FROM oscontribution.contributed_on) = ?
    AND EXTRACT(year FROM oscontribution.contributed_on) = ?
    WHERE knolder.id = ? """)
      .bind(month, year, month, year, month, year, month, year, month, year, month, year, knolderId)
      .map(rs => ContributionScore(rs.int("blogScore"), rs.int("knolxScore"),
        rs.int("webinarScore"), rs.int("techHubScore"), rs.int("osContributionScore"), rs.int("conferenceScore")))
      .single().apply()
  }
}
