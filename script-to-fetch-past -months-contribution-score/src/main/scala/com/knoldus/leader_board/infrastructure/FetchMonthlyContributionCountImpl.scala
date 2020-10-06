package com.knoldus.leader_board.infrastructure

import java.sql.{Connection, Timestamp}

import com.knoldus.leader_board.{DatabaseConnection, GetContributionCount, IndianTime}
import com.typesafe.config.Config
import com.typesafe.scalalogging.LazyLogging
import scalikejdbc.{DB, DBSession, SQL}

class FetchMonthlyContributionCountImpl(config: Config) extends LazyLogging with FetchMonthlyContributionCount {
  implicit val connection: Connection = DatabaseConnection.connection(config)
  implicit val session: DBSession = DB.readOnlySession()

  /**
   * Fetching monthly contribution count of each knolder.
   *
   * @return List of get contribution count of knolders.
   */

  override def fetchMonthlyContribution(month: Int, year: Int): List[GetContributionCount] = {
    logger.info("Fetching monthly contribution count of each knolder.")

    SQL(""" SELECT
    knolder.id,
    knolder.full_name,
    COUNT(DISTINCT blog.id) AS blog_count, COUNT(DISTINCT knolx.id) AS knolx_count, COUNT(DISTINCT techhub.id) AS techhub_count,
    COUNT(DISTINCT webinar.id) AS webinar_count, COUNT(DISTINCT oscontribution.id) AS OS_contribution_count,
    COUNT(DISTINCT conference.id) AS conference_count, COUNT(DISTINCT book.id) AS book_count,
    COUNT(DISTINCT researchpaper.id) AS research_paper_count
    FROM knolder
    LEFT JOIN blog
    ON knolder.wordpress_id = blog.wordpress_id AND EXTRACT(month FROM blog.published_on) = ?
     AND EXTRACT(year FROM blog.published_on) = ?
    LEFT JOIN techhub
    ON knolder.email_id = techhub.email_id AND EXTRACT(month FROM techhub.uploaded_on) = ?
     AND EXTRACT(year FROM techhub.uploaded_on) = ?
    LEFT JOIN
      webinar
    ON knolder.email_id = webinar.email_id AND EXTRACT(month FROM webinar.delivered_on) = ?
     AND EXTRACT(year FROM webinar.delivered_on) = ?
    LEFT JOIN
      knolx
    ON knolder.email_id = knolx.email_id AND EXTRACT(month FROM knolx.delivered_on) = ?
     AND EXTRACT(year FROM knolx.delivered_on) = ?
    LEFT JOIN
      oscontribution
    ON knolder.email_id = oscontribution.email_id AND EXTRACT(month FROM oscontribution.contributed_on) = ?
     AND EXTRACT(year FROM oscontribution.contributed_on) = ?
    LEFT JOIN
      conference
    ON knolder.email_id = conference.email_id AND EXTRACT(month FROM conference.delivered_on) = ?
     AND EXTRACT(year FROM conference.delivered_on) = ?
    LEFT JOIN
      book
    ON knolder.email_id = book.email_id AND EXTRACT(month FROM book.published_on) = ?
    AND EXTRACT(year FROM book.published_on) = ?
    LEFT JOIN
      researchpaper
    ON knolder.email_id = researchpaper.email_id AND EXTRACT(month FROM researchpaper.published_on) = ?
    AND EXTRACT(year FROM researchpaper.published_on) = ?
    WHERE knolder.active_status = true
    GROUP BY knolder.id,knolder.wordpress_id,knolder.email_id,
    knolder.full_name""").bind(month, year, month, year, month, year, month, year, month, year, month, year, month, year, month, year)
      .map(rs => GetContributionCount(rs.int("id"), rs.string("full_name"), rs.int("blog_count"),
        rs.int("knolx_count"), rs.int("webinar_count"), rs.int("techhub_count"), rs.int("OS_contribution_count"), rs.int("conference_count")
        , rs.int("book_count"), rs.int("research_paper_count"))).list.apply()
  }
}
