package com.knoldus.leader_board.business

import com.knoldus.leader_board.MonthlyAllTimeReputation

trait MonthlyLeaders {

  def getMonthlyAndAllTimeReputation: List[MonthlyAllTimeReputation]
}
