package com.knoldus.leader_board.business

import com.knoldus.leader_board.{GetCount, GetScore}

trait KnolderScore {
  def calculateScore(listOfCount: List[GetCount]): List[GetScore]
}
