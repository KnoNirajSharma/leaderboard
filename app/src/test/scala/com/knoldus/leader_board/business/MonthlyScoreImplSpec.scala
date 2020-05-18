package com.knoldus.leader_board.business

import com.knoldus.leader_board.infrastructure.{ReadBlog, ReadBlogImpl}
import com.knoldus.leader_board.{GetMonthlyCount, GetScore}
import com.typesafe.config.ConfigFactory
import org.mockito.MockitoSugar
import org.scalatest.flatspec.AnyFlatSpec

class MonthlyScoreImplSpec extends AnyFlatSpec with MockitoSugar {
  val mockReadBlog: ReadBlog = mock[ReadBlogImpl]
  val monthlyScore = new MonthlyScoreImpl(mockReadBlog, ConfigFactory.load())

  "calculate monthly score" should "give monthly score of each knolder" in {
    val scorePerKnolder = List(GetScore(1, "Mukesh Gupta", 15), GetScore(2, "Anjali", 5))

    when(mockReadBlog.fetchKnoldersWithMonthlyBlogs)
      .thenReturn(List(GetMonthlyCount(1, "Mukesh Gupta", 3), GetMonthlyCount(2, "Anjali", 1)))

    assert(monthlyScore.calculateMonthlyScore == scorePerKnolder)
  }
}
