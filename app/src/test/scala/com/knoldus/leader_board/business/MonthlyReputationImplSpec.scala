package com.knoldus.leader_board.business

import com.knoldus.leader_board._
import com.knoldus.leader_board.infrastructure._
import org.mockito.MockitoSugar
import org.scalatest.flatspec.AnyFlatSpec

class MonthlyReputationImplSpec extends AnyFlatSpec with MockitoSugar {
  val mockReadMonthlyReputation: ReadMonthlyReputation = mock[ReadMonthlyReputationImpl]
  val mockKnolderRank: KnolderRank = mock[KnolderRankImpl]
  val mockReadBlog: ReadBlog = mock[ReadBlogImpl]
  val mockKnolderScore: KnolderScore = mock[KnolderScoreImpl]
  val monthlyReputation: MonthlyReputation =
    new MonthlyReputationImpl(mockReadBlog, mockKnolderRank, mockKnolderScore, mockReadMonthlyReputation)

  "get monthly reputation" should "return monthly knolder reputation of each knolder along with their knolder id" in {
    val scorePerKnolder = List(GetScore(1, "Mukesh Gupta", 15))
    val blogCounts = List(GetCount(1, "Mukesh Gupta", 3))

    when(mockReadBlog.fetchKnoldersWithMonthlyBlogs)
      .thenReturn(blogCounts)

    when(mockKnolderScore.calculateScore(blogCounts))
      .thenReturn(scorePerKnolder)

    when(mockKnolderRank.calculateRank(scorePerKnolder))
      .thenReturn(List(GetReputation(1, "Mukesh Gupta", 15, 1)))

    when(mockReadMonthlyReputation.fetchKnolderIdFromMonthlyReputation(1))
      .thenReturn(Option(1))

    val reputationOfKnolders = List(KnolderReputation(Some(1), GetReputation(1, "Mukesh Gupta", 15, 1)))

    assert(monthlyReputation.getKnolderMonthlyReputation == reputationOfKnolders)
  }
}
