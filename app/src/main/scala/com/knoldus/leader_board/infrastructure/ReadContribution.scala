package com.knoldus.leader_board.infrastructure

import com.knoldus.leader_board.{ContributionScore, GetContributionCount}

trait ReadContribution {
  def fetchKnoldersWithContributions: List[KnolderContributionScore]

  def fetchKnoldersWithMonthlyContributions(monthName: String, year: Int): List[GetContributionCount]

  def fetchKnoldersWithQuarterFirstMonthContributions: List[KnolderContributionScore]

  def fetchKnoldersWithQuarterSecondMonthContributions: List[KnolderContributionScore]

  def fetchKnoldersWithQuarterThirdMonthContributions: List[KnolderContributionScore]

  def fetchKnoldersWithTwelveMonthContributions(month: Int, year: Int, knolderId: Int): Option[ContributionScore]

  def fetchMonthlyContributionScore: List[KnolderContributionScore]

}
