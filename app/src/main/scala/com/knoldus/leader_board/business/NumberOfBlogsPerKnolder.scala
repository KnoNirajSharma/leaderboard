package com.knoldus.leader_board.business

import com.knoldus.leader_board.BlogCount

trait NumberOfBlogsPerKnolder {
  def getNumberOfBlogsPerKnolder: List[BlogCount]

  def insertBlogCount(numberOfBlogsOfKnolders: List[BlogCount]): List[Int]

  def updateBlogCount(numberOfBlogsOfKnolders: List[BlogCount]): List[Int]
}
