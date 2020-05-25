package com.knoldus.leader_board.business

import com.knoldus.leader_board._
import com.knoldus.leader_board.infrastructure._
import org.mockito.MockitoSugar
import org.scalatest.flatspec.AnyFlatSpec

class QuarterlyReputationImplSpec extends AnyFlatSpec with MockitoSugar {
  val mockKnolderScore: KnolderScore = mock[KnolderScoreImpl]
  val mockReadBlog: ReadBlog = mock[ReadBlogImpl]
  val mockReadQuarterlyReputation: ReadQuarterlyReputation = mock[ReadQuarterlyReputationImpl]
  val quarterlyReputation: QuarterlyReputation =
    new QuarterlyReputationImpl(mockReadBlog, mockKnolderScore, mockReadQuarterlyReputation)

  "get quarterly reputation" should "return knolder reputation of each knolder along with their knolder id" in {
    val scoresForFirstMonth = List(GetScore(1, "Mukesh Gupta", 15), GetScore(2, "anjali", 10))
    val firstMonthBlogCount = List(GetCount(1, "Mukesh Gupta", 3), GetCount(2, "anjali", 2))

    when(mockReadBlog.fetchKnoldersWithQuarterFirstMonthBlogs)
      .thenReturn(List(GetCount(1, "Mukesh Gupta", 3), GetCount(2, "anjali", 2)))
    when(mockKnolderScore.calculateScore(firstMonthBlogCount))
      .thenReturn(scoresForFirstMonth)

    val scoresForSecondMonth = List(GetScore(1, "Mukesh Gupta", 20), GetScore(2, "anjali", 10))
    val secondMonthBlogCount = List(GetCount(1, "Mukesh Gupta", 4), GetCount(2, "anjali", 2))

    when(mockReadBlog.fetchKnoldersWithQuarterSecondMonthBlogs)
      .thenReturn(List(GetCount(1, "Mukesh Gupta", 4), GetCount(2, "anjali", 2)))
    when(mockKnolderScore.calculateScore(secondMonthBlogCount))
      .thenReturn(scoresForSecondMonth)

    val scoresForThirdMonth = List(GetScore(1, "Mukesh Gupta", 20), GetScore(2, "anjali", 15))
    val thirdMonthBlogCount = List(GetCount(1, "Mukesh Gupta", 4), GetCount(2, "anjali", 3))

    when(mockReadBlog.fetchKnoldersWithQuarterThirdMonthBlogs)
      .thenReturn(List(GetCount(1, "Mukesh Gupta", 4), GetCount(2, "anjali", 3)))
    when(mockKnolderScore.calculateScore(thirdMonthBlogCount))
      .thenReturn(scoresForThirdMonth)

    when(mockReadQuarterlyReputation.fetchKnolderIdFromQuarterlyReputation(1))
      .thenReturn(Option(1))
    when(mockReadQuarterlyReputation.fetchKnolderIdFromQuarterlyReputation(2))
      .thenReturn(Option(2))

    val reputationOfKnolders = List(KnolderStreak(Some(1), Streak(1, "Mukesh Gupta", "15-20-20")),
      KnolderStreak(Some(2), Streak(2, "anjali", "10-10-15")))

    assert(quarterlyReputation.getKnolderQuarterlyReputation == reputationOfKnolders)
  }
}
