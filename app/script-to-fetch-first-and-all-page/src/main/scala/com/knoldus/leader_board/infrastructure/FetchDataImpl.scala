package com.knoldus.leader_board.infrastructure

import java.sql.{Connection, Timestamp}

import com.knoldus.leader_board.DatabaseConnection
import scalikejdbc._

class FetchDataImpl(databaseConnection: DatabaseConnection) extends FetchData {
  implicit val connection: Connection = databaseConnection.connection
  implicit val session: DBSession = DB.readOnlySession()

  /**
   * Fetches Maximum publication date of blog present in blog table.
   *
   * @return Maximum publication date of blog wrapped in option.
   */
  override def fetchMaxBlogPublicationDate: Option[Timestamp] = {
    sql"SELECT MAX(published_on) FROM blog".map(rs => rs.timestamp(1)).single().apply()
  }
}
