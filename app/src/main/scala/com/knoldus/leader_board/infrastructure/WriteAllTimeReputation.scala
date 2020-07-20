package com.knoldus.leader_board.infrastructure

import com.knoldus.leader_board.KnolderReputation

trait WriteAllTimeReputation {
  def insertAllTimeReputationData(reputationOfKnolders: List[KnolderReputation]): List[Int]

  def updateAllTimeReputationData(reputationOfKnolders: List[KnolderReputation]): List[Int]
}
