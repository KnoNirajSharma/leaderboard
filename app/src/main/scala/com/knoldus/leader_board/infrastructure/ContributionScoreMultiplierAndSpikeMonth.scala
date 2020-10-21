package com.knoldus.leader_board.infrastructure

trait ContributionScoreMultiplierAndSpikeMonth {

  def fetchContributionScoreMultiplierAndSpikeMonthImpl(month:String , year:Int): Option[ScoreMultiplier]
}
