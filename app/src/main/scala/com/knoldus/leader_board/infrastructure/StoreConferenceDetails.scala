package com.knoldus.leader_board.infrastructure

import com.knoldus.leader_board.OtherContributionDetails

trait StoreConferenceDetails {

  def insertConferenceDetails(listOfContribution: List[OtherContributionDetails]): List[Int]
}
