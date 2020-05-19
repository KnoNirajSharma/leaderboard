package com.knoldus.leader_board.business

import com.knoldus.leader_board.infrastructure.ReadMonthlyReputation
import com.knoldus.leader_board.{KnolderReputation, Reputation}
import com.typesafe.scalalogging.LazyLogging

class MonthlyReputationPerKnolderImpl(knolderRank: KnolderRank, readMonthlyReputation: ReadMonthlyReputation,
                                      monthlyScore: MonthlyScore)
  extends LazyLogging with MonthlyReputationPerKnolder {

  /**
   * Gets knolder id of knolders from monthly reputation table.
   *
   * @return List of monthly reputation of knolders along with their knolder id.
   */
  override def getKnolderMonthlyReputation: List[KnolderReputation] = {
    val scorePerKnolder = monthlyScore.calculateMonthlyScore
    val reputationOfKnolders: List[Reputation] = knolderRank.calculateRank(scorePerKnolder)
    logger.info("Fetching knolder id of knolders from monthly reputation table.")
    reputationOfKnolders.map { reputationOfKnolder =>
      KnolderReputation(readMonthlyReputation.fetchKnolderIdFromMonthlyReputation(reputationOfKnolder.knolderId),
        reputationOfKnolder)
    }
  }
}
