package com.knoldus.leader_board.business

import com.knoldus.leader_board.infrastructure.ReadContribution
import com.knoldus.leader_board.{IndianTime, TwelveMonthsScore}
import com.typesafe.scalalogging.LazyLogging

import scala.annotation.tailrec
import scala.collection.mutable.ListBuffer

class TwelveMonthsContributionImpl(readContribution: ReadContribution) extends TwelveMonthsContribution with LazyLogging {
  /**
   * calculating last twelve months data of specific knolder.
   *
   * @return last twelve month score with the month and year of the specific knolder.
   */

  override def lastTwelveMonthsScore(knolderId: Int, index: Int): Option[List[TwelveMonthsScore]] = {
    logger.info("calculating last twelve months score")

    @tailrec
    def calculateMonthsScore(scoreList: ListBuffer[TwelveMonthsScore], monthsIndex: Int, id: Int): Option[List[TwelveMonthsScore]] = {
      if (monthsIndex > 12) {
        Option(scoreList.toList)
      } else {
        val monthValue = IndianTime.currentTime.minusMonths(monthsIndex).getMonthValue
        val monthName = IndianTime.currentTime.minusMonths(monthsIndex).getMonth.toString
        val year = IndianTime.currentTime.minusMonths(monthsIndex).getYear
        val monthScore = readContribution.fetchKnoldersWithTwelveMonthContributions(monthValue, year, id)
        monthScore match {
          case Some(contributionScore) => calculateMonthsScore(scoreList :+ TwelveMonthsScore(monthName, year, contributionScore.blogScore,
            contributionScore.knolxScore, contributionScore.webinarScore, contributionScore.techHubScore, contributionScore.osContributionScore,
            contributionScore.conferenceScore, contributionScore.bookScore, contributionScore.researchPaperScore,contributionScore.meetupScore),
            monthsIndex + 1, id)
          case None => calculateMonthsScore(scoreList :+ TwelveMonthsScore(monthName, year, 0, 0, 0, 0, 0, 0, 0, 0 ,0),
            monthsIndex + 1, id)
        }
      }
    }

    calculateMonthsScore(ListBuffer.empty, index, knolderId)
  }
}
