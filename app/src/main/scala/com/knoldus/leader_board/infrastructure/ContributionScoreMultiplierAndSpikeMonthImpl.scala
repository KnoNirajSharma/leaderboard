package com.knoldus.leader_board.infrastructure

import java.sql.Connection

import com.knoldus.leader_board.DatabaseConnection
import com.typesafe.config.Config
import com.typesafe.scalalogging.LazyLogging
import scalikejdbc.{DB, DBSession, SQL}

final case class ScoreMultiplier(blogScoreMultiplier: Int, knolxScoreMultiplier: Int, webinarScoreMultiplier: Int
                                 , techHubScoreMultiplier: Int, osContributionScoreMultiplier: Int, conferenceScoreMultiplier: Int
                                 , researchPaperScoreMultiplier: Int, meetupScoreMultiplier: Int, bookScoreMultiplier: Int, month: String, year: Int)

class ContributionScoreMultiplierAndSpikeMonthImpl(config: Config) extends ContributionScoreMultiplierAndSpikeMonth with LazyLogging {


  implicit val connection: Connection = DatabaseConnection.connection(config)
  implicit val session: DBSession = DB.readOnlySession()

  /**
   * Fetching spike month and contribution multiplier.
   *
   * @return option of score multiplier .
   */
  override def fetchContributionScoreMultiplierAndSpikeMonthImpl(month: String, year: Int): Option[ScoreMultiplier] = {
    logger.info("fetching spike month and score multiplier of each contribution")

    SQL("select * from dynamicscoring where month = ? And year= ?").bind(month, year)
      .map(rs => ScoreMultiplier(rs.int("blog_score_multiplier"), rs.int("knolx_score_multiplier")
        , rs.int("webinar_score_multiplier"), rs.int("techhub_score_multiplier"), rs.int("oscontribution_score_multiplier")
        , rs.int("conference_score_multiplier"), rs.int("researchpaper_score_multiplier"), rs.int("meetup_score_multiplier"), rs.int("book_score_multiplier")
        , rs.string("month"), rs.int("year"))).first().apply()
  }
}
