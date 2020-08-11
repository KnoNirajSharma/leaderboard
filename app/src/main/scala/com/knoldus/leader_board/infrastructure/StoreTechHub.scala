package com.knoldus.leader_board.infrastructure

import com.knoldus.leader_board.TechHubTemplate

trait StoreTechHub {
  def insertTechHub(listOfTechHub: List[TechHubTemplate]): List[Int]
}
