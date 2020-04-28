package com.knoldus.leader_board.infrastructure

import com.knoldus.leader_board.BlogCount

trait StoreData {
  def insertAllTimeData(blogCount: BlogCount): Int
}
