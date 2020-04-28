package com.knoldus.leader_board.infrastructure

import com.knoldus.leader_board._

trait FetchData {
  def fetchKnolderIdFromAllTime(authorId: Int): Option[Int]

  def fetchAllTimeData: List[GetAllTime]

  def fetchKnolders: List[Knolder]

  def fetchKnoldersWithBlogs: List[KnolderBlog]
}
