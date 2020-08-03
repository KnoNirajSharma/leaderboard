package com.knoldus.leader_board.infrastructure

import java.sql.Timestamp

trait FetchTechHub {
  def fetchMaxTechHubUploadedDate: Option[Timestamp]
}
