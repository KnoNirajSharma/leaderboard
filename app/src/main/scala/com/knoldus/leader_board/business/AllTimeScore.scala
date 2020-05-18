package com.knoldus.leader_board.business

import com.knoldus.leader_board.GetScore

trait AllTimeScore {
  def calculateAllTimeScore: List[GetScore]
}
