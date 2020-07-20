package com.knoldus.leader_board.infrastructure

import java.sql.{Connection, Timestamp}

import com.knoldus.leader_board.DatabaseConnection
import com.typesafe.config.Config
import com.typesafe.scalalogging.LazyLogging
import scalikejdbc._

class FetchKnolxImpl(config: Config) extends FetchKnolx with LazyLogging {
  implicit val connection: Connection = DatabaseConnection.connection(config)
  implicit val session: DBSession = DB.readOnlySession()

  /**
   * Fetches maximum delivered date of knolx present in knolx table.
   *
   * @return Maximum delivered date of knolx wrapped in option.
   */
  override def fetchMaxKnolxDeliveredDate: Option[Timestamp] = {
    logger.info("Querying knolx table to fetch maximum delivered date of knolx.")
    sql"SELECT MAX(delivered_on) FROM knolx".map(rs => rs.timestamp(1)).single().apply()
  }
}
