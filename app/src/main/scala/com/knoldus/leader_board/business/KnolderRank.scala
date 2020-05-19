package com.knoldus.leader_board.business

import com.knoldus.leader_board.{GetScore, Reputation}

trait KnolderRank {
  def calculateRank(scorePerKnolder: List[GetScore]): List[Reputation]
}
