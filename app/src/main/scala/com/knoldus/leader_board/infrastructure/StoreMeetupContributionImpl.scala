package com.knoldus.leader_board.infrastructure

import java.sql.Connection

import com.knoldus.leader_board.{DatabaseConnection, OtherContributionDetails}
import com.typesafe.config.Config
import com.typesafe.scalalogging.LazyLogging
import scalikejdbc.{DB, DBSession, SQL}

class StoreMeetupContributionImpl(config: Config) extends StoreMeetupContribution with LazyLogging {

  implicit val connection: Connection = DatabaseConnection.connection(config)
  implicit val session: DBSession = DB.autoCommitSession()

  /**
   * Stores list of Meetup details in book table.
   * @param listOfContribution List of other contribution.
   * @return List of Integer which displays the status of query execution.
   */
  override def insertMeetupContributionDetails(listOfContribution: List[OtherContributionDetails]): List[Int] = {
    logger.info("Querying meetup table to insert meetup details.")
    val listOfMeetupContribution = listOfContribution.filter(contribution => contribution.typeOfContributon == "Meetup")
    listOfMeetupContribution.map { MeetupContributionData =>
      SQL(
        """INSERT INTO
          Meetup(id , email_id, delivered_on, title)
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
          Meetup
          WHERE
          id = ?
        )""").stripMargin
        .bind(MeetupContributionData.contributionId, MeetupContributionData.emailId, MeetupContributionData.contributedOn
          , MeetupContributionData.title, MeetupContributionData.emailId, MeetupContributionData.contributionId)
        .update().apply()
    }
  }
}
