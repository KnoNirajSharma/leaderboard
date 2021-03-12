package com.knoldus.leader_board.infrastructure

import com.knoldus.leader_board.IndianTime
import com.typesafe.config.ConfigFactory
import org.mockito.MockitoSugar
import org.scalatest.wordspec.AnyWordSpecLike

class ReadSpikeMonthAndScoreMultiplierImplSpec extends MockitoSugar with AnyWordSpecLike {
  val fetchSpikeMonths = mock[ContributionScoreMultiplierAndSpikeMonth]
  val readSpikeMonthAndScoreMultiplier = new ReadSpikeMonthAndScoreMultiplierImpl(ConfigFactory.load(), fetchSpikeMonths)

  "ReadSpikeMonthAndScoreMultiplierImpl" should {


    "give score points with multiplier when the current month is spike" in {
      val month = IndianTime.currentTime.getMonth.toString
      val year = IndianTime.currentTime.getYear
      when(fetchSpikeMonths.fetchContributionScoreMultiplierAndSpikeMonthImpl(month, year))
        .thenReturn(Option(ScoreMultiplier(2, 2, 2, 2, 2, 2, 2, 2, 2, month, year)))

      val scoreMultiplier = ContributionPointsWithMultiplier(PointsAndMultiplier(5, 2), PointsAndMultiplier(20, 2),
        PointsAndMultiplier(15, 2), PointsAndMultiplier(15, 2), PointsAndMultiplier(30, 2), PointsAndMultiplier(100, 2),
        PointsAndMultiplier(50, 2), PointsAndMultiplier(30, 2),PointsAndMultiplier(100,2))

      assert(readSpikeMonthAndScoreMultiplier.readContributionScoreMultiplierAndSpikeMonth == scoreMultiplier)
    }
    "give score points with multiplier when the current month is not spike" in {
      val month = IndianTime.currentTime.getMonth.toString
      val year = IndianTime.currentTime.getYear
      when(fetchSpikeMonths.fetchContributionScoreMultiplierAndSpikeMonthImpl(month, year))
        .thenReturn(None)
      val scoreMultiplier = ContributionPointsWithMultiplier(PointsAndMultiplier(5, 1), PointsAndMultiplier(20, 1),
        PointsAndMultiplier(15, 1), PointsAndMultiplier(15, 1), PointsAndMultiplier(30, 1), PointsAndMultiplier(100, 1),
        PointsAndMultiplier(50, 1), PointsAndMultiplier(30, 1),PointsAndMultiplier(100,1))

      assert(readSpikeMonthAndScoreMultiplier.readContributionScoreMultiplierAndSpikeMonth == scoreMultiplier)
    }
  }
}
