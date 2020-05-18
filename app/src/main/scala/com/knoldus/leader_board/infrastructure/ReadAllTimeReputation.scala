package com.knoldus.leader_board.infrastructure

import com.knoldus.leader_board.GetReputation

trait ReadAllTimeReputation {
  def fetchAllTimeReputationData: List[GetReputation]

  def fetchKnolderIdFromAllTimeReputation(knolderId: Int): Option[Int]
}
