package com.knoldus.leader_board.infrastructure

import com.knoldus.leader_board.GetCount

trait ReadContribution {
  def fetchKnoldersWithContributions: List[GetCount]

  def fetchKnoldersWithMonthlyContributions: List[GetCount]

  def fetchKnoldersWithQuarterFirstMonthContributions: List[GetCount]

  def fetchKnoldersWithQuarterSecondMonthContributions: List[GetCount]

  def fetchKnoldersWithQuarterThirdMonthContributions: List[GetCount]

  def fetchKnoldersWithTwelveMonthContributions(month: Int, year: Int, knolderId: Int): Option[Int]
}
