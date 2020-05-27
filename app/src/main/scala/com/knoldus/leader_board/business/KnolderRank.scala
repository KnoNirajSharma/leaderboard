package com.knoldus.leader_board.business

import com.knoldus.leader_board.{GetReputation, GetScore}

trait KnolderRank {
  def calculateRank(scorePerKnolder: List[GetScore]): List[GetReputation]
}
