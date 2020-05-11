package com.knoldus.leader_board.infrastructure

import com.knoldus.leader_board.BlogCount

trait ReadBlog {
  def fetchKnoldersWithBlogs: List[BlogCount]
}
