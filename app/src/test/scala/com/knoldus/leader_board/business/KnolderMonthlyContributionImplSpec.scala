package com.knoldus.leader_board.business

import com.knoldus.leader_board._
import com.knoldus.leader_board.infrastructure._
import org.mockito.ArgumentMatchersSugar.any
import org.mockito.MockitoSugar
import org.scalatest.flatspec.AnyFlatSpec


class KnolderMonthlyContributionImplSpec extends AnyFlatSpec with MockitoSugar {
  val mockKnolderScore: KnolderScore = mock[KnolderScoreImpl]
  val mockReadContribution: ReadContribution = mock[ReadContributionImpl]
  val mockFetchMonthlyKnolderContribution:FetchMonthlyKnolderContribution = mock[FetchMonthlyKnolderContributionImpl]
  val knolderMonthlyContribution:KnolderMonthlyContribution =
    new KnolderMonthlyContributionImpl(mockReadContribution, mockKnolderScore,mockFetchMonthlyKnolderContribution)

  "KnolderMonthlyContributionImpl" should "return knolder contribution of each knolder along with their knolder id" in {
    val scorePerKnolder = List(KnolderEachContrbutionScore(1,"Mukesh Gupta",10,40,15,15,30,100,100,50,"SEPTEMBER",2020))
    val contributionCount = List(GetContributionCount(1, "Mukesh Gupta", 2, 2, 1, 1, 1, 1,1,1))
    val month = IndianTime.currentTime.getMonth.toString
    val year = IndianTime.currentTime.getYear
    when(mockReadContribution.fetchKnoldersWithMonthlyContributions(month,year))
      .thenReturn(contributionCount)

    when(mockKnolderScore.calculateEachContributionScore(month,year,contributionCount))
      .thenReturn(scorePerKnolder)


    when(mockFetchMonthlyKnolderContribution.fetchKnolderIdIfKnolderMonthlyContributionExist(any,any,any))
      .thenReturn(Option(1))

    val contributionOfKnolders = List(KnolderIdWithKnolderContributionScore(Some(1), KnolderEachContrbutionScore(1,"Mukesh Gupta",10,40,15,15,30,100,100,50,"SEPTEMBER",2020)))

    assert(knolderMonthlyContribution.getKnolderMonthlyContribution(month,year) == contributionOfKnolders)
  }
}
