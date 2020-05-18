package com.knoldus.leader_board.business

import com.knoldus.leader_board.infrastructure.{ReadAllTime, ReadAllTimeImpl}
import com.knoldus.leader_board.{GetAllTime, Reputation}
import com.typesafe.config.ConfigFactory
import org.mockito.MockitoSugar
import org.scalatest.flatspec.AnyFlatSpec

class OverallReputationImplSpec extends AnyFlatSpec with MockitoSugar {
  val mockReadAllTime: ReadAllTime = mock[ReadAllTimeImpl]
  val overallReputation: OverallReputation = new OverallReputationImpl(mockReadAllTime, ConfigFactory.load())

  "calculate reputation" should "give overall reputation of each knolder" in {
    val allTimeData = List(GetAllTime(1, "Mukesh Gupta", Option(2)),
      GetAllTime(2, "Komal Rajpal", Option(1)),
      GetAllTime(3, "Abhishek Baranwal", Option(1)))
    when(mockReadAllTime.fetchAllTimeData)
      .thenReturn(allTimeData)
    assert(overallReputation.calculateReputation ==
      List(Reputation(1, "Mukesh Gupta", 10, 1), Reputation(3, "Abhishek Baranwal", 5, 2),
        Reputation(2, "Komal Rajpal", 5, 2)))
  }

  "calculate reputation" should "give overall reputation of each knolder if one knolder has 0 blog count" in {
    val allTimeData = List(GetAllTime(1, "Mukesh Gupta", Option(2)),
      GetAllTime(2, "Komal Rajpal", Option(1)),
      GetAllTime(3, "Abhishek Baranwal", None))
    when(mockReadAllTime.fetchAllTimeData)
      .thenReturn(allTimeData)
    assert(overallReputation.calculateReputation ==
      List(Reputation(1, "Mukesh Gupta", 10, 1), Reputation(2, "Komal Rajpal", 5, 2),
        Reputation(3, "Abhishek Baranwal", 0, 3)))
  }

  "calculate reputation" should "give overall reputation of each knolder if list of all time data has no element" in {
    val allTimeData = List()
    when(mockReadAllTime.fetchAllTimeData)
      .thenReturn(allTimeData)
    assert(overallReputation.calculateReputation == List(Reputation(0, "", 0, 1)))
  }

  "calculate reputation" should "give overall reputation of each knolder if list of all time data has one element" in {
    val allTimeData = List(GetAllTime(1, "Mukesh Gupta", Option(2)))
    when(mockReadAllTime.fetchAllTimeData)
      .thenReturn(allTimeData)
    assert(overallReputation.calculateReputation == List(Reputation(1, "Mukesh Gupta", 10, 1)))
  }

  "calculate reputation" should "give overall reputation of each knolder if list of all time data has two element" +
    "and first and second blog counts are not equal" in {
    val allTimeData = List(GetAllTime(1, "Mukesh Gupta", Option(2)),
      GetAllTime(2, "Komal Rajpal", Option(1)))
    when(mockReadAllTime.fetchAllTimeData)
      .thenReturn(allTimeData)
    assert(overallReputation.calculateReputation == List(Reputation(1, "Mukesh Gupta", 10, 1),
      Reputation(2, "Komal Rajpal", 5, 2)))
  }

  "calculate reputation" should "give overall reputation of each knolder if list of all time data has two element" +
    "and first and second blog counts are equal" in {
    val allTimeData = List(GetAllTime(1, "Mukesh Gupta", Option(2)),
      GetAllTime(2, "Komal Rajpal", Option(2)),
      GetAllTime(3, "Abhishek Baranwal", Option(1)))
    when(mockReadAllTime.fetchAllTimeData)
      .thenReturn(allTimeData)
    assert(overallReputation.calculateReputation ==
      List(Reputation(2, "Komal Rajpal", 10, 1), Reputation(1, "Mukesh Gupta", 10, 1),
        Reputation(3, "Abhishek Baranwal", 5, 2)))
  }
}
