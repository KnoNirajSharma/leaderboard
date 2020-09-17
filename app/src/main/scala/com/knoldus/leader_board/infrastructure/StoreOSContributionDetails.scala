package com.knoldus.leader_board.infrastructure

import com.knoldus.leader_board.OtherContributionDetails

trait StoreOSContributionDetails {
  def insertOSContribution(listOfOSContribution: List[OtherContributionDetails]): List[Int]
}
