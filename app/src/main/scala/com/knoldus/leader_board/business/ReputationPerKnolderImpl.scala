package com.knoldus.leader_board.business

import com.knoldus.leader_board.infrastructure.ReadAllTimeReputation
import com.knoldus.leader_board.{KnolderReputation, Reputation}
import com.typesafe.scalalogging._

class ReputationPerKnolderImpl(knolderRank: KnolderRank, allTimeScore: AllTimeScore,
                               readAllTimeReputation: ReadAllTimeReputation)
  extends ReputationPerKnolder with LazyLogging {

  /**
   * Gets knolder id of knolders along with reputation from all time reputation table.
   *
   * @return List of reputation of knolders along with their knolder id.
   */
  override def getKnolderReputation: List[KnolderReputation] = {
    val scorePerKnolder = allTimeScore.calculateAllTimeScore
    val reputationOfKnolders: List[Reputation] = knolderRank.calculateRank(scorePerKnolder)
    logger.info("Fetching knolder id of knolders from all time reputation table.")
    reputationOfKnolders.map { reputationOfKnolder =>
      KnolderReputation(readAllTimeReputation.fetchKnolderIdFromAllTimeReputation(reputationOfKnolder.knolderId),
        reputationOfKnolder)
    }
  }
}
