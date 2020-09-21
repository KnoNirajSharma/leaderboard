package com.knoldus.leader_board.business

import com.knoldus.leader_board._
import com.knoldus.leader_board.infrastructure.{FetchAllTimeAndMonthlyReputation, FetchAllTimeAndMonthlyReputationImpl}
import org.mockito.MockitoSugar
import org.mockito.ArgumentMatchersSugar.any
import org.scalatest.flatspec.AnyFlatSpec

class MonthlyLeadersImplSpec extends AnyFlatSpec with MockitoSugar {
  val mockKnolderRank: KnolderRank = mock[KnolderRankImpl]
  val mockKnolderScore: KnolderScore = mock[KnolderScoreImpl]
  val mockFetchAllTimeAndMonthlyReputation: FetchAllTimeAndMonthlyReputation = mock[FetchAllTimeAndMonthlyReputationImpl]
  val monthlyLeaders: MonthlyLeaders =
    new MonthlyLeadersImpl(mockFetchAllTimeAndMonthlyReputation, mockKnolderScore, mockKnolderRank)

  "MonthlyLeadersImpl" should "return monthly and all time knolder reputation of each knolder" in {
    val scorePerKnolder = List(GetScore(1, "Mukesh Gupta", 360))
    val monthlyAndAllTimeContributionCount = List(GetContributionCount(1, "Mukesh Gupta", 2, 2, 1, 1, 1, 1, 1, 1))

    when(mockFetchAllTimeAndMonthlyReputation.fetchMonthlyReputation(any,any))
      .thenReturn(monthlyAndAllTimeContributionCount)

    when(mockKnolderScore.calculateScore(monthlyAndAllTimeContributionCount))
      .thenReturn(scorePerKnolder)

    when(mockKnolderRank.calculateRank(scorePerKnolder))
      .thenReturn(List(GetReputation(1, "Mukesh Gupta", 360, 1)))

    when(mockFetchAllTimeAndMonthlyReputation.fetchAllTimeReputation(any,any))
      .thenReturn(List(GetContributionCount(1, "Mukesh Gupta", 2, 2, 1, 1, 1, 1, 1, 1)))

    when(mockKnolderScore.calculateScore(monthlyAndAllTimeContributionCount))
      .thenReturn(scorePerKnolder)

    when(mockKnolderRank.calculateRank(scorePerKnolder))
      .thenReturn(List(GetReputation(1, "Mukesh Gupta", 360, 1)))

    val reputationOfKnolders = List(MonthlyAllTimeReputation(1, "Mukesh Gupta", 360, 1, 360, 1,"AUGUST",2020))

    assert(monthlyLeaders.getMonthlyAndAllTimeReputation(8,2020) == reputationOfKnolders)
  }
}
