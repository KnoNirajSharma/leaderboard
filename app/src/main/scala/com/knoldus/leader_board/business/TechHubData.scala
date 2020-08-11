package com.knoldus.leader_board.business

import com.knoldus.leader_board.TechHubTemplate

trait TechHubData {
  def getLatestTechHubTemplates: List[TechHubTemplate]

  def parseTechHubJson(unparsedTechHub: String): List[TechHubTemplate]
}
