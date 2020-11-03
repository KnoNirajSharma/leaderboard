package com.knoldus.leader_board.infrastructure

import java.sql.Connection

import com.knoldus.leader_board.{DatabaseConnection, OtherContributionDetails}
import com.typesafe.config.Config
import com.typesafe.scalalogging.LazyLogging
import scalikejdbc.{DB, DBSession, SQL}

class StoreBooksContributionImpl(config: Config) extends StoreBooksContribution with LazyLogging {

  implicit val connection: Connection = DatabaseConnection.connection(config)
  implicit val session: DBSession = DB.autoCommitSession()

  /**
   * Stores list of books details in book table.
   * @param listOfContribution List of other contribution.
   * @return List of Integer which displays the status of query execution.
   */
  override def insertBooksContributionDetails(listOfContribution: List[OtherContributionDetails]): List[Int] = {
    logger.info("Querying books table to insert books details.")
    val listOfBooksContribution = listOfContribution.filter(contribution => contribution.typeOfContributon == "Books")
    listOfBooksContribution.map { booksContributionData =>
      SQL(
        """INSERT INTO
          book(id , email_id, published_on, title)
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
          book
          WHERE
          id = ?
        )""").stripMargin
        .bind(booksContributionData.contributionId, booksContributionData.emailId, booksContributionData.contributedOn
          , booksContributionData.title, booksContributionData.emailId, booksContributionData.contributionId)
        .update().apply()
    }
  }
}
