package com.knoldus.leader_board.infrastructure

import com.knoldus.leader_board.ReputationCountAndReputation

trait FetchReputation {
  def fetchReputation: Option[ReputationCountAndReputation]
}
