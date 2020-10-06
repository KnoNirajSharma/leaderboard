package com.knoldus.leader_board.business

import com.knoldus.leader_board.{GetContributionCount, GetScore}

trait KnolderScore {
  def calculateScore(month:Int,year:Int,listOfCount: List[GetContributionCount]): List[KnolderEachContrbutionScore]
}
