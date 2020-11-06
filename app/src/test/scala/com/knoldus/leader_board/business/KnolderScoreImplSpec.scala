package com.knoldus.leader_board.business

import com.knoldus.leader_board.infrastructure.{ContributionScoreMultiplierAndSpikeMonth, KnolderContributionScore, ScoreMultiplier}
import com.knoldus.leader_board.{GetContributionCount, GetScore, IndianTime}
import com.typesafe.config.ConfigFactory
import org.mockito.MockitoSugar
import org.scalatest.wordspec.AnyWordSpecLike

class KnolderScoreImplSpec extends MockitoSugar with AnyWordSpecLike{
  val fetchSpikeMonths= mock[ContributionScoreMultiplierAndSpikeMonth]
  val knolderScore = new KnolderScoreImpl(fetchSpikeMonths,ConfigFactory.load())

  "calculate score" should {
    "give overall score of each knolder" in {
      val scorePerKnolder = List(GetScore(1, "Mukesh Gupta", 395), GetScore(2, "anjali", 385))
      val score = List(KnolderContributionScore(1, "Mukesh Gupta", Option(15), Option(40), Option(15), Option(15),
        Option(30), Option(100),Option(100),Option(50),Option(30)),
        KnolderContributionScore(2, "anjali", Option(10), Option(20), Option(30), Option(15), Option(30), Option(100),Option(100),Option(50),Option(30)))

      assert(knolderScore.calculateScore(score) == scorePerKnolder)
    }

    "give each contribution score of each knolder when month is spike" in {
      val month = IndianTime.currentTime.getMonth.toString
      val year = IndianTime.currentTime.getYear
      when(fetchSpikeMonths.fetchContributionScoreMultiplierAndSpikeMonthImpl(month,year))
        .thenReturn(Option(ScoreMultiplier(2,2,2,2,2,2,2,2,2,month,year)))
      val scorePerKnolder = List(KnolderEachContrbutionScore(1,"Mukesh Gupta",30,80,30,30,60,200,200,100,60,month,year))
      val counts = List(GetContributionCount(1, "Mukesh Gupta", 3, 2, 1, 1, 1, 1, 1, 1,1))

      assert(knolderScore.calculateEachContributionScore(month,year,counts) == scorePerKnolder)
    }
    "give each contribution score of each knolder when month is not spike" in {
      val month = IndianTime.currentTime.getMonth.toString
      val year = IndianTime.currentTime.getYear
      when(fetchSpikeMonths.fetchContributionScoreMultiplierAndSpikeMonthImpl(month,year))
        .thenReturn(None)
      val scorePerKnolder = List(KnolderEachContrbutionScore(1,"Mukesh Gupta",15,40,15,15,30,100,100,50,30,month,year))
      val counts = List(GetContributionCount(1, "Mukesh Gupta", 3, 2, 1, 1, 1, 1, 1, 1, 1))

      assert(knolderScore.calculateEachContributionScore(month,year,counts) == scorePerKnolder)
    }
  }
}
