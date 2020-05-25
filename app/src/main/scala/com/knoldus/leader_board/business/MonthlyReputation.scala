package com.knoldus.leader_board.business

import com.knoldus.leader_board.KnolderReputation

trait MonthlyReputation {
  def getKnolderMonthlyReputation: List[KnolderReputation]
}
