package com.knoldus.leader_board.infrastructure

import com.knoldus.leader_board.AuthorScore

trait UpdateData {
  def updateRank(): List[Int]

  def updateAllTimeData(scores: AuthorScore, authorId: Option[Int]): Int
}
