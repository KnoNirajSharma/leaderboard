package com.knoldus.leader_board.infrastructure

import java.sql.Timestamp

trait FetchBlogs {
  def fetchMaxBlogPublicationDate: Option[Timestamp]
}
