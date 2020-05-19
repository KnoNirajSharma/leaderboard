package com.knoldus.leader_board.infrastructure

import com.knoldus.leader_board.{BlogCount, GetMonthlyCount}

trait ReadBlog {
  def fetchKnoldersWithBlogs: List[BlogCount]

  def fetchKnoldersWithMonthlyBlogs: List[GetMonthlyCount]
}
