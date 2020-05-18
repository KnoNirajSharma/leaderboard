package com.knoldus.leader_board.business

import com.knoldus.leader_board.infrastructure.{ReadAllTime, ReadAllTimeImpl}
import com.knoldus.leader_board.{GetAllTimeCount, GetScore}
import com.typesafe.config.ConfigFactory
import org.mockito.MockitoSugar
import org.scalatest.flatspec.AnyFlatSpec

class AllTimeScoreImplSpec extends AnyFlatSpec with MockitoSugar {
  val mockReadAllTime: ReadAllTime = mock[ReadAllTimeImpl]
  val allTimeScoreImpl: AllTimeScore = new AllTimeScoreImpl(mockReadAllTime, ConfigFactory.load())

  "calculate score" should "give score of each knolder" in {
    val allTimeData = List(GetAllTimeCount(1, "Mukesh Gupta", Option(2)),
      GetAllTimeCount(2, "Abhishek Baranwal", Option(1)),
      GetAllTimeCount(3, "Komal Rajpal", None))

    when(mockReadAllTime.fetchAllTimeData)
      .thenReturn(allTimeData)

    val scorePerKnolder = List(GetScore(1, "Mukesh Gupta", 10),
      GetScore(2, "Abhishek Baranwal", 5),
      GetScore(3, "Komal Rajpal", 0))

    assert(allTimeScoreImpl.calculateAllTimeScore == scorePerKnolder)
  }
}
