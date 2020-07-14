package com.knoldus.leader_board.infrastructure

import com.knoldus.leader_board.ReputationCountAndReputation

trait FetchCountWithReputation {

  def allTimeAndMonthlyContributionCountWithReputation: Option[ReputationCountAndReputation]
}
