package com.knoldus.leader_board.infrastructure

import java.sql.Connection
import java.time.Month

import com.knoldus.leader_board.{ContributionScore, DatabaseConnection, GetContributionCount, IndianTime}
import com.typesafe.config.Config
import com.typesafe.scalalogging._
import scalikejdbc.{DB, DBSession, SQL}

final case class KnolderContributionScore(knolderId: Int, knolderName: String, blogScore: Option[Int], knolxScore: Option[Int],
                                          webinarScore: Option[Int], techHubScore: Option[Int],
                                          osContributionScore: Option[Int],
                                          conferenceScore: Option[Int], bookScore: Option[Int], researchPaperScore: Option[Int], meetupScore: Option[Int])

class ReadContributionImpl(config: Config) extends ReadContribution with LazyLogging {
  implicit val connection: Connection = DatabaseConnection.connection(config)
  implicit val session: DBSession = DB.readOnlySession()

  val KNOLDER_ID= "knolder_id"
  val FULL_NAME= "full_name"
  val BLOG_SCORE= "blog_score"
  val KNOLX_SCORE= "knolx_score"
  val WEBINAR_SCORE= "webinar_score"
  val TECHHUB_SCORE= "techhub_score"
  val OSCONTRIBUTION_SCORE= "oscontribution_score"
  val CONFERENCE_SCORE= "conference_score"
  val BOOK_SCORE= "book_score"
  val RESEARCH_PAPER_SCORE= "researchpaper_score"
  val MEETUP_SCORE= "meetup_score"


  val queryToFetchMonthlyDetails =
    """ SELECT
    knolder.id,
    knolder.full_name,
    COUNT(DISTINCT blog.id) AS blog_count, COUNT(DISTINCT knolx.id) AS knolx_count, COUNT(DISTINCT techhub.id) AS techhub_count,
    COUNT(DISTINCT webinar.id) AS webinar_count, COUNT(DISTINCT oscontribution.id) AS OS_contribution_count,
    COUNT(DISTINCT conference.id) AS conference_count, COUNT(DISTINCT book.id) AS book_count,
    COUNT(DISTINCT researchpaper.id) AS research_paper_count,
    COUNT(DISTINCT Meetup.id) AS meetup_count
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
    LEFT JOIN Meetup
    ON knolder.email_id = Meetup.email_id AND EXTRACT(month FROM Meetup.delivered_on) = ?
     AND EXTRACT(year FROM Meetup.delivered_on) = ?
    WHERE knolder.active_status = true
    GROUP BY knolder.id,knolder.wordpress_id,knolder.email_id,
    knolder.full_name"""

  val queryToFetchKnolderContributionSCore =
    """select knolder.full_name,knolder.id AS knolder_id,blog_score,knolx_Score,webinar_score,techhub_Score
  ,oscontribution_score,conference_score,researchpaper_score,book_score,meetup_score
  from knolder left join monthlycontribution
  on knolder.id=monthlycontribution.knolder_id And month= ? And year=? WHERE knolder.active_status = true"""

  /**
   * Fetches all time scores of contributions of each knolder.
   *
   * @return List of all time data of each knolder.
   */
  override def fetchKnoldersWithContributions: List[KnolderContributionScore] = {
    logger.info("Fetching all time scores of knolders with contributions.")
    SQL(
      """
       select knolder.full_name,knolder.id as knolder_id,SUM(knolx_score) as knolx_score,SUM(blog_score) as blog_score,
       SUM(webinar_score) as webinar_score,SUM(techhub_score) as techhub_score,SUM(oscontribution_score) as oscontribution_score,
       SUM(conference_score) as conference_score,SUM(book_score) as book_score,SUM(researchpaper_score) as researchpaper_score,
       SUM(Meetup_score) as meetup_score
       from knolder
       left join monthlycontribution on knolder.id=monthlycontribution.knolder_id
       WHERE knolder.active_status = true
        group by monthlycontribution.knolder_id,knolder.full_name,knolder.id""")
      .map(rs => KnolderContributionScore(rs.int(KNOLDER_ID), rs.string(FULL_NAME), rs.intOpt(BLOG_SCORE)
        , rs.intOpt(KNOLX_SCORE), rs.intOpt(WEBINAR_SCORE), rs.intOpt(TECHHUB_SCORE),
        rs.intOpt(OSCONTRIBUTION_SCORE), rs.intOpt(CONFERENCE_SCORE), rs.intOpt(BOOK_SCORE),
        rs.intOpt(RESEARCH_PAPER_SCORE), rs.intOpt(MEETUP_SCORE))).list().apply()
  }

  /**
   * Fetches number of contributions of each knolder in current month.
   *
   * @return List of monthly data of each knolder.
   */
  override def fetchKnoldersWithMonthlyContributions(monthName: String, year: Int): List[GetContributionCount] = {
    logger.info("Fetching details of knolders with contributions of current month.")
    val month = Month.valueOf(monthName).getValue
    SQL(queryToFetchMonthlyDetails)
      .bind(month, year, month, year, month, year, month, year, month, year, month, year, month, year, month, year, month, year)
      .map(rs => GetContributionCount(rs.int("id"), rs.string("full_name"), rs.int("blog_count"),
        rs.int("knolx_count"), rs.int("webinar_count"), rs.int("techhub_count"), rs.int("OS_contribution_count"), rs.int("conference_count")
        , rs.int("book_count"), rs.int("research_paper_count"), rs.int("meetup_count"))).list().apply()
  }

