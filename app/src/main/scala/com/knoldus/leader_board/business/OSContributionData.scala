package com.knoldus.leader_board.business

import com.knoldus.leader_board.OSContributionTemplate

trait OSContributionData {

  def getOSContributionData: List[OSContributionTemplate]
}
