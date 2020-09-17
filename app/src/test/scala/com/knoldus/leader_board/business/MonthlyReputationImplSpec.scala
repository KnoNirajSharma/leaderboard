package com.knoldus.leader_board.business

import com.knoldus.leader_board._
import com.knoldus.leader_board.infrastructure._
import org.mockito.MockitoSugar
import org.scalatest.flatspec.AnyFlatSpec

class MonthlyReputationImplSpec extends AnyFlatSpec with MockitoSugar {
  val mockReadMonthlyReputation: ReadMonthlyReputation = mock[ReadMonthlyReputationImpl]
  val mockKnolderRank: KnolderRank = mock[KnolderRankImpl]
  val mockReadContribution: ReadContribution = mock[ReadContributionImpl]
  val mockKnolderScore: KnolderScore = mock[KnolderScoreImpl]
  val monthlyReputation: MonthlyReputation =
    new MonthlyReputationImpl(mockReadContribution, mockKnolderRank, mockKnolderScore, mockReadMonthlyReputation)

  "get monthly reputation" should "return monthly knolder reputation of each knolder along with their knolder id" in {
    val scorePerKnolder = List(GetScore(1, "Mukesh Gupta", 365))
    val contributionCounts = List(GetContributionCount(1, "Mukesh Gupta", 3, 2, 1, 1, 1, 1, 1, 1))

    when(mockReadContribution.fetchKnoldersWithMonthlyContributions)
      .thenReturn(contributionCounts)

    when(mockKnolderScore.calculateScore(contributionCounts))
      .thenReturn(scorePerKnolder)

    when(mockKnolderRank.calculateRank(scorePerKnolder))
      .thenReturn(List(GetReputation(1, "Mukesh Gupta", 365, 1)))

    when(mockReadMonthlyReputation.fetchKnolderIdFromMonthlyReputation(1))
      .thenReturn(Option(1))

    val reputationOfKnolders = List(KnolderReputation(Some(1), GetReputation(1, "Mukesh Gupta", 365, 1)))

    assert(monthlyReputation.getKnolderMonthlyReputation == reputationOfKnolders)
  }
}
