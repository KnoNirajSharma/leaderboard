package com.knoldus.leader_board.infrastructure

import java.sql.Connection

import com.knoldus.leader_board.business.MonthlyLeaders
import com.knoldus.leader_board.{DatabaseConnection, IndianTime}
import com.typesafe.config.Config
import com.typesafe.scalalogging.LazyLogging
import scalikejdbc.{DB, DBSession, SQL}

class StoreTopFiveLeadersImpl(config: Config, getKnolderReputataion: MonthlyLeaders) extends StoreTopFiveLeaders
  with LazyLogging {

  implicit val connection: Connection = DatabaseConnection.connection(config)
  implicit val session: DBSession = DB.autoCommitSession()

  /**
   * stores top five leaders of every month.
   *
   * @return List of Integer which displays the status of query execution.
   */

  def insertTopFiveLeaders: List[Int] = {
    val monthName = IndianTime.currentTime.minusMonths(1).getMonth.toString
    val year = IndianTime.currentTime.minusMonths(1).getYear
    logger.info("storing top five leaders in leader table of every month")
    val topFiveLeaders = 5
    val fetchReputation = getKnolderReputataion.getMonthlyAndAllTimeReputation.take(topFiveLeaders)

    fetchReputation.map { knoldersReputation =>
      SQL(
        """insert into
        halloffame(knolder_id,knolder_name,monthly_score,all_time_score,monthly_rank,all_time_rank,month,year)
        values(?, ?, ?, ?, ?, ?, ?, ?)""")
        .bind(knoldersReputation.knolderId, knoldersReputation.knolderName, knoldersReputation.monthlyScore,
          knoldersReputation.allTimeScore, knoldersReputation.monthlyRank, knoldersReputation.allTimeRank,monthName,year
        ).update()
        .apply()
    }
  }
}
