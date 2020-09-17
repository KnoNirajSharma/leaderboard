package com.knoldus.leader_board.infrastructure

import java.sql.Connection

import com.knoldus.leader_board.{DatabaseConnection, OtherContributionDetails}
import com.typesafe.config.Config
import com.typesafe.scalalogging.LazyLogging
import scalikejdbc.{DB, DBSession, SQL}

class StoreConferenceDetailsImpl(config: Config) extends StoreConferenceDetails with LazyLogging {

  implicit val connection: Connection = DatabaseConnection.connection(config)
  implicit val session: DBSession = DB.autoCommitSession()

  /**
   * Stores list of conference details in conference table.
   *
   * @param listOfContribution List of other contribution.
   * @return List of Integer which displays the status of query execution.
   */
  override def insertConferenceDetails(listOfContribution: List[OtherContributionDetails]): List[Int] = {
    logger.info("Querying conference table to insert conference details.")
    val listOfConferenceContribution = listOfContribution.filter(contribution => contribution.typeOfContributon == "Conference")
    listOfConferenceContribution.map { conferenceContributionData =>
      SQL(
        """INSERT INTO
          conference(id , email_id, delivered_on, title)
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
          conference
          WHERE
          id = ?
        )""").stripMargin
        .bind(conferenceContributionData.contributionId, conferenceContributionData.emailId, conferenceContributionData.contributedOn
          , conferenceContributionData.title, conferenceContributionData.emailId, conferenceContributionData.contributionId)
        .update().apply()
    }
  }
}
