package com.knoldus.leader_board.infrastructure

import java.sql.Connection

import com.knoldus.leader_board.DatabaseConnection
import com.typesafe.config.Config
import com.typesafe.scalalogging.LazyLogging
import scalikejdbc.{DB, DBSession, SQL}

class FetchMonthlyKnolderContributionImpl(config: Config) extends FetchMonthlyKnolderContribution with LazyLogging{

  implicit val connection: Connection = DatabaseConnection.connection(config)
  implicit val session: DBSession = DB.readOnlySession()
  /**
   * Fetches foreign key i.e. knolder id in monthly contribution table.
   *
   * @param knolderId Knolder id.
   * @param month month .
   * @param year year.
   * @return Knolder id wrapped in option.
   */

  def fetchKnolderIdIfKnolderMonthlyContributionExist(knolderId: Int, month: String, year: Int):Option[Int] = {
  logger.info("fetches knolder id if that knolder id month and year exist in monthly contribution table")

    SQL("select knolder_id from monthlycontribution where knolder_id=? and month =? and year=?").bind(knolderId, month, year)
      .map(rs => rs.int("knolder_id")).single().apply()
  }
}
