package com.knoldus.leader_board.infrastructure

import com.knoldus.leader_board.GetContributionCount

trait ReadContribution {
  def fetchKnoldersWithContributions: List[GetContributionCount]

  def fetchKnoldersWithMonthlyContributions: List[GetContributionCount]

  def fetchKnoldersWithQuarterFirstMonthContributions: List[GetContributionCount]

  def fetchKnoldersWithQuarterSecondMonthContributions: List[GetContributionCount]

  def fetchKnoldersWithQuarterThirdMonthContributions: List[GetContributionCount]

  def fetchKnoldersWithTwelveMonthContributions(month: Int, year: Int, knolderId: Int): Option[(Int, Int, Int, Int, Int)]
}
