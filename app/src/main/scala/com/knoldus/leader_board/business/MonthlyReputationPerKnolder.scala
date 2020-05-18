package com.knoldus.leader_board.business

import com.knoldus.leader_board.KnolderReputation

trait MonthlyReputationPerKnolder {
  def getKnolderMonthlyReputation: List[KnolderReputation]
}
