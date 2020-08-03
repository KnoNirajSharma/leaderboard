package com.knoldus.leader_board.business

import com.knoldus.leader_board.TechHub

trait TechHubData {
  def getLatestTechHubFromAPI: List[TechHub]

  def getListOfLatestTechHub(unparsedTechHub: String): List[TechHub]
}
