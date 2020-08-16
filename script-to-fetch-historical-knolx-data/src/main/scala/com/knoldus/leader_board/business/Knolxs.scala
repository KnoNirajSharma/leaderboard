package com.knoldus.leader_board.business

import com.knoldus.leader_board.Knolx

trait Knolxs {
  def getAllKnolxDetails: List[Knolx]

  def parseAllKnolxJsonData(unparsedKnolx: String): List[Knolx]
}
