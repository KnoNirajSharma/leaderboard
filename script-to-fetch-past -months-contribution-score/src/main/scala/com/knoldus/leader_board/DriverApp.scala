package com.knoldus.leader_board

import com.knoldus.leader_board.business._
import com.knoldus.leader_board.infrastructure._
import com.typesafe.config.{Config, ConfigFactory}

object DriverApp extends App {

  val config: Config = ConfigFactory.load()
  val knolderScore: KnolderScore = new KnolderScoreImpl(config)
  val fetchAllTimeAndMonthlyReputation: FetchMonthlyContributionCount = new FetchMonthlyContributionCountImpl(config)
  val monthlyLeaders = new KnolderMonthlyContributionImpl(fetchAllTimeAndMonthlyReputation, knolderScore)
  val pastMonthsLeadersObj = new PastMonthsContributionImpl(monthlyLeaders)
  val storeTopFiveLeaders = new WriteMonthlyContributionImpl(config,pastMonthsLeadersObj)
  storeTopFiveLeaders.insertKnolderMonthlyContribution
}
