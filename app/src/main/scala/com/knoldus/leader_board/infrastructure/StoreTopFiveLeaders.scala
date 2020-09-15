package com.knoldus.leader_board.infrastructure

trait StoreTopFiveLeaders {

  def insertTopFiveLeaders: List[Int]
}
