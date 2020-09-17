package com.knoldus.leader_board.infrastructure

import java.sql.Connection

import com.knoldus.leader_board.{DatabaseConnection, OtherContributionDetails}
import com.typesafe.config.Config
import com.typesafe.scalalogging.LazyLogging
import scalikejdbc.{DB, DBSession, SQL}

class StoreOSContributionDetailsImpl(config: Config) extends StoreOSContributionDetails with LazyLogging {

  implicit val connection: Connection = DatabaseConnection.connection(config)
  implicit val session: DBSession = DB.autoCommitSession()

  /**
   * Stores list of oscontribution in oscontribution table.
   *
   * @param listOfContribution List of other contribution.
   * @return List of Integer which displays the status of query execution.
   */
  override def insertOSContribution(listOfContribution: List[OtherContributionDetails]): List[Int] = {
    logger.info("Querying oscontribution table to insert oscontribution details.")
    val listOfOSContribution = listOfContribution.filter(contribution => contribution.typeOfContributon == "Open Source")
    listOfOSContribution.map { oSContributionData =>
      SQL(
        """INSERT INTO
          oscontribution(id , email_id, contributed_on, title)
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
          oscontribution
          WHERE
          id = ?
        )""").stripMargin
        .bind(oSContributionData.contributionId, oSContributionData.emailId, oSContributionData.contributedOn, oSContributionData.title,
          oSContributionData.emailId, oSContributionData.contributionId)
        .update().apply()
    }
  }
}
