package com.knoldus.leader_board.infrastructure

import java.sql.Connection

import com.knoldus.leader_board.DatabaseConnection
import com.knoldus.leader_board.business.KnolderIdWithKnolderContributionScore
import com.typesafe.config.Config
import com.typesafe.scalalogging.LazyLogging
import scalikejdbc.{DB, DBSession, SQL}

class WriteMonthlyContributionImpl(config: Config) extends WriteMonthlyContribution with LazyLogging {

  implicit val connection: Connection = DatabaseConnection.connection(config)
  implicit val session: DBSession = DB.autoCommitSession()

  /**
   * Verifies whether knolder contribution of current month already exist in monthly contribution table or not, if not then it inserts it into the
   * table with its score and rank.
   *
   * @param knolderMonthlyContribution List of contribution of knolders along with their knolder id fetched from monthly
   *                                   contribution table..
   * @return List of Integer which displays the status of query execution.
   */

  def insertKnolderMonthlyContribution(knolderMonthlyContribution: List[KnolderIdWithKnolderContributionScore]): List[Int] = {
    logger.info("Inserting contribution of knolder monthly contribution table.")

    knolderMonthlyContribution.filter(knolderContribution => knolderContribution.knolderId.isEmpty)
      .map { knolderContribution =>
        SQL(
          """insert into monthlycontribution(knolder_id,blog_score,knolx_score,webinar_score
            |,techhub_score,oscontribution_score,conference_score,book_score,researchpaper_score,Meetup_score,month,year)
            |values(?,?,?,?,?,?,?,?,?,?,?,?)
            """.stripMargin).bind(knolderContribution.knolderContributionScore.knolderId, knolderContribution.knolderContributionScore.blogScore,
          knolderContribution.knolderContributionScore.knolxScore, knolderContribution.knolderContributionScore.webinarScore,
          knolderContribution.knolderContributionScore.techHubScore, knolderContribution.knolderContributionScore.osContributionScore,
          knolderContribution.knolderContributionScore.conferenceScore, knolderContribution.knolderContributionScore.bookScore,
          knolderContribution.knolderContributionScore.researchPaperScore, knolderContribution.knolderContributionScore.MeetupScore, knolderContribution.knolderContributionScore.month,
          knolderContribution.knolderContributionScore.year).update().apply()
      }
  }

  /**
   * Verifies whether knolder current month contribution already exist in monthly cpontribution table or not, if it does then it updates its score
   * and rank.
   *
   * @param knolderMonthlyContribution List of contribution of knolders along with their knolder id fetched from monthly
   *                                   contribution table.
   * @return List of Integer which displays the status of query execution.
   */
  def updateKnolderMonthlyContribution(knolderMonthlyContribution: List[KnolderIdWithKnolderContributionScore]): List[Int] = {
    logger.info("Updating contribution of knolder in monthly contribution table.")

    knolderMonthlyContribution.filter(knolderContribution => knolderContribution.knolderId.isDefined)
      .map { knolderContribution =>
        SQL(
          """update monthlycontribution SET blog_score=?,knolx_score=?,webinar_score=?,techhub_score=?,oscontribution_score=?,
            |conference_score=?,book_score=?,researchpaper_score=?,Meetup_score=? where knolder_id=? and month=? and year = ?
            |""".stripMargin).bind(knolderContribution.knolderContributionScore.blogScore,
          knolderContribution.knolderContributionScore.knolxScore, knolderContribution.knolderContributionScore.webinarScore,
          knolderContribution.knolderContributionScore.techHubScore, knolderContribution.knolderContributionScore.osContributionScore,
          knolderContribution.knolderContributionScore.conferenceScore, knolderContribution.knolderContributionScore.bookScore,
          knolderContribution.knolderContributionScore.researchPaperScore,
          knolderContribution.knolderContributionScore.MeetupScore, knolderContribution.knolderContributionScore.knolderId,
          knolderContribution.knolderContributionScore.month, knolderContribution.knolderContributionScore.year).update().apply()
      }
  }

}
