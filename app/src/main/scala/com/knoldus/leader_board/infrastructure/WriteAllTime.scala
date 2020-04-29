package com.knoldus.leader_board.infrastructure

import com.knoldus.leader_board.BlogCount

trait WriteAllTime {
  def insertAllTimeData(blogCount: BlogCount): Int

  def updateAllTimeData(blogCount: BlogCount): Int
}
