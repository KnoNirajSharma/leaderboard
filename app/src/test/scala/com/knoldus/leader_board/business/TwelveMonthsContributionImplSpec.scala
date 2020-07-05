package com.knoldus.leader_board.business

import java.sql.Timestamp

import com.knoldus.leader_board.{Blog, TwelveMonthsScore}
import com.knoldus.leader_board.infrastructure.{ReadContribution, ReadContributionImpl}
import org.mockito.{Mockito, MockitoSugar}
import org.scalatest.BeforeAndAfterEach
import org.scalatest.wordspec.AnyWordSpecLike

class TwelveMonthsContributionImplSpec extends AnyWordSpecLike with MockitoSugar with BeforeAndAfterEach {
  val mockReadContribution: ReadContribution = mock[ReadContributionImpl]
  val twelveMonthsContribution = new TwelveMonthsContributionImpl(mockReadContribution)

  "TwelveMonthsContributionImpl" should {


    "return list of scores of 12 month of particular knolder if month is greater than 12" in {

      assert(twelveMonthsContribution.lastTwelveMonthsScore(1,13)==Option(List()))
    }
    "return list of scores of 12 month of particular knolder if month is less than 12" in {
      when(mockReadContribution.fetchKnoldersWithTwelveMonthContributions(7,2019,1))
        .thenReturn(Option(30))
      assert(twelveMonthsContribution.lastTwelveMonthsScore(1,12)==Option(List(TwelveMonthsScore("JULY", 2019,30))))
    }
  }
}