package com.knoldus.leader_board

import com.knoldus.leader_board.business._
import com.knoldus.leader_board.infrastructure._
import com.typesafe.config.{Config, ConfigFactory}

object DriverApp extends App {

  val config: Config = ConfigFactory.load()
  val knolderScore: KnolderScore = new KnolderScoreImpl(config)
  val knolderRank: KnolderRank = new KnolderRankImpl
  val fetchAllTimeAndMonthlyReputation: FetchAllTimeAndMonthlyReputation = new FetchAllTimeAndMonthlyReputationImpl(config)
  val monthlyLeaders = new MonthlyLeadersImpl(fetchAllTimeAndMonthlyReputation, knolderScore, knolderRank)
  val pastMonthsLeadersObj = new PastMonthsLeadersImpl(monthlyLeaders)
  val storeTopFiveLeaders = new StoreTopFiveLeadersImpl(config,pastMonthsLeadersObj)
  storeTopFiveLeaders.insertTopFiveLeaders
}
