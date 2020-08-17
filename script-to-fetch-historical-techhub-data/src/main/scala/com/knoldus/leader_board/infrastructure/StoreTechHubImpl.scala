package com.knoldus.leader_board.infrastructure

import java.sql.Connection

import com.knoldus.leader_board.{DatabaseConnection, TechHubTemplate}
import com.typesafe.config.Config
import com.typesafe.scalalogging.LazyLogging
import scalikejdbc.{DB, DBSession, SQL}

class StoreTechHubImpl(config: Config) extends StoreTechHub with LazyLogging {

  implicit val connection: Connection = DatabaseConnection.connection(config)
  implicit val session: DBSession = DB.autoCommitSession()

  /**
   * Stores list of techhub in techhub table.
   *
   * @param listOfTechHub List of techhub.
   * @return List of Integer which displays the status of query execution.
   */
  override def insertTechHub(listOfTechHub: List[TechHubTemplate]): List[Int] = {
    logger.info("Querying techhub table to insert techhub details.")
    listOfTechHub.map { techHub =>
      SQL(
        """INSERT INTO
          techhub(id , email_id, uploaded_on, title)
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
          techhub
          WHERE
          id = ?
        )""").stripMargin
        .bind(techHub.techHubId, techHub.emailId, techHub.uploadedOn, techHub.title, techHub.emailId, techHub.techHubId)
        .update().apply()
    }
  }
}
