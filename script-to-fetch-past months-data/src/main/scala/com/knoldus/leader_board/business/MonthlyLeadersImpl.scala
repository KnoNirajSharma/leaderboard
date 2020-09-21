package com.knoldus.leader_board.business

import java.time.Month

import com.knoldus.leader_board.infrastructure.FetchAllTimeAndMonthlyReputation
import com.knoldus.leader_board.{GetReputation, MonthlyAllTimeReputation}
import com.typesafe.scalalogging.LazyLogging

class MonthlyLeadersImpl(getAllTimeAndMonthlyReputation: FetchAllTimeAndMonthlyReputation,
                         knolderScore: KnolderScore, knolderRank: KnolderRank) extends MonthlyLeaders with LazyLogging {

  /**
   * Gets monthly and all time reputation of knolders.
   *
   * @return List of monthly and all time reputation of knolders.
   */
  override def getMonthlyAndAllTimeReputation(month: Int, year: Int): List[MonthlyAllTimeReputation] = {
    logger.info("getting monthly and all time reputation of knolders")
    val topFiveLeaders= 5
    val monthlyCount = getAllTimeAndMonthlyReputation.fetchMonthlyReputation(month, year)
    val scorePerKnolder = knolderScore.calculateScore(monthlyCount)
    val monthlyReputationOfKnolders: List[GetReputation] = knolderRank.calculateRank(scorePerKnolder).sortBy(knolderReputation =>
      knolderReputation.knolderId)
    val allTimeContributionCount = getAllTimeAndMonthlyReputation.fetchAllTimeReputation(month, year)
    val allTimeScorePerKnolder = knolderScore.calculateScore(allTimeContributionCount)
    val allTimeReputationOfKnolder: List[GetReputation] = knolderRank.calculateRank(allTimeScorePerKnolder).sortBy(knolderReputation =>
      knolderReputation.knolderId)
    (monthlyReputationOfKnolders, allTimeReputationOfKnolder).zipped.toList.map(knoldersReputation => MonthlyAllTimeReputation(
      knoldersReputation._1.knolderId, knoldersReputation._1.knolderName, knoldersReputation._2.score, knoldersReputation._2.rank,
      knoldersReputation._1.score, knoldersReputation._1.rank,Month.of(month).toString,year)).sortBy(reputation =>
      (reputation.monthlyRank, reputation.allTimeScore))(Ordering.Tuple2(Ordering.Int, Ordering.Int.reverse)).take(topFiveLeaders)
  }
}
