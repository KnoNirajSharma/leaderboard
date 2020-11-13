package com.knoldus.leader_board.business

import com.knoldus.leader_board._
import com.knoldus.leader_board.infrastructure._
import org.mockito.MockitoSugar
import org.scalatest.flatspec.AnyFlatSpec

class MonthlyLeadersImplSpec extends AnyFlatSpec with MockitoSugar {
  val mockKnolderRank: KnolderRank = mock[KnolderRankImpl]
  val mockKnolderScore: KnolderScore = mock[KnolderScoreImpl]
  val mockReadContribution: ReadContribution = mock[ReadContributionImpl]
  val mockFetchAllTimeReputation: FetchAllTimeReputation = mock[FetchAllTimeReputationImpl]
  val monthlyLeaders: MonthlyLeaders =
    new MonthlyLeadersImpl(mockReadContribution, mockFetchAllTimeReputation, mockKnolderScore, mockKnolderRank)

  "MonthlyLeadersImpl" should "return monthly and all timeknolder reputation of each knolder" in {
    val scorePerKnolder = List(GetScore(1, "Mukesh Gupta", 360))
    val contributionScores = List(KnolderContributionScore(1, "Mukesh Gupta", Option(10), Option(10), Option(15), Option(15), Option(60), Option(100), Option(100), Option(50), Option(30)))

    when(mockReadContribution.fetchKnoldersWithQuarterThirdMonthContributions)
      .thenReturn(contributionScores)

    when(mockKnolderScore.calculateScore(contributionScores))
      .thenReturn(scorePerKnolder)

    when(mockKnolderRank.calculateRank(scorePerKnolder))
      .thenReturn(List(GetReputation(1, "Mukesh Gupta", 360, 1)))

    when(mockFetchAllTimeReputation.fetchAllTimeReputation)
      .thenReturn(List(GetReputation(1, "Mukesh Gupta", 800, 1)))

    val reputationOfKnolders = List(MonthlyAllTimeReputation(1, "Mukesh Gupta", 800, 1, 360, 1))

    assert(monthlyLeaders.getMonthlyAndAllTimeReputation == reputationOfKnolders)
  }
}
