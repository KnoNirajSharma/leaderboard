package com.knoldus.leader_board.business

import java.time.Month

import com.knoldus.leader_board.{GetContributionCount, GetScore, IndianTime}
import com.typesafe.config.ConfigFactory
import org.mockito.MockitoSugar
import org.scalatest.flatspec.AnyFlatSpec

class KnolderScoreImplSpec extends AnyFlatSpec with MockitoSugar {
  val knolderScore = new KnolderScoreImpl(ConfigFactory.load())

  "calculate score" should "give score of each knolder" in {
    val monthValue = IndianTime.currentTime.getMonthValue
    val year = IndianTime.currentTime.getYear

    val scorePerKnolder = List(KnolderEachContrbutionScore(1, "Mukesh Gupta", 15,40,15,15,30,100,100,50,Month.of(monthValue).toString,year),
      KnolderEachContrbutionScore(2, "Anjali", 5,40,15,15,30,100,100,50,Month.of(monthValue).toString,year))
    val counts = List(GetContributionCount(1, "Mukesh Gupta", 3, 2, 1, 1, 1, 1,1,1), GetContributionCount(2, "Anjali", 1, 2, 1, 1, 1, 1,1,1))

    assert(knolderScore.calculateScore(monthValue,year,counts) == scorePerKnolder)
  }
}
