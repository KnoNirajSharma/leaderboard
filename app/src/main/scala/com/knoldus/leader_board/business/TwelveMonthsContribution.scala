package com.knoldus.leader_board.business

import com.knoldus.leader_board.TwelveMonthsScore

trait TwelveMonthsContribution {

  def lastTwelveMonthsScore(knolderId: Int,index:Int): Option[List[TwelveMonthsScore]]

}
