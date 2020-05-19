package com.knoldus.leader_board.infrastructure

import com.knoldus.leader_board.GetReputation

trait ReadMonthlyReputation {
  def fetchMonthlyReputationData: List[GetReputation]

  def fetchKnolderIdFromMonthlyReputation(knolderId: Int): Option[Int]
}
