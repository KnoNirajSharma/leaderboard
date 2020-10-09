package com.knoldus.leader_board.infrastructure

import com.knoldus.leader_board.GetContributionCount

trait FetchMonthlyContributionCount {

  def fetchMonthlyContribution(month: Int, year: Int): List[GetContributionCount]
}
