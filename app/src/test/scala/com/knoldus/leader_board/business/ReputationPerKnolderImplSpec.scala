package com.knoldus.leader_board.business

import com.knoldus.leader_board._
import com.knoldus.leader_board.infrastructure._
import org.mockito.MockitoSugar
import org.scalatest.flatspec.AnyFlatSpec

class ReputationPerKnolderImplSpec extends AnyFlatSpec with MockitoSugar {
  val mockReadAllTimeReputation: ReadAllTimeReputation = mock[ReadAllTimeReputationImpl]
  val mockOverallReputation:OverallReputation=mock[OverallReputationImpl]
  val reputationPerKnolder:ReputationPerKnolder =
    new ReputationPerKnolderImpl(mockOverallReputation,mockReadAllTimeReputation)

  "get knolder Reputation" should "return knolder reputation of each knolder along with their knolder id" in {

    when(mockOverallReputation.calculateReputation)
      .thenReturn(List(Reputation(1,"Mukesh Gupta", 10, 1)))
    val knolderReputation = List(KnolderReputation(Some(1),Reputation(1,"Mukesh Gupta", 10, 1)))

    when(mockReadAllTimeReputation.knolderIdFromAllTimeReputation(1))
      .thenReturn(Option(1))

    assert(reputationPerKnolder.getKnolderReputation == knolderReputation)
  }
}