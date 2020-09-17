package com.knoldus.leader_board.infrastructure

trait ReadMonthlyReputation {
  def fetchKnolderIdFromMonthlyReputation(knolderId: Int): Option[Int]
}
