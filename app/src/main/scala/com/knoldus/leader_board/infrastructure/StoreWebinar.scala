package com.knoldus.leader_board.infrastructure

import com.knoldus.leader_board.Webinar

trait StoreWebinar {
  def insertWebinar(listOfWebinar: List[Webinar]): List[Int]
}
