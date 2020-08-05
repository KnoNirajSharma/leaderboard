package com.knoldus.leader_board.business

import com.knoldus.leader_board.TechHubTemplate

trait TechHubData {
  def getLatestTechHubTemplatesFromAPI: List[TechHubTemplate]

  def getListOfLatestTechHubTemplates(unparsedTechHub: String): List[TechHubTemplate]
}
