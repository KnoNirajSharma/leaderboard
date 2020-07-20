package com.knoldus.leader_board.business

import com.knoldus.leader_board.infrastructure.{ReadAllTimeReputation, ReadContribution}
import com.knoldus.leader_board.{GetReputation, KnolderReputation}
import com.typesafe.scalalogging._

class AllTimeReputationImpl(readContribution: ReadContribution, knolderRank: KnolderRank, knolderScore: KnolderScore,
                            readAllTimeReputation: ReadAllTimeReputation) extends AllTimeReputation with LazyLogging {

  /**
   * Gets knolder id of knolders along with reputation from all time reputation table.
   *
   * @return List of reputation of knolders along with their knolder id.
   */
  override def getKnolderReputation: List[KnolderReputation] = {
    val contributions = readContribution.fetchKnoldersWithContributions
    val scorePerKnolder = knolderScore.calculateScore(contributions)
    val reputationOfKnolders: List[GetReputation] = knolderRank.calculateRank(scorePerKnolder)
    logger.info("Fetching knolder id of knolders from all time reputation table.")
    reputationOfKnolders.map { reputationOfKnolder =>
      KnolderReputation(readAllTimeReputation.fetchKnolderIdFromAllTimeReputation(reputationOfKnolder.knolderId),
        reputationOfKnolder)
    }
  }
}
