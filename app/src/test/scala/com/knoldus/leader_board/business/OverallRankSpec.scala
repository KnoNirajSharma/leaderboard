package com.knoldus.leader_board.business

import com.knoldus.leader_board.infrastructure.FetchData
import com.knoldus.leader_board.{GetRank, GetScore}
import org.mockito.MockitoSugar
import org.scalatest.flatspec.AnyFlatSpec

class OverallRankSpec extends AnyFlatSpec with MockitoSugar {
  val mockFetchData: FetchData = mock[FetchData]
  val listOfScores = List(GetScore(1, 10), GetScore(2, 5), GetScore(3, 5))
  when(mockFetchData.fetchScores)
    .thenReturn(listOfScores)
  val overallRank = new OverallRank(mockFetchData)
  "calculate rank" should "give overall rank of each knolder" in {
    assert(overallRank.calculateRank == List(GetRank(1, 1), GetRank(2, 2), GetRank(3, 2)))
  }
}
