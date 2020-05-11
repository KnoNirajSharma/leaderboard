package com.knoldus.leader_board.business

import com.knoldus.leader_board.{BlogCount, KnolderBlogCount}

trait NumberOfBlogsPerKnolder {
  def getNumberOfBlogsPerKnolder: List[BlogCount]

  def getKnolderBlogCount(numberOfBlogsOfKnolders: List[BlogCount]): List[KnolderBlogCount]

  def insertBlogCount(numberOfBlogsOfKnolders: List[KnolderBlogCount]): List[Int]

  def updateBlogCount(numberOfBlogsOfKnolders: List[KnolderBlogCount]): List[Int]
}
