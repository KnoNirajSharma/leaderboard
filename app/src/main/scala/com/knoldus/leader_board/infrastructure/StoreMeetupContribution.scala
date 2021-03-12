package com.knoldus.leader_board.infrastructure

import com.knoldus.leader_board.OtherContributionDetails

trait StoreMeetupContribution {
  def insertMeetupContributionDetails(listOfContribution: List[OtherContributionDetails]): List[Int]
}
