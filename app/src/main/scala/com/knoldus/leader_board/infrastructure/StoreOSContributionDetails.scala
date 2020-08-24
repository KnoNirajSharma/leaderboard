package com.knoldus.leader_board.infrastructure

import com.knoldus.leader_board.OSContributionTemplate

trait StoreOSContributionDetails {
  def insertOSContribution(listOfOSContribution: List[OSContributionTemplate]): List[Int]
}
