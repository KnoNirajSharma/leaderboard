package com.knoldus.leader_board.business

import com.knoldus.leader_board.KnolderStreak

trait QuarterlyReputation {
  def getKnolderQuarterlyReputation: List[KnolderStreak]
}
