package com.knoldus.leader_board.business

import com.knoldus.leader_board.KnolderReputation

trait AllTimeReputation {
  def getKnolderReputation: List[KnolderReputation]
}
