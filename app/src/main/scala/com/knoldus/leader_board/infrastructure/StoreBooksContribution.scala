package com.knoldus.leader_board.infrastructure

import com.knoldus.leader_board.OtherContributionDetails

trait StoreBooksContribution {
  def insertBooksContributionDetails(listOfContribution: List[OtherContributionDetails]): List[Int]
}
