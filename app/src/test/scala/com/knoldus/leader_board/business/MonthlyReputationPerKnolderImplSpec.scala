package com.knoldus.leader_board.business

import com.knoldus.leader_board._
import com.knoldus.leader_board.infrastructure._
import org.mockito.MockitoSugar
import org.scalatest.flatspec.AnyFlatSpec

class MonthlyReputationPerKnolderImplSpec extends AnyFlatSpec with MockitoSugar {
  val mockReadMonthlyReputation: ReadMonthlyReputation = mock[ReadMonthlyReputationImpl]
  val mockKnolderRank: KnolderRank = mock[KnolderRankImpl]
  val mockMonthlyScore: MonthlyScore = mock[MonthlyScoreImpl]
  val monthlyReputationPerKnolder: MonthlyReputationPerKnolder =
    new MonthlyReputationPerKnolderImpl(mockKnolderRank, mockReadMonthlyReputation, mockMonthlyScore)

  "get monthly reputation" should "return monthly knolder reputation of each knolder along with their knolder id" in {
    val scorePerKnolder = List(GetScore(1, "Mukesh Gupta", 15))

    when(mockMonthlyScore.calculateMonthlyScore)
      .thenReturn(scorePerKnolder)

    when(mockKnolderRank.calculateRank(scorePerKnolder))
      .thenReturn(List(Reputation(1, "Mukesh Gupta", 15, 1)))

    when(mockReadMonthlyReputation.fetchKnolderIdFromMonthlyReputation(1))
      .thenReturn(Option(1))

    val knolderReputation = List(KnolderReputation(Some(1), Reputation(1, "Mukesh Gupta", 15, 1)))

    assert(monthlyReputationPerKnolder.getKnolderMonthlyReputation == knolderReputation)
  }
}
