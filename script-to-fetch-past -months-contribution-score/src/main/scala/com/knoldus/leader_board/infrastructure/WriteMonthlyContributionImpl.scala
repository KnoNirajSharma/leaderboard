package com.knoldus.leader_board.infrastructure

import java.sql.Connection

import com.knoldus.leader_board.DatabaseConnection
import com.knoldus.leader_board.business.PastMonthsContribution
import com.typesafe.config.Config
import scalikejdbc.{DB, DBSession, SQL}

class WriteMonthlyContributionImpl(config: Config, pastMonthsContribution: PastMonthsContribution) extends WriteMonthlyContribution {

  implicit val connection: Connection = DatabaseConnection.connection(config)
  implicit val session: DBSession = DB.autoCommitSession()

  def insertKnolderMonthlyContribution: List[Int] = {
    val index = 127
    val knolderMonthlyContribution = pastMonthsContribution.getPastMonthsContributionScores(index)
    knolderMonthlyContribution.map { knolderContribution =>
      SQL(
        """insert into monthlycontribution(knolder_id,blog_score,knolx_score,webinar_score
          |,techhub_score,oscontribution_score,conference_score,book_score,researchpaper_score,month,year)
          |values(?,?,?,?,?,?,?,?,?,?,?)
            """.stripMargin).bind(knolderContribution.knolderId, knolderContribution.blogScore,
        knolderContribution.knolxScore, knolderContribution.webinarScore,
        knolderContribution.techHubScore, knolderContribution.osContributionScore,
        knolderContribution.conferenceScore, knolderContribution.bookScore,
        knolderContribution.researchPaperScore, knolderContribution.month,
        knolderContribution.year).update().apply()
    }
  }
}
