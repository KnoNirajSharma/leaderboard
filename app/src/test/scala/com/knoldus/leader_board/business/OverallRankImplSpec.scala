package com.knoldus.leader_board.business

import com.knoldus.leader_board.infrastructure.FetchDataImpl
import com.knoldus.leader_board.{GetRank, GetScore}
import org.mockito.MockitoSugar
import org.scalatest.flatspec.AnyFlatSpec

class OverallRankImplSpec extends AnyFlatSpec with MockitoSugar {
  val mockFetchData: FetchDataImpl = mock[FetchDataImpl]
  val overallRank: OverallRankImpl = new OverallRankImpl(mockFetchData)

  "calculate rank" should "give overall rank of each knolder" in {
    val listOfScores = List(GetScore(1, 10), GetScore(2, 5), GetScore(3, 5))
    when(mockFetchData.fetchScores)
      .thenReturn(listOfScores)
    assert(overallRank.calculateRank == List(GetRank(1, 1), GetRank(2, 2), GetRank(3, 2)))
  }

  "calculate rank" should "give overall rank of each knolder if list of scores has no element" in {
    val listOfScores = List()
    when(mockFetchData.fetchScores)
      .thenReturn(listOfScores)
    assert(overallRank.calculateRank == List(GetRank(0, 1)))
  }

  "calculate rank" should "give overall rank of each knolder if list of scores has one element" in {
    val listOfScores = List(GetScore(1, 10))
    when(mockFetchData.fetchScores)
      .thenReturn(listOfScores)
    assert(overallRank.calculateRank == List(GetRank(1, 1)))
  }

  "calculate rank" should "give overall rank of each knolder if list of scores has two elements" +
    "and first and second scores are not equal" in {
    val listOfScores = List(GetScore(1, 10), GetScore(2, 5))
    when(mockFetchData.fetchScores)
      .thenReturn(listOfScores)
    assert(overallRank.calculateRank == List(GetRank(1, 1), GetRank(2, 2)))
  }

  "calculate rank" should "give overall rank of each knolder if list of scores has more than two elements" +
    "and first and second scores are equal" in {
    val listOfScores = List(GetScore(1, 10), GetScore(2, 10), GetScore(3, 5))
    when(mockFetchData.fetchScores)
      .thenReturn(listOfScores)
    assert(overallRank.calculateRank == List(GetRank(1, 1), GetRank(2, 1), GetRank(3, 2)))
  }
}
