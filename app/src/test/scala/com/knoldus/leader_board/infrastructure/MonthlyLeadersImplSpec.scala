package com.knoldus.leader_board.infrastructure

import com.knoldus.leader_board._
import com.knoldus.leader_board.business.{KnolderRank, KnolderRankImpl, KnolderScore, KnolderScoreImpl, MonthlyLeaders, MonthlyLeadersImpl}
import org.mockito.MockitoSugar
import org.scalatest.flatspec.AnyFlatSpec

class MonthlyLeadersImplSpec extends AnyFlatSpec with MockitoSugar {
  val mockKnolderRank: KnolderRank = mock[KnolderRankImpl]
  val mockKnolderScore: KnolderScore = mock[KnolderScoreImpl]
  val mockReadContribution: ReadContribution = mock[ReadContributionImpl]
  val mockFetchAllTimeReputation: FetchAllTimeReputation = mock[FetchAllTimeReputationImpl]
  val monthlyLeaders: MonthlyLeaders =
    new MonthlyLeadersImpl(mockReadContribution, mockFetchAllTimeReputation, mockKnolderScore, mockKnolderRank)

  "MonthlyLeadersImpl" should "return monthly knolder reputation of each knolder" in {
    val scorePerKnolder = List(GetScore(1, "Mukesh Gupta", 360))
    val contributionCount = List(GetContributionCount(1, "Mukesh Gupta", 2, 2, 1, 1, 1, 1, 1, 1))

    when(mockReadContribution.fetchKnoldersWithQuarterThirdMonthContributions)
      .thenReturn(contributionCount)

    when(mockKnolderScore.calculateScore(contributionCount))
      .thenReturn(scorePerKnolder)

    when(mockKnolderRank.calculateRank(scorePerKnolder))
      .thenReturn(List(GetReputation(1, "Mukesh Gupta", 360, 1)))

    when(mockFetchAllTimeReputation.fetchAllTimeReputation)
      .thenReturn(List(GetReputation(1, "Mukesh Gupta", 800, 1)))

    val reputationOfKnolders = List(MonthlyAllTimeReputation(1, "Mukesh Gupta", 800, 1, 360, 1))

    assert(monthlyLeaders.getMonthlyAndAllTimeReputation == reputationOfKnolders)
  }
}
