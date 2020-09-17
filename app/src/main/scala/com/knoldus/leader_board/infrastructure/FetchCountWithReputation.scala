package com.knoldus.leader_board.infrastructure

import com.knoldus.leader_board.ReputationWithCount

trait FetchCountWithReputation {

  def allTimeAndMonthlyContributionCountWithReputation: Option[ReputationWithCount]
}
