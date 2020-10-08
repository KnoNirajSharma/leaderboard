package com.knoldus.leader_board.business

import com.knoldus.leader_board._
import com.knoldus.leader_board.infrastructure._
import org.mockito.MockitoSugar
import org.scalatest.flatspec.AnyFlatSpec

class QuarterlyReputationImplSpec extends AnyFlatSpec with MockitoSugar {
  val mockKnolderScore: KnolderScore = mock[KnolderScoreImpl]
  val mockReadContribution: ReadContribution = mock[ReadContributionImpl]
  val mockReadQuarterlyReputation: ReadQuarterlyReputation = mock[ReadQuarterlyReputationImpl]
  val quarterlyReputation: QuarterlyReputation =
    new QuarterlyReputationImpl(mockReadContribution, mockKnolderScore, mockReadQuarterlyReputation)

  "get quarterly reputation" should "return knolder reputation of each knolder along with their knolder id" in {
    val scoresForFirstMonth = List(GetScore(1, "Mukesh Gupta", 365), GetScore(2, "anjali", 330))
    val firstMonthContributionScores = List(KnolderContributionScore(1, "Mukesh Gupta", Option(15), Option(40), Option(15), Option(15), Option(30), Option(100),Option(100),Option(50)),
      KnolderContributionScore(2, "anjali", Option(10), Option(10), Option(15), Option(15), Option(30), Option(100),Option(100),Option(50)))

    when(mockReadContribution.fetchKnoldersWithQuarterFirstMonthContributions)
      .thenReturn(List(KnolderContributionScore(1, "Mukesh Gupta", Option(15), Option(40), Option(15), Option(15), Option(30), Option(100),Option(100),Option(50)),
        KnolderContributionScore(2, "anjali", Option(10), Option(10), Option(15), Option(15), Option(30), Option(100),Option(100),Option(50))))
    when(mockKnolderScore.calculateScore(firstMonthContributionScores))
      .thenReturn(scoresForFirstMonth)

    val scoresForSecondMonth = List(GetScore(1, "Mukesh Gupta", 340), GetScore(2, "anjali", 330))
    val secondMonthContributionScores = List(KnolderContributionScore(1, "Mukesh Gupta", Option(10), Option(20), Option(15), Option(15), Option(30), Option(100),Option(100),Option(50)),
      KnolderContributionScore(2, "anjali", Option(10), Option(10), Option(15), Option(15), Option(30), Option(100),Option(100),Option(50)))

    when(mockReadContribution.fetchKnoldersWithQuarterSecondMonthContributions)
      .thenReturn(List(KnolderContributionScore(1, "Mukesh Gupta", Option(10), Option(20), Option(15), Option(15), Option(30), Option(100),Option(100),Option(50)),
        KnolderContributionScore(2, "anjali",  Option(10), Option(10), Option(15), Option(15), Option(30), Option(100),Option(100),Option(50))))
    when(mockKnolderScore.calculateScore(secondMonthContributionScores))
      .thenReturn(scoresForSecondMonth)

    val scoresForThirdMonth = List(GetScore(1, "Mukesh Gupta", 340), GetScore(2, "anjali", 335))
    val thirdMonthContributionScore = List(KnolderContributionScore(1, "Mukesh Gupta", Option(10), Option(20), Option(15), Option(15), Option(30), Option(100),Option(100),Option(50)),
      KnolderContributionScore(2, "anjali", Option(5), Option(10), Option(15), Option(15), Option(30), Option(100),Option(100),Option(50)))

    when(mockReadContribution.fetchKnoldersWithQuarterThirdMonthContributions)
      .thenReturn(List(KnolderContributionScore(1, "Mukesh Gupta", Option(10), Option(20), Option(15), Option(15), Option(30), Option(100),Option(100),Option(50)),
        KnolderContributionScore(2, "anjali", Option(5), Option(10), Option(15), Option(15), Option(30), Option(100),Option(100),Option(50))))
    when(mockKnolderScore.calculateScore(thirdMonthContributionScore))
      .thenReturn(scoresForThirdMonth)

    when(mockReadQuarterlyReputation.fetchKnolderIdFromQuarterlyReputation(1))
      .thenReturn(Option(1))
    when(mockReadQuarterlyReputation.fetchKnolderIdFromQuarterlyReputation(2))
      .thenReturn(Option(2))

    val reputationOfKnolders = List(KnolderStreak(Some(1), GetStreak(1, "Mukesh Gupta", "340-340-365")),
      KnolderStreak(Some(2), GetStreak(2, "anjali", "335-330-330")))

    assert(quarterlyReputation.getKnolderQuarterlyReputation == reputationOfKnolders)
  }
}
