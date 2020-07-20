package com.knoldus.leader_board.infrastructure

import com.knoldus.leader_board.Knolx

trait StoreKnolx {
  def insertKnolx(listOfKnolx: List[Knolx]): List[Int]
}
