package com.knoldus.leader_board.infrastructure

import com.knoldus.leader_board.MonthYearWithTopFiveLeaders

trait FetchMonthlyTopFiveLeaders {

  def fetchMonthlyTopFiveLeaders: List[MonthYearWithTopFiveLeaders]
}
