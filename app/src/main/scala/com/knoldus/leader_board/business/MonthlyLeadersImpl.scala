package com.knoldus.leader_board.business

import com.knoldus.leader_board.infrastructure.{FetchAllTimeReputation, ReadContribution}
import com.knoldus.leader_board.{GetReputation, MonthlyAllTimeReputation}
import com.typesafe.scalalogging.LazyLogging

class MonthlyLeadersImpl(fetchMonthlyReputation: ReadContribution, getAllTimeReputation: FetchAllTimeReputation,
                         knolderScore: KnolderScore, knolderRank: KnolderRank) extends MonthlyLeaders with LazyLogging {

  /**
   * Gets monthly and all time reputation of knolders.
   *
   * @return List of monthly and all time reputation of knolders.
   */
  override def getMonthlyAndAllTimeReputation: List[MonthlyAllTimeReputation] = {
    logger.info("getting monthly and all time reputation of knolders")

    val monthlyCount = fetchMonthlyReputation.fetchKnoldersWithQuarterThirdMonthContributions
    val scorePerKnolder = knolderScore.calculateScore(monthlyCount)
    val monthlyReputationOfKnolders: List[GetReputation] = knolderRank.calculateRank(scorePerKnolder).sortBy(knolderReputation =>
      knolderReputation.knolderId)
    val allTimeReputationOfKnolders = getAllTimeReputation.fetchAllTimeReputation.sortBy(knolderReputation => knolderReputation
      .knolderId)
    (monthlyReputationOfKnolders, allTimeReputationOfKnolders).zipped.toList.map(knoldersReputation => MonthlyAllTimeReputation(
      knoldersReputation._1.knolderId, knoldersReputation._1.knolderName, knoldersReputation._2.score, knoldersReputation._2.rank,
      knoldersReputation._1.score, knoldersReputation._1.rank)).sortBy(reputation =>
      (reputation.monthlyRank, reputation.allTimeScore))(Ordering.Tuple2(Ordering.Int, Ordering.Int.reverse))
  }
}
