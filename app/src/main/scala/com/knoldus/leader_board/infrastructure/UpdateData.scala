package com.knoldus.leader_board.infrastructure

import java.sql.SQLException

import com.knoldus.leader_board.{AuthorScore, DatabaseConnection, GetRank}
import scalikejdbc.{DB, DBSession, SQL}

class UpdateData(databaseConnection: DatabaseConnection) {

  /**
   * Updates rank of each knolder according to their overall score in all_time table.
   *
   * @param listOfRanks List of GetRank case class objects, which specifies overall rank for each knolder.
   * @return Message specifying data is updated and database connection is closed.
   */
  def updateRank(listOfRanks: List[GetRank]): List[Int] = {
    implicit val session: DBSession = DB.autoCommitSession()
    try {
      listOfRanks.map { ranks =>
        SQL("UPDATE all_time SET overall_rank = ? WHERE knolder_id = ?").bind(ranks.rank, ranks.knolderId)
          .update().apply()
      }
    }
    catch {
      case ex: SQLException => throw new SQLException(ex)
      case ex: Exception => throw new Exception(ex)
    }
    finally {
      session.close()
    }
  }

  /**
   * Updates overall score of knolder in all_time table.
   *
   * @param scores   AuthorScore case class object which contains score of each knolder.
   * @param authorId Knolder id wrapped in option.
   * @return Integer which display the status of query execution.
   */
  def updateAllTimeData(scores: AuthorScore, authorId: Option[Int]): Int = {
    implicit val session: DBSession = DB.autoCommitSession()
    try {
      SQL("UPDATE all_time SET overall_score = overall_score + ? WHERE knolder_id = ?")
        .bind(scores.score, authorId).update().apply()
    }
    catch {
      case ex: SQLException => throw new SQLException(ex)
      case ex: Exception => throw new Exception(ex)
    }
    finally {
      session.close()
    }
  }
}
