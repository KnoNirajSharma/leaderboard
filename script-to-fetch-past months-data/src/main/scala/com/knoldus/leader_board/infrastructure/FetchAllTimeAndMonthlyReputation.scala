package com.knoldus.leader_board.infrastructure

import com.knoldus.leader_board.GetContributionCount

trait FetchAllTimeAndMonthlyReputation {

  def fetchAllTimeReputation(month: Int, year: Int): List[GetContributionCount]

  def fetchMonthlyReputation(month: Int, year: Int): List[GetContributionCount]
}
