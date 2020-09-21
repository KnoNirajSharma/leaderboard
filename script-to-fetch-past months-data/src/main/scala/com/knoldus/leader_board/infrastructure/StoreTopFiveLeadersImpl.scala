package com.knoldus.leader_board.infrastructure

import java.sql.Connection

import com.knoldus.leader_board.business.PastMonthsLeaders
import com.knoldus.leader_board.{DatabaseConnection, IndianTime}
import com.typesafe.config.Config
import com.typesafe.scalalogging.LazyLogging
import scalikejdbc.{DB, DBSession, SQL}

class StoreTopFiveLeadersImpl(config: Config, getKnolderReputataion: PastMonthsLeaders) extends StoreTopFiveLeaders
  with LazyLogging {

  implicit val connection: Connection = DatabaseConnection.connection(config)
  implicit val session: DBSession = DB.autoCommitSession()

  /**
   * stores top five leaders of every month.
   *
   * @return List of Integer which displays the status of query execution.
   */

  def insertTopFiveLeaders: List[Int] = {
    logger.info("storing top five leaders in leader table of past months")
    val index= 8
    val listOfPastMonthsLeaders = getKnolderReputataion.getPastMonthsLeaders(index)

    listOfPastMonthsLeaders.map { knoldersReputation =>
      SQL(
        """insert into
        halloffame(knolder_id,knolder_name,monthly_score,all_time_score,monthly_rank,all_time_rank,month,year)
        values(?, ?, ?, ?, ?, ?, ?, ?)""")
        .bind(knoldersReputation.knolderId, knoldersReputation.knolderName, knoldersReputation.monthlyScore,
          knoldersReputation.allTimeScore, knoldersReputation.monthlyRank, knoldersReputation.allTimeRank, knoldersReputation.month, knoldersReputation.year
        ).update()
        .apply()
    }
  }
}
