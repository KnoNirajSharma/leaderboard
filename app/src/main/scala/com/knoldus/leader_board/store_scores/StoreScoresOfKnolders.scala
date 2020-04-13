package com.knoldus.leader_board.store_scores

import java.sql.SQLException

import com.knoldus.leader_board.{AuthorScore, DatabaseConnection}

class StoreScoresOfKnolders(databaseConnection: DatabaseConnection) {
  /**
   * Stores list of scores in all_time table.
   *
   * @param listOfAuthorScore List of AuthorScore case class objects specifying wordpress id, name and overall
   *                          score of each knolder.
   * @return Message specifying data is stored and database connection is closed.
   */
  def storeScores(listOfAuthorScore: List[AuthorScore]): String = {
    try {
      val connection = databaseConnection.connection
      listOfAuthorScore.foreach { scores =>
        val fetchId = connection
          .prepareStatement("SELECT id FROM knolder WHERE wordpress_id = ?")
        scores.authorLogin.foreach(authorLogin => fetchId.setString(1, authorLogin))
        val getId = fetchId.executeQuery()
        getId.next()
        val authorId = getId.getInt("id")
        fetchId.close()

        val fetchKnolderId = connection
          .prepareStatement("SELECT knolder_id FROM all_time WHERE knolder_id = ?")
        fetchKnolderId.setInt(1, authorId)
        val getKnolderId = fetchKnolderId.executeQuery()
        getKnolderId.next()
        if (getKnolderId.getRow == 0) {
          val insertAllTimeData = connection
            .prepareStatement("INSERT INTO all_time(knolder_id, overall_score, overall_rank) values (?,?,?)")
          insertAllTimeData.setInt(1, authorId)
          insertAllTimeData.setInt(2, scores.score)
          insertAllTimeData.setInt(3, 0)
          insertAllTimeData.executeUpdate()
          insertAllTimeData.close()
        } else {
          val updateAllTimeData = connection
            .prepareStatement("UPDATE all_time SET overall_score = overall_score + ? WHERE knolder_id = ?")
          updateAllTimeData.setInt(1, scores.score)
          updateAllTimeData.setInt(2, authorId)
          updateAllTimeData.executeUpdate()
          updateAllTimeData.close()
        }
      }
      "connection closed"
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
