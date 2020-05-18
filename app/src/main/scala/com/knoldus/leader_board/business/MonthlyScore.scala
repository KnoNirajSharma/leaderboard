package com.knoldus.leader_board.business

import com.knoldus.leader_board.GetScore

trait MonthlyScore {
  def calculateMonthlyScore: List[GetScore]
}
