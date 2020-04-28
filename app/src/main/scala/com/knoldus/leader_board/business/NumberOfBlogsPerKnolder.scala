package com.knoldus.leader_board.business

import com.knoldus.leader_board.BlogCount

trait NumberOfBlogsPerKnolder {
  def getNumberOfBlogsPerKnolder: List[BlogCount]

  def manageAllTimeData(numberOfBlogsOfKnolders: List[BlogCount]): List[Int]
}
