package com.knoldus.leader_board.business

import java.time.Month

import com.knoldus.leader_board._
import com.knoldus.leader_board.infrastructure.{FetchMonthlyContributionCount, FetchMonthlyContributionCountImpl}
import org.mockito.MockitoSugar
import org.mockito.ArgumentMatchersSugar.any
import org.scalatest.flatspec.AnyFlatSpec

class KnolderMonthlyContributionImplSpec extends AnyFlatSpec with MockitoSugar {
  val mockKnolderScore: KnolderScore = mock[KnolderScoreImpl]
  val mockFetchMonthlyContribution: FetchMonthlyContributionCount = mock[FetchMonthlyContributionCountImpl]
  val knolderMonthlyContribution: KnolderMonthlyContribution =
    new KnolderMonthlyContributionImpl(mockFetchMonthlyContribution, mockKnolderScore)

  "KnolderMonthlyContributionImpl" should "return monthly contribution of each knolder" in {
    val monthValue = IndianTime.currentTime.getMonthValue
    val year = IndianTime.currentTime.getYear

    val scorePerKnolder = List(KnolderEachContrbutionScore(1, "Mukesh Gupta", 10,40,15,15,30,100,100,50,Month.of(monthValue).toString,year))
    val monthlyContributionCount = List(GetContributionCount(1, "Mukesh Gupta", 2, 2, 1, 1, 1, 1, 1, 1))

    when(mockFetchMonthlyContribution.fetchMonthlyContribution(any,any))
      .thenReturn(monthlyContributionCount)

    when(mockKnolderScore.calculateScore(monthValue,year,monthlyContributionCount))
      .thenReturn(scorePerKnolder)

    assert(knolderMonthlyContribution.getKnolderMonthlyContribution(monthValue,year) == scorePerKnolder)
  }
}
