package com.knoldus.leader_board.business

import com.knoldus.leader_board.KnolderBlogCount

trait NumberOfBlogsPerKnolder {
  def getKnolderBlogCount: List[KnolderBlogCount]
}
