package com.knoldus.leader_board.business

import com.knoldus.leader_board.Knolx

trait Knolxs {
  def getLatestKnolxFromAPI: List[Knolx]

  def getListOfLatestKnolx(unparsedKnolx: String): List[Knolx]
}
