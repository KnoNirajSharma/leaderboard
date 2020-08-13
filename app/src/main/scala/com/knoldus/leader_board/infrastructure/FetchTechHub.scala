package com.knoldus.leader_board.infrastructure

import java.sql.Timestamp

trait FetchTechHub {
  def getLastUpdatedDateForTechHub: Option[Timestamp]
}
