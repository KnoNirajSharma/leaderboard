package com.knoldus.leader_board.business

import com.knoldus.leader_board.GetRank

trait OverallRank {
  def calculateRank: List[GetRank]
}