  /**
   * Fetches scores of contributions of each knolder in first month of quarter.
   *
   * @return List of quarterly data of each knolder.
   */
  override def fetchKnoldersWithQuarterFirstMonthContributions: List[KnolderContributionScore] = {
    logger.info("Fetching scores of knolders with contributions of first month of quarter.")
    val month = IndianTime.currentTime.minusMonths(3).getMonth.toString
    val year = IndianTime.currentTime.minusMonths(3).getYear

    SQL(queryToFetchKnolderContributionSCore)
      .bind(month, year)
      .map(rs => KnolderContributionScore(rs.int(KNOLDER_ID), rs.string(FULL_NAME), rs.intOpt(BLOG_SCORE)
        , rs.intOpt(KNOLX_SCORE), rs.intOpt(WEBINAR_SCORE), rs.intOpt(TECHHUB_SCORE),
        rs.intOpt(OSCONTRIBUTION_SCORE), rs.intOpt(CONFERENCE_SCORE), rs.intOpt(BOOK_SCORE),
        rs.intOpt(RESEARCH_PAPER_SCORE), rs.intOpt(MEETUP_SCORE))).list().apply()
  }

  /**
   * Fetches scores of contributions of each knolder in second month of quarter from blog table.
   *
   * @return List of quarterly data of each knolder.
   */
  override def fetchKnoldersWithQuarterSecondMonthContributions: List[KnolderContributionScore] = {
    logger.info("Fetching scores of knolders with contributions of second month of quarter.")
    val month = IndianTime.currentTime.minusMonths(2).getMonth.toString
    val year = IndianTime.currentTime.minusMonths(2).getYear
    SQL(queryToFetchKnolderContributionSCore)
      .bind(month, year)
      .map(rs => KnolderContributionScore(rs.int(KNOLDER_ID), rs.string(FULL_NAME), rs.intOpt(BLOG_SCORE)
        , rs.intOpt(KNOLX_SCORE), rs.intOpt(WEBINAR_SCORE), rs.intOpt(TECHHUB_SCORE),
        rs.intOpt(OSCONTRIBUTION_SCORE), rs.intOpt(CONFERENCE_SCORE), rs.intOpt(BOOK_SCORE),
        rs.intOpt(RESEARCH_PAPER_SCORE), rs.intOpt(MEETUP_SCORE))).list().apply()
  }

  /**
   * Fetches scorers of contributions of each knolder in third month of quarter.
   *
   * @return List of monthly data of each knolder.
   */
  override def fetchKnoldersWithQuarterThirdMonthContributions: List[KnolderContributionScore] = {
    logger.info("Fetching scores of knolders with contributions of third month of quarter.")
    val month = IndianTime.currentTime.minusMonths(1).getMonth.toString
    val year = IndianTime.currentTime.minusMonths(1).getYear
    SQL(queryToFetchKnolderContributionSCore)
      .bind(month, year)
      .map(rs => KnolderContributionScore(rs.int(KNOLDER_ID), rs.string(FULL_NAME), rs.intOpt(BLOG_SCORE)
        , rs.intOpt(KNOLX_SCORE), rs.intOpt(WEBINAR_SCORE), rs.intOpt(TECHHUB_SCORE),
        rs.intOpt(OSCONTRIBUTION_SCORE), rs.intOpt(CONFERENCE_SCORE), rs.intOpt(BOOK_SCORE),
        rs.intOpt(RESEARCH_PAPER_SCORE), rs.intOpt(MEETUP_SCORE))).list().apply()
  }

  /**
   * fetching score of given month and year of specific knolder.
   *
   * @return score of the month of specific knolder.
   */

  override def fetchKnoldersWithTwelveMonthContributions(month: Int, year: Int, knolderId: Int): Option[ContributionScore] = {
    logger.info("Fetching score of specific month of knolder.")

    SQL(
      """
       select blog_score,knolx_Score,webinar_score,techhub_Score
 ,oscontribution_score,conference_score,researchpaper_score,Meetup_score,book_score
 from monthlycontribution where month= ? And year=? And knolder_id=? """)
      .bind(Month.of(month).toString, year, knolderId)
      .map(rs => ContributionScore(rs.int(BLOG_SCORE), rs.int(KNOLX_SCORE),
        rs.int(WEBINAR_SCORE), rs.int(TECHHUB_SCORE), rs.int(OSCONTRIBUTION_SCORE), rs.int(CONFERENCE_SCORE)
        , rs.int(BOOK_SCORE), rs.int(RESEARCH_PAPER_SCORE), rs.int(MEETUP_SCORE)))
      .single().apply()
  }

  /**
   * fetching score of contribution of given month and year of specific knolder.
   *
   * @return scores of contribution of the month of specific knolder.
   */

  def fetchMonthlyContributionScore: List[KnolderContributionScore] = {
    logger.info("fetching scores of each contribution of the current")
    val month = IndianTime.currentTime.getMonth.toString
    val year = IndianTime.currentTime.getYear

    SQL(
      queryToFetchKnolderContributionSCore.stripMargin).bind(month, year)
      .map(rs => KnolderContributionScore(rs.int(KNOLDER_ID), rs.string(FULL_NAME), rs.intOpt(BLOG_SCORE)
        , rs.intOpt(KNOLX_SCORE), rs.intOpt(WEBINAR_SCORE), rs.intOpt(TECHHUB_SCORE),
        rs.intOpt(OSCONTRIBUTION_SCORE), rs.intOpt(CONFERENCE_SCORE), rs.intOpt(BOOK_SCORE),
        rs.intOpt(RESEARCH_PAPER_SCORE), rs.intOpt(MEETUP_SCORE))).list().apply()
  }
}
