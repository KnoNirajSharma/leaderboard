package com.knoldus.leader_board.store_rank

import java.sql.SQLException

import com.knoldus.leader_board.{DatabaseConnection, GetRank}

class UpdateRankOfKnolders(databaseConnection: DatabaseConnection) {
  /**
   * Updates rank of each knolder according to their overall score in all_time table.
   *
   * @param listOfRanks List of GetRank case class objects, which specifies overall rank for each knolder.
   * @return Message specifying data is updated and database connection is closed.
   */
  def updateRank(listOfRanks: List[GetRank]): String = {
    try {
      val connection = databaseConnection.connection
      listOfRanks.foreach { ranks =>
        val updateAllTime = connection
          .prepareStatement("UPDATE all_time SET overall_rank = ? WHERE knolder_id = ?")
        updateAllTime.setInt(1, ranks.rank)
        updateAllTime.setInt(2, ranks.knolderId)
        updateAllTime.executeUpdate()
        updateAllTime.close()
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
