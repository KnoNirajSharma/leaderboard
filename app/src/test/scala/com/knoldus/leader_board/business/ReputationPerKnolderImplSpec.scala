package com.knoldus.leader_board.business

import com.knoldus.leader_board._
import com.knoldus.leader_board.infrastructure._
import org.mockito.MockitoSugar
import org.scalatest.flatspec.AnyFlatSpec

class ReputationPerKnolderImplSpec extends AnyFlatSpec with MockitoSugar {
  val mockKnolderRank: KnolderRank = mock[KnolderRankImpl]
  val mockAllTimeScore: AllTimeScore = mock[AllTimeScoreImpl]
  val mockReadAllTimeReputation: ReadAllTimeReputation = mock[ReadAllTimeReputationImpl]
  val reputationPerKnolder: ReputationPerKnolder =
    new ReputationPerKnolderImpl(mockKnolderRank, mockAllTimeScore, mockReadAllTimeReputation)

  "get knolder reputation" should "return knolder reputation of each knolder along with their knolder id" in {
    val scores = List(GetScore(1, "Mukesh Gupta", 10))
    when(mockAllTimeScore.calculateAllTimeScore)
      .thenReturn(scores)

    val reputations = List(Reputation(1, "Mukesh Gupta", 10, 1))
    when(mockKnolderRank.calculateRank(scores))
      .thenReturn(reputations)

    when(mockReadAllTimeReputation.fetchKnolderIdFromAllTimeReputation(1))
      .thenReturn(Option(1))

    val knolderReputation = List(KnolderReputation(Some(1), Reputation(1, "Mukesh Gupta", 10, 1)))

    assert(reputationPerKnolder.getKnolderReputation == knolderReputation)
  }
}
