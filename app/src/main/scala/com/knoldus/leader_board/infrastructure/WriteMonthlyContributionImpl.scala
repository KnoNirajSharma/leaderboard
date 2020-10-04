package com.knoldus.leader_board.infrastructure

import java.sql.Connection

import com.knoldus.leader_board.DatabaseConnection
import com.knoldus.leader_board.business.KnolderIdWithKnolderContributionScore
import com.typesafe.config.Config
import scalikejdbc.{DB, DBSession, SQL}

class WriteMonthlyContributionImpl(config:Config) extends WriteMonthlyContribution {

  implicit val connection: Connection = DatabaseConnection.connection(config)
  implicit val session: DBSession = DB.autoCommitSession()

  def insertKnolderMonthlyContribution(knolderMonthlyContribution:List[KnolderIdWithKnolderContributionScore]): List[Int] ={
    knolderMonthlyContribution.filter(knolderContribution=>knolderContribution.knolderId.isEmpty)
      .map{knolderContribution=>
        SQL(
          """insert into monthlycontribution(knolder_id,blog_score,knolx_score,webinar_score
            |,techhub_score,oscontribution_score,conference_score,book_score,researchpaper_score,month,year)
            |values(?,?,?,?,?,?,?,?,?,?,?)
            """.stripMargin).bind(knolderContribution.knolderContributionScore.knolderId,knolderContribution.knolderContributionScore.blogScore,
          knolderContribution.knolderContributionScore.knolxScore,knolderContribution.knolderContributionScore.webinarScore,
          knolderContribution.knolderContributionScore.techHubScore,knolderContribution.knolderContributionScore.osContributionScore,
          knolderContribution.knolderContributionScore.conferenceScore,knolderContribution.knolderContributionScore.bookScore,
          knolderContribution.knolderContributionScore.researchPaperScore,knolderContribution.knolderContributionScore.month,
          knolderContribution.knolderContributionScore.year).update().apply()
      }
  }
  def updateKnolderMonthlyContribution(knolderMonthlyContribution:List[KnolderIdWithKnolderContributionScore]): List[Int] ={
    knolderMonthlyContribution.filter(knolderContribution=>knolderContribution.knolderId.isDefined)
      .map{knolderContribution=>
        SQL(
          """update monthlycontribution SET blog_score=?,knolx_score=?,webinar_score=?,techhub_score=?,oscontribution_score=?,
            |conference_score=?,book_score=?,researchpaper_score=? where knolder_id=? and month=? and year = ?
            |""".stripMargin).bind(knolderContribution.knolderContributionScore.blogScore,
          knolderContribution.knolderContributionScore.knolxScore,knolderContribution.knolderContributionScore.webinarScore,
        knolderContribution.knolderContributionScore.techHubScore,knolderContribution.knolderContributionScore.osContributionScore,
        knolderContribution.knolderContributionScore.conferenceScore,knolderContribution.knolderContributionScore.bookScore,
        knolderContribution.knolderContributionScore.researchPaperScore,knolderContribution.knolderContributionScore.knolderId,
          knolderContribution.knolderContributionScore.month, knolderContribution.knolderContributionScore.year).update().apply()
      }
  }

}
