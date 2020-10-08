package com.knoldus.leader_board.infrastructure

import com.knoldus.leader_board.business.KnolderIdWithKnolderContributionScore

trait WriteMonthlyContribution {

  def insertKnolderMonthlyContribution(knolderMonthlyContribution:List[KnolderIdWithKnolderContributionScore]):List[Int]

  def updateKnolderMonthlyContribution(knolderMonthlyContribution:List[KnolderIdWithKnolderContributionScore]): List[Int]
}
