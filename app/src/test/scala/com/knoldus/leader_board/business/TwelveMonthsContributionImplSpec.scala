package com.knoldus.leader_board.business

import com.knoldus.leader_board.infrastructure.{ReadContribution, ReadContributionImpl}
import com.knoldus.leader_board.{ContributionScore, IndianTime, TwelveMonthsScore}
import org.mockito.MockitoSugar
import org.scalatest.BeforeAndAfterEach
import org.scalatest.wordspec.AnyWordSpecLike

class TwelveMonthsContributionImplSpec extends AnyWordSpecLike with MockitoSugar with BeforeAndAfterEach {
  val mockReadContribution: ReadContribution = mock[ReadContributionImpl]
  val twelveMonthsContribution = new TwelveMonthsContributionImpl(mockReadContribution)

  "TwelveMonthsContributionImpl" should {


    "return list of scores of 12 month of particular knolder if month is greater than 12" in {

      assert(twelveMonthsContribution.lastTwelveMonthsScore(1, 13) == Option(List()))
    }
    "return list of scores of 12 month of particular knolder if month is less than 12" in {
      val month = IndianTime.currentTime.minusMonths(12).getMonthValue
      val monthName = IndianTime.currentTime.minusMonths(12).getMonth.toString

      val year = IndianTime.currentTime.minusMonths(12).getYear
      when(mockReadContribution.fetchKnoldersWithTwelveMonthContributions(month, year, 1))
        .thenReturn(Option(ContributionScore(30, 20, 40, 50, 60, 100, 100, 50)))
      assert(twelveMonthsContribution.lastTwelveMonthsScore(1, 12) == Option(List(TwelveMonthsScore(monthName, 2019, 30, 20, 40, 50, 60, 100, 100, 50))))
    }

    "return list of scores of 12 month of particular knolder if month is less than 12 when that knolder last month data not exist" in {
      val month = IndianTime.currentTime.minusMonths(12).getMonthValue
      val monthName = IndianTime.currentTime.minusMonths(12).getMonth.toString
      val year = IndianTime.currentTime.minusMonths(12).getYear
      when(mockReadContribution.fetchKnoldersWithTwelveMonthContributions(month, year, 1))
        .thenReturn(None)
      assert(twelveMonthsContribution.lastTwelveMonthsScore(1, 12) == Option(List(TwelveMonthsScore(monthName, 2019, 0,0,0,0,0,0,0,0))))
    }
  }
}
