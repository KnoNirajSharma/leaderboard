package com.knoldus.leader_board.infrastructure

import com.knoldus.leader_board.TechHub

trait StoreTechHub {
  def insertTechHub(listOfTechHub: List[TechHub]): List[Int]
}
