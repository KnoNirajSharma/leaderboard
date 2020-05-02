package com.knoldus.leader_board.infrastructure

import com.knoldus.leader_board.Knolder

trait ReadKnolder {
  def fetchKnolders: List[Knolder]
}
