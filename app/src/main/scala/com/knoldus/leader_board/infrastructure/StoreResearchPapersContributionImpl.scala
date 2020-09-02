package com.knoldus.leader_board.infrastructure

import java.sql.Connection

import com.knoldus.leader_board.{DatabaseConnection, OtherContributionDetails}
import com.typesafe.config.Config
import com.typesafe.scalalogging.LazyLogging
import scalikejdbc.{DB, DBSession, SQL}

class StoreResearchPapersContributionImpl(config: Config) extends StoreResearchPapersContribution with LazyLogging {

  implicit val connection: Connection = DatabaseConnection.connection(config)
  implicit val session: DBSession = DB.autoCommitSession()

  /**
   * Stores list of research papers details in research paper table.
   * @param listOfContribution List of other contribution.
   * @return List of Integer which displays the status of query execution.
   */
  override def insertResearchPaperContributionDetails(listOfContribution: List[OtherContributionDetails]): List[Int] = {
    logger.info("Querying research paper table to insert research paper details.")
    val listOfResearchPaperContribution = listOfContribution.filter(contribution => contribution.typeOfContributon == "Research Paper")
    listOfResearchPaperContribution.map { researchPaperContributionData =>
      SQL(
        """INSERT INTO
          researchpaper(id , email_id, published_on, title)
          SELECT
          ?,
          ?,
          ?,
          ?
          WHERE
          EXISTS
          (
           SELECT
          id
          FROM
          knolder
          WHERE
          email_id = ?
        )
      AND NOT EXISTS
      (
        SELECT
          id
          FROM
          researchpaper
          WHERE
          id = ?
        )""").stripMargin
        .bind(researchPaperContributionData.contributionId, researchPaperContributionData.emailId, researchPaperContributionData.contributedOn
          , researchPaperContributionData.title, researchPaperContributionData.emailId, researchPaperContributionData.contributionId)
        .update().apply()
    }
  }
}
