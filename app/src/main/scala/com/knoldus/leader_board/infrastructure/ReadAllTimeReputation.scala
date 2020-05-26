package com.knoldus.leader_board.infrastructure

trait ReadAllTimeReputation {
  def fetchKnolderIdFromAllTimeReputation(knolderId: Int): Option[Int]
}
