package com.knoldus.leader_board.business

import com.knoldus.leader_board.{GetCount, GetScore}
import com.typesafe.config.ConfigFactory
import org.mockito.MockitoSugar
import org.scalatest.flatspec.AnyFlatSpec

class KnolderScoreImplSpec extends AnyFlatSpec with MockitoSugar {
  val knolderScore = new KnolderScoreImpl(ConfigFactory.load())

  "calculate score" should "give score of each knolder" in {
    val scorePerKnolder = List(GetScore(1, "Mukesh Gupta", 85), GetScore(2, "Anjali", 75))
    val counts = List(GetCount(1, "Mukesh Gupta", 3, 2, 1, 1), GetCount(2, "Anjali", 1, 2, 1, 1))

    assert(knolderScore.calculateScore(counts) == scorePerKnolder)
  }
}
