package com.knoldus.leader_board.infrastructure

import com.knoldus.leader_board.Reputation

trait FetchReputation {
  def fetchReputation: List[Reputation]
}
