package com.knoldus.leader_board.infrastructure

import com.knoldus.leader_board.GetAllTime

trait ReadAllTime {
  def fetchAllTimeData: List[GetAllTime]

  def fetchKnolderIdFromAllTime(authorId: Int): Option[Int]
}
