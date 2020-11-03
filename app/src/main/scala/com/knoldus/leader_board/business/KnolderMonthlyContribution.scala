package com.knoldus.leader_board.business

trait KnolderMonthlyContribution {

  def getKnolderMonthlyContribution(month: String, year: Int): List[KnolderIdWithKnolderContributionScore]
}
