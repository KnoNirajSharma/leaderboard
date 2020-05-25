package com.knoldus.leader_board.infrastructure

import com.knoldus.leader_board.KnolderStreak

trait WriteQuarterlyReputation {
  def insertQuarterlyReputationData(reputationOfKnolders: List[KnolderStreak]): List[Int]

  def updateQuarterlyReputationData(reputationOfKnolders: List[KnolderStreak]): List[Int]
}
