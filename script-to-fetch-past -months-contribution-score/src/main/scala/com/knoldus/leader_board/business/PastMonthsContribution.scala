package com.knoldus.leader_board.business

trait PastMonthsContribution {

  def getPastMonthsContributionScores(index: Int): List[KnolderEachContrbutionScore]
}
