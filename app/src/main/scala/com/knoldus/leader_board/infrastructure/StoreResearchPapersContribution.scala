package com.knoldus.leader_board.infrastructure

import com.knoldus.leader_board.OtherContributionDetails

trait StoreResearchPapersContribution {
  def insertResearchPaperContributionDetails(listOfContribution: List[OtherContributionDetails]): List[Int]
}
