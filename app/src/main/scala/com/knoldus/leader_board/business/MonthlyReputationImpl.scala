package com.knoldus.leader_board.business

import com.knoldus.leader_board.infrastructure.{ReadContribution, ReadMonthlyReputation}
import com.knoldus.leader_board.{GetReputation, KnolderReputation}
import com.typesafe.scalalogging.LazyLogging

class MonthlyReputationImpl(readContribution: ReadContribution, knolderRank: KnolderRank, knolderScore: KnolderScore,
                            readMonthlyReputation: ReadMonthlyReputation) extends LazyLogging with MonthlyReputation {

  /**
   * Gets knolder id of knolders along with reputation from monthly reputation table.
   *
   * @return List of monthly reputation of knolders along with their knolder id.
   */
  override def getKnolderMonthlyReputation: List[KnolderReputation] = {
    val contributions = readContribution.fetchKnoldersWithMonthlyContributions
    val scorePerKnolder = knolderScore.calculateScore(contributions)
    val reputationOfKnolders: List[GetReputation] = knolderRank.calculateRank(scorePerKnolder)
    logger.info("Fetching knolder id of knolders from monthly reputation table.")
    reputationOfKnolders.map { reputationOfKnolder =>
      KnolderReputation(readMonthlyReputation.fetchKnolderIdFromMonthlyReputation(reputationOfKnolder.knolderId),
        reputationOfKnolder)
    }
  }
}
