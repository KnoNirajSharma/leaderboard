package com.knoldus.leader_board.business

import com.knoldus.leader_board.IndianTime
import com.typesafe.scalalogging.LazyLogging

import scala.annotation.tailrec

class PastMonthsContributionImpl(getMonthlyContributionScore: KnolderMonthlyContribution) extends PastMonthsContribution with LazyLogging {
  /**
   * calculating past months contribution scores of each knolder
   *
   * @return list of knolders each contribution score of past months.
   */

  override def getPastMonthsContributionScores(index: Int): List[KnolderEachContrbutionScore] = {
    logger.info("getting past months knolders each contribution score")

    @tailrec
    def getListOfPastMonthsContributionScore(knolderEachContributionList: List[KnolderEachContrbutionScore], monthsIndex: Int):
    List[KnolderEachContrbutionScore] = {
      if (monthsIndex < 1) {
        knolderEachContributionList
      } else {
        val monthValue = IndianTime.currentTime.minusMonths(monthsIndex).getMonthValue
        val year = IndianTime.currentTime.minusMonths(monthsIndex).getYear
        getListOfPastMonthsContributionScore(knolderEachContributionList ::: getMonthlyContributionScore.getKnolderMonthlyContribution(monthValue, year),
          monthsIndex - 1)
      }
    }

    getListOfPastMonthsContributionScore(List.empty, index)
  }
}
