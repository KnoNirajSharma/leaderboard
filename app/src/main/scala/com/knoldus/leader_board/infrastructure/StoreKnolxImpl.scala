package com.knoldus.leader_board.infrastructure

import java.sql.Connection

import com.knoldus.leader_board.{DatabaseConnection, Knolx}
import com.typesafe.config.Config
import com.typesafe.scalalogging.LazyLogging
import scalikejdbc._

class StoreKnolxImpl(config: Config) extends StoreKnolx with LazyLogging {
  implicit val connection: Connection = DatabaseConnection.connection(config)
  implicit val session: DBSession = DB.autoCommitSession()

  /**
   * Stores list of knolx in knolx table.
   *
   * @param listOfKnolx List of knolx.
   * @return List of Integer which displays the status of query execution.
   */
  override def insertKnolx(listOfKnolx: List[Knolx]): List[Int] = {
    logger.info("Querying blog table to insert knolx details.")
    listOfKnolx.map { knolx =>
      SQL("INSERT INTO knolx(id , email_id, delivered_on, title) " +
        "SELECT ?, ?, ?, ? WHERE EXISTS (SELECT id FROM knolder WHERE email_id = ?) AND " +
        "NOT EXISTS (SELECT id FROM knolx WHERE id = ?)")
        .bind(knolx.knolxId, knolx.emailId, knolx.deliveredOn, knolx.title, knolx.emailId, knolx.knolxId)
        .update().apply()
    }
  }
}
