package com.knoldus.leader_board.display_all_time_data

import java.sql.{ResultSet, SQLException}

import com.knoldus.leader_board.{DatabaseConnection, GetAuthorScore}

class AllTimeData(databaseConnection: DatabaseConnection) {
  /**
   * Queries all_time table to get full name, overall score and overall rank of each knolder.
   *
   * @return List of GetAuthorScore case class objects, which specifies full name, overall score and
   *         overall rank of each knolder.
   */
  def queryAllTimeData: List[GetAuthorScore] = {
    try {
      val fetchReputation: ResultSet = databaseConnection.connection.createStatement()
        .executeQuery("SELECT full_name, overall_score, overall_rank FROM all_time " +
          "INNER JOIN knolder ON all_time.knolder_id = knolder.id ORDER BY overall_score")

      @scala.annotation.tailrec
      def fetchAllTimeData(fetchRank: ResultSet, listOfAuthorScore: List[GetAuthorScore]): List[GetAuthorScore] = {
        if (fetchRank.next()) {
          val fullName = fetchRank.getString("full_name")
          val overallScore = fetchRank.getInt("overall_score")
          val overallRank = fetchRank.getInt("overall_rank")
          fetchAllTimeData(fetchRank, GetAuthorScore(fullName, overallScore, overallRank) :: listOfAuthorScore)
        }
        else {
          listOfAuthorScore
        }
      }

      fetchAllTimeData(fetchReputation, List.empty)
    }
    catch {
      case ex: SQLException => throw new SQLException(ex)
      case ex: Exception => throw new Exception(ex)
    }
    finally {
      databaseConnection.connection.close()
    }
  }
}
