package com.knoldus.leader_board.business

import com.knoldus.leader_board.MonthlyAllTimeReputation

trait PastMonthsLeaders {

  def getPastMonthsLeaders(index: Int): List[MonthlyAllTimeReputation]
}
