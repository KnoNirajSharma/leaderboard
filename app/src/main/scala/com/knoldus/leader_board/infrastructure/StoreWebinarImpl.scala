package com.knoldus.leader_board.infrastructure

import java.sql.Connection

import com.knoldus.leader_board.{DatabaseConnection, Webinar}
import com.typesafe.config.Config
import com.typesafe.scalalogging.LazyLogging
import scalikejdbc.{DB, DBSession, SQL}

class StoreWebinarImpl(config: Config) extends StoreWebinar with LazyLogging {

  implicit val connection: Connection = DatabaseConnection.connection(config)
  implicit val session: DBSession = DB.autoCommitSession()

  /**
   * Stores list of webinar in webinar table.
   *
   * @param listOfWebinar List of webinar.
   * @return List of Integer which displays the status of query execution.
   */
  override def insertWebinar(listOfWebinar: List[Webinar]): List[Int] = {
    logger.info("Querying webinar table to insert webinar details.")
    listOfWebinar.map { webinar =>
      SQL("""INSERT INTO
          webinar(id , email_id, delivered_on, title)
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
          webinar
          WHERE
          id = ?
        )""").stripMargin
        .bind(webinar.id, webinar.emailId, webinar.deliveredOn, webinar.title, webinar.emailId, webinar.id)
        .update().apply()
    }
  }
}
