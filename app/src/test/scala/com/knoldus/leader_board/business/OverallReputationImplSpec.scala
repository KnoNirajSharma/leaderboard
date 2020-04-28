package com.knoldus.leader_board.business

import com.knoldus.leader_board.infrastructure.FetchDataImpl
import com.knoldus.leader_board.{GetAllTime, Reputation}
import com.typesafe.config.ConfigFactory
import org.mockito.MockitoSugar
import org.scalatest.flatspec.AnyFlatSpec

class OverallReputationImplSpec extends AnyFlatSpec with MockitoSugar {
  val mockFetchData: FetchDataImpl = mock[FetchDataImpl]
  val overallReputation: OverallReputation = new OverallReputationImpl(mockFetchData, ConfigFactory.load())

  "calculate reputation" should "give overall reputation of each knolder" in {
    val allTimeData = List(GetAllTime("Mukesh Gupta", Option(2)),
      GetAllTime("Komal Rajpal", Option(1)),
      GetAllTime("Abhishek Baranwal", Option(1)))
    when(mockFetchData.fetchAllTimeData)
      .thenReturn(allTimeData)
    assert(overallReputation.calculateReputation ==
      List(Reputation("Mukesh Gupta", 10, 1), Reputation("Abhishek Baranwal", 5, 2), Reputation("Komal Rajpal", 5, 2)))
  }

  "calculate reputation" should "give overall reputation of each knolder if one knolder has 0 blog count" in {
    val allTimeData = List(GetAllTime("Mukesh Gupta", Option(2)),
      GetAllTime("Komal Rajpal", Option(1)),
      GetAllTime("Abhishek Baranwal", None))
    when(mockFetchData.fetchAllTimeData)
      .thenReturn(allTimeData)
    assert(overallReputation.calculateReputation ==
      List(Reputation("Mukesh Gupta", 10, 1), Reputation("Komal Rajpal", 5, 2), Reputation("Abhishek Baranwal", 0, 3)))
  }

  "calculate reputation" should "give overall reputation of each knolder if list of all time data has no element" in {
    val allTimeData = List()
    when(mockFetchData.fetchAllTimeData)
      .thenReturn(allTimeData)
    assert(overallReputation.calculateReputation == List(Reputation("", 0, 1)))
  }

  "calculate reputation" should "give overall reputation of each knolder if list of all time data has one element" in {
    val allTimeData = List(GetAllTime("Mukesh Gupta", Option(2)))
    when(mockFetchData.fetchAllTimeData)
      .thenReturn(allTimeData)
    assert(overallReputation.calculateReputation == List(Reputation("Mukesh Gupta", 10, 1)))
  }

  "calculate reputation" should "give overall reputation of each knolder if list of all time data has two element" +
    "and first and second blog counts are not equal" in {
    val allTimeData = List(GetAllTime("Mukesh Gupta", Option(2)),
      GetAllTime("Komal Rajpal", Option(1)))
    when(mockFetchData.fetchAllTimeData)
      .thenReturn(allTimeData)
    assert(overallReputation.calculateReputation == List(Reputation("Mukesh Gupta", 10, 1),
      Reputation("Komal Rajpal", 5, 2)))
  }

  "calculate reputation" should "give overall reputation of each knolder if list of all time data has two element" +
    "and first and second blog counts are equal" in {
    val allTimeData = List(GetAllTime("Mukesh Gupta", Option(2)),
      GetAllTime("Komal Rajpal", Option(2)),
      GetAllTime("Abhishek Baranwal", Option(1)))
    when(mockFetchData.fetchAllTimeData)
      .thenReturn(allTimeData)
    assert(overallReputation.calculateReputation ==
      List(Reputation("Komal Rajpal", 10, 1), Reputation("Mukesh Gupta", 10, 1), Reputation("Abhishek Baranwal", 5, 2)))
  }
}
