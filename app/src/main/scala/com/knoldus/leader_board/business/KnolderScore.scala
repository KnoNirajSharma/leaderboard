package com.knoldus.leader_board.business

import com.knoldus.leader_board.{GetContributionCount, GetScore}

trait KnolderScore {
  def calculateScore(listOfCount: List[GetContributionCount]): List[GetScore]

  def calculateEachContributionScore(month:String,year:Int,contributionCount: List[GetContributionCount]):List[KnolderEachContrbutionScore]
}
