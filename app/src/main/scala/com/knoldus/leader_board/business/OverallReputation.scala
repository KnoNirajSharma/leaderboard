package com.knoldus.leader_board.business

import com.knoldus.leader_board.Reputation

trait OverallReputation {
  def calculateReputation: List[Reputation]
}
