package com.knoldus.leader_board.infrastructure

import com.knoldus.leader_board.business.OverallRank
import com.knoldus.leader_board.{AuthorScore, GetRank}
import scalikejdbc.{DB, DBSession, SQL}

class UpdateDataImpl(overallRank: OverallRank) extends UpdateData {
  implicit val session: DBSession = DB.autoCommitSession()

  /**
   * Updates rank of each knolder according to their overall score in all_time table.
   *
   * @return Message specifying data is updated and database connection is closed.
   */
  override def updateRank(): List[Int] = {
    val listOfRanks: List[GetRank] = overallRank.calculateRank.toList
    listOfRanks.map { ranks =>
      SQL("UPDATE all_time SET overall_rank = ? WHERE knolder_id = ?").bind(ranks.rank, ranks.knolderId)
        .update().apply()
    }
  }

  /**
   * Updates overall score of knolder in all_time table.
   *
   * @param scores   AuthorScore case class object which contains score of each knolder.
   * @param authorId Knolder id wrapped in option.
   * @return Integer which display the status of query execution.
   */
  override def updateAllTimeData(scores: AuthorScore, authorId: Option[Int]): Int = {
    SQL("UPDATE all_time SET overall_score = overall_score + ? WHERE knolder_id = ?")
      .bind(scores.score, authorId).update().apply()
  }
}
