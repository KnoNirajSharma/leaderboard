package com.knoldus.leader_board.infrastructure

import java.sql.Timestamp



trait FetchData {
  def fetchMaxBlogPublicationDate: Option[Timestamp]

}
