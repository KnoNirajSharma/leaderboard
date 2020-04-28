package com.knoldus.leader_board.infrastructure

import com.knoldus.leader_board.BlogCount

trait UpdateData {
  def updateAllTimeData(blogCount: BlogCount): Int
}
