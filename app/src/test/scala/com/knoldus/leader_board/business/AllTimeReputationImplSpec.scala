package com.knoldus.leader_board.business

import com.knoldus.leader_board._
import com.knoldus.leader_board.infrastructure._
import org.mockito.MockitoSugar
import org.scalatest.flatspec.AnyFlatSpec

class AllTimeReputationImplSpec extends AnyFlatSpec with MockitoSugar {
  val mockKnolderRank: KnolderRank = mock[KnolderRankImpl]
  val mockKnolderScore: KnolderScore = mock[KnolderScoreImpl]
  val mockReadContribution: ReadContribution = mock[ReadContributionImpl]
  val mockReadAllTimeReputation: ReadAllTimeReputation = mock[ReadAllTimeReputationImpl]
  val allTimeReputation: AllTimeReputation =
    new AllTimeReputationImpl(mockReadContribution, mockKnolderRank, mockKnolderScore, mockReadAllTimeReputation)

  "get all time reputation" should "return knolder reputation of each knolder along with their knolder id" in {
    val scorePerKnolder = List(GetScore(1, "Mukesh Gupta", 360))
    val contributionScore = List(KnolderContributionScore(1, "Mukesh Gupta", Option(10), Option(40), Option(15), Option(15), Option(30), Option(100),Option(100),Option(50),Option(30)))

    when(mockReadContribution.fetchKnoldersWithContributions)
      .thenReturn(contributionScore)

    when(mockKnolderScore.calculateScore(contributionScore))
      .thenReturn(scorePerKnolder)

    when(mockKnolderRank.calculateRank(scorePerKnolder))
      .thenReturn(List(GetReputation(1, "Mukesh Gupta", 360, 1)))

    when(mockReadAllTimeReputation.fetchKnolderIdFromAllTimeReputation(1))
      .thenReturn(Option(1))

    val reputationOfKnolders = List(KnolderReputation(Some(1), GetReputation(1, "Mukesh Gupta", 360, 1)))

    assert(allTimeReputation.getKnolderReputation == reputationOfKnolders)
  }
}
