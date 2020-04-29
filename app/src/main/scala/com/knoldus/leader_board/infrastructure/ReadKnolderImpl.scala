package com.knoldus.leader_board.infrastructure

import java.sql.Connection

import com.knoldus.leader_board.{DatabaseConnection, Knolder}
import com.typesafe.scalalogging._
import scalikejdbc.{DB, DBSession, SQL}

class ReadKnolderImpl(databaseConnection: DatabaseConnection) extends ReadKnolder with LazyLogging {
  implicit val connection: Connection = databaseConnection.connection
  implicit val session: DBSession = DB.readOnlySession()

  /**
   * Queries knolder table to get details of each Knolder.
   *
   * @return List of knolders.
   */
  override def fetchKnolders: List[Knolder] = {
    logger.info("Querying knolder table to get details of each knolder.")
    SQL("SELECT * FROM knolder").map(rs => Knolder(rs.int("id"), rs.string("full_name"),
      rs.string("wordpress_id"), rs.string("email_id"))).list().apply()
  }
}
