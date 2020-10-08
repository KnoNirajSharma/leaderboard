package com.knoldus.leader_board.business

import com.knoldus.leader_board.infrastructure.FetchMonthlyContributionCount


class KnolderMonthlyContributionImpl(readContribution: FetchMonthlyContributionCount, knolderScore: KnolderScore) extends KnolderMonthlyContribution {

  def getKnolderMonthlyContribution(month: Int, year: Int): List[KnolderEachContrbutionScore] = {

    val contributions = readContribution.fetchMonthlyContribution(month, year)

    knolderScore.calculateScore(month, year, contributions)
  }
}
