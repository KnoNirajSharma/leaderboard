package com.knoldus.leader_board.infrastructure

import com.knoldus.leader_board.KnolderBlog

trait ReadBlog {
  def fetchKnoldersWithBlogs: List[KnolderBlog]
}
