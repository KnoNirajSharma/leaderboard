package com.knoldus.leader_board.business

import com.knoldus.leader_board.{IndianTime, MonthlyAllTimeReputation}
import com.typesafe.scalalogging.LazyLogging

import scala.annotation.tailrec

class PastMonthsLeadersImpl(getMonthlyLeaders: MonthlyLeaders) extends PastMonthsLeaders with LazyLogging {
  /**
   * calculating past months leaders from january.
   *
   * @return leaders of every months from january.
   */

  override def getPastMonthsLeaders(index: Int): List[MonthlyAllTimeReputation] = {
    logger.info("getting past months monthly leaders")

    @tailrec
    def getListOfPastMonthsLeaders(monthlyAndAlltimeReputationList: List[MonthlyAllTimeReputation], monthsIndex: Int): List[MonthlyAllTimeReputation] = {
      if (monthsIndex < 1) {
        monthlyAndAlltimeReputationList
      } else {
        val monthValue = IndianTime.currentTime.minusMonths(monthsIndex).getMonthValue
        val year = IndianTime.currentTime.minusMonths(monthsIndex).getYear
        getListOfPastMonthsLeaders(monthlyAndAlltimeReputationList ::: getMonthlyLeaders.getMonthlyAndAllTimeReputation(monthValue, year),
          monthsIndex - 1)
      }
    }

    getListOfPastMonthsLeaders(List.empty, index)
  }
}
