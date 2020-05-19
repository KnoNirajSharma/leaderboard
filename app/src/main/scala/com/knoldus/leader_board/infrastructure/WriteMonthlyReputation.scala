package com.knoldus.leader_board.infrastructure

import com.knoldus.leader_board.KnolderReputation

trait WriteMonthlyReputation {
  def insertMonthlyReputationData(reputationOfKnolders: List[KnolderReputation]): List[Int]

  def updateMonthlyReputationData(reputationOfKnolders: List[KnolderReputation]): List[Int]
}
