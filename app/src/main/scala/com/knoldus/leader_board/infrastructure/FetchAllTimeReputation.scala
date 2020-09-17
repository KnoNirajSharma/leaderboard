package com.knoldus.leader_board.infrastructure

import com.knoldus.leader_board.GetReputation

trait FetchAllTimeReputation {

  def fetchAllTimeReputation: List[GetReputation]
}
