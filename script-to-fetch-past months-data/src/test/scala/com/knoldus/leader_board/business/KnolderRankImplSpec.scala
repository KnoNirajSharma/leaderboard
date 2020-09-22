package com.knoldus.leader_board.business

import com.knoldus.leader_board.{GetReputation, GetScore}
import org.scalatest.flatspec.AnyFlatSpec

class KnolderRankImplSpec extends AnyFlatSpec {
  val knolderRank: KnolderRank = new KnolderRankImpl

  "calculate rank" should "give reputation of each knolder" in {
    val scorePerKnolder = List(GetScore(1, "Mukesh Gupta", 10),
      GetScore(2, "Abhishek Baranwal", 5),
      GetScore(3, "Komal Rajpal", 5))

    val reputationPerKnolder = List(GetReputation(1, "Mukesh Gupta", 10, 1),
      GetReputation(2, "Abhishek Baranwal", 5, 2),
      GetReputation(3, "Komal Rajpal", 5, 2))

    assert(knolderRank.calculateRank(scorePerKnolder) == reputationPerKnolder)
  }

  "calculate rank" should "give reputation of each knolder if one knolder has 0 blog count" in {
    val scorePerKnolder = List(GetScore(1, "Mukesh Gupta", 10),
      GetScore(2, "Abhishek Baranwal", 5),
      GetScore(3, "Komal Rajpal", 0))

    val reputationPerKnolder = List(GetReputation(1, "Mukesh Gupta", 10, 1),
      GetReputation(2, "Abhishek Baranwal", 5, 2),
      GetReputation(3, "Komal Rajpal", 0, 3))

    assert(knolderRank.calculateRank(scorePerKnolder) == reputationPerKnolder)
  }

  "calculate rank" should "give reputation of each knolder if list of scores has no element" in {
    val scorePerKnolder = List.empty

    val reputationPerKnolder = List(GetReputation(0, "", 0, 1))

    assert(knolderRank.calculateRank(scorePerKnolder) == reputationPerKnolder)
  }

  "calculate rank" should "give reputation of each knolder if list of scores has one element" in {
    val scorePerKnolder = List(GetScore(1, "Mukesh Gupta", 10))

    val reputationPerKnolder = List(GetReputation(1, "Mukesh Gupta", 10, 1))

    assert(knolderRank.calculateRank(scorePerKnolder) == reputationPerKnolder)
  }

  "calculate rank" should "give reputation of each knolder if list of scores has two elements" +
    "and first and second scores are not equal" in {
    val scorePerKnolder = List(GetScore(1, "Mukesh Gupta", 10),
      GetScore(2, "Abhishek Baranwal", 5))

    val reputationPerKnolder = List(GetReputation(1, "Mukesh Gupta", 10, 1),
      GetReputation(2, "Abhishek Baranwal", 5, 2))

    assert(knolderRank.calculateRank(scorePerKnolder) == reputationPerKnolder)
  }

  "calculate rank" should "give reputation of each knolder if list of scores has two elements" +
    "and first and second blog counts are equal" in {
    val scorePerKnolder = List(GetScore(1, "Mukesh Gupta", 10),
      GetScore(2, "Abhishek Baranwal", 10))

    val reputationPerKnolder = List(GetReputation(1, "Mukesh Gupta", 10, 1),
      GetReputation(2, "Abhishek Baranwal", 10, 1))

    assert(knolderRank.calculateRank(scorePerKnolder) == reputationPerKnolder)
  }

  "calculate rank" should "give reputation of each knolder if list of scores has more than two elements" +
    "and first and second scores are not equal" in {
    val scorePerKnolder = List(GetScore(1, "Mukesh Gupta", 10),
      GetScore(2, "Abhishek Baranwal", 5),
      GetScore(3, "Komal Rajpal", 5))

    val reputationPerKnolder = List(GetReputation(1, "Mukesh Gupta", 10, 1),
      GetReputation(2, "Abhishek Baranwal", 5, 2),
      GetReputation(3, "Komal Rajpal", 5, 2))

    assert(knolderRank.calculateRank(scorePerKnolder) == reputationPerKnolder)
  }

  "calculate rank" should "give reputation of each knolder if list of scores has more than two elements" +
    "and first and second blog counts are equal" in {
    val scorePerKnolder = List(GetScore(1, "Mukesh Gupta", 10),
      GetScore(2, "Abhishek Baranwal", 10),
      GetScore(3, "Komal Rajpal", 5))

    val reputationPerKnolder = List(GetReputation(1, "Mukesh Gupta", 10, 1),
      GetReputation(2, "Abhishek Baranwal", 10, 1),
      GetReputation(3, "Komal Rajpal", 5, 2))

    assert(knolderRank.calculateRank(scorePerKnolder) == reputationPerKnolder)
  }
}
