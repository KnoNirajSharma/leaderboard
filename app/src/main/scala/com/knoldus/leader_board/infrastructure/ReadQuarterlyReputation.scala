package com.knoldus.leader_board.infrastructure

trait ReadQuarterlyReputation {
  def fetchKnolderIdFromQuarterlyReputation(knolderId: Int): Option[Int]
}
