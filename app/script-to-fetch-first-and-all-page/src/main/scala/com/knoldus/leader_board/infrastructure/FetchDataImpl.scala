package com.knoldus.leader_board.infrastructure

import java.sql.{Connection, Timestamp}

import com.knoldus.leader_board.DatabaseConnection
import com.typesafe.config.Config
import scalikejdbc._

class FetchDataImpl(config: Config) extends FetchData {
  implicit val connection: Connection = DatabaseConnection.connection(config)
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
