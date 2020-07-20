package com.knoldus.leader_board.infrastructure

import java.sql.Timestamp

trait FetchKnolx {
  def fetchMaxKnolxDeliveredDate: Option[Timestamp]
}
