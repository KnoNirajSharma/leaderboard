package com.knoldus.leader_board.infrastructure

import com.knoldus.leader_board.KnolderBlogCount

trait WriteAllTime {
  def insertAllTimeData(numberOfBlogsOfKnolders: List[KnolderBlogCount]): List[Int]

  def updateAllTimeData(numberOfBlogsOfKnolders: List[KnolderBlogCount]): List[Int]
}
