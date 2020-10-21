package com.knoldus.leader_board.business

import java.time.Month

import com.knoldus.leader_board.{IndianTime, MonthlyAllTimeReputation}
import org.mockito.MockitoSugar
import org.scalatest.BeforeAndAfterEach
import org.scalatest.wordspec.AnyWordSpecLike

class PastMonthsContributionImplSpec extends AnyWordSpecLike with MockitoSugar with BeforeAndAfterEach {
  val mockMonthlyContribution: KnolderMonthlyContribution = mock[KnolderMonthlyContributionImpl]
  val pastMonthsContribution = new PastMonthsContributionImpl(mockMonthlyContribution)

  "PastMonthsContributionImpl" should {


    "return empty list of monthly contribution when month is less than december 2010" in {

      assert(pastMonthsContribution.getPastMonthsContributionScores(0) == List())
    }

    "return monthly contribution of knolders" in {
      val month = IndianTime.currentTime.minusMonths(1).getMonthValue

      val year = IndianTime.currentTime.minusMonths(1).getYear
      when(mockMonthlyContribution.getKnolderMonthlyContribution(month, year))
        .thenReturn(List(KnolderEachContrbutionScore(1,"mukesh",10,20,15,15,30,100,100,50,Month.of(month).toString,year)))
      assert(pastMonthsContribution.getPastMonthsContributionScores(1) ==List(KnolderEachContrbutionScore(1,"mukesh",10,20,15,15,30,100,100,50,Month.of(month).toString,year)))

    }
  }
}
