package com.knoldus.leader_board.store_rank

import java.sql.{ResultSet, SQLException}

import com.knoldus.leader_board.{DatabaseConnection, GetScore}

class ScoresOfKnolders(databaseConnection: DatabaseConnection) {
  /**
   * Fetches overall score of each knolder from all_time table.
   *
   * @return List of GetScore case class objects, which specifies overall score for each knolder.
   */
  def fetchScores: List[GetScore] = {
    try {
      val fetchReputation = databaseConnection.connection.createStatement()
        .executeQuery("SELECT knolder_id, overall_score FROM all_time")

      @scala.annotation.tailrec
      def fetchScores(fetchReputation: ResultSet, listOfScores: List[GetScore]): List[GetScore] = {
        if (fetchReputation.next()) {
          val knolderId = fetchReputation.getInt("knolder_id")
          val overallScore = fetchReputation.getInt("overall_score")
          fetchScores(fetchReputation, GetScore(knolderId, overallScore) :: listOfScores)
        }
        else {
          listOfScores
        }
      }

      fetchScores(fetchReputation, List.empty).sortBy(getScore => getScore.score).reverse
    }
    catch {
      case ex: SQLException => throw new SQLException(ex)
      case ex: Exception => throw new Exception(ex)
    }
  }
}
