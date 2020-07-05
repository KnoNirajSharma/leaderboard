package com.knoldus.leader_board.business

import com.knoldus.leader_board.infrastructure.ReadContribution
import com.knoldus.leader_board.{IndianTime, TwelveMonthsScore}

class TwelveMonthsContributionImpl(readContribution: ReadContribution) extends TwelveMonthsContribution {

  override def lastTwelveMonthsScore(knolderId: Int, index: Int): Option[List[TwelveMonthsScore]] = {

    def calculateMonthsScore(scoreList: List[TwelveMonthsScore], monthsIndex: Int, id: Int): Option[List[TwelveMonthsScore]] = {
      if (monthsIndex > 12) {
        Option(scoreList)
      } else {
        val monthValue = IndianTime.currentTime.minusMonths(monthsIndex).getMonthValue
        val monthName = IndianTime.currentTime.minusMonths(monthsIndex).getMonth.toString
        val year = IndianTime.currentTime.minusMonths(monthsIndex).getYear
        val monthScore = readContribution.fetchKnoldersWithTwelveMonthContributions(monthValue, year, id)
        monthScore.flatMap(score => calculateMonthsScore(scoreList :+ TwelveMonthsScore(monthName, year, score), monthsIndex + 1, id))

      }
    }

    calculateMonthsScore(List.empty, index, knolderId)
  }
}

