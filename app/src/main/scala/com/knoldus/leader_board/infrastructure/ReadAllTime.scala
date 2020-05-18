package com.knoldus.leader_board.infrastructure

import com.knoldus.leader_board.GetAllTimeCount

trait ReadAllTime {
  def fetchAllTimeData: List[GetAllTimeCount]

  def fetchKnolderIdFromAllTime(authorId: Int): Option[Int]
}
