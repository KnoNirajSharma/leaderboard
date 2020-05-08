package com.knoldus.leader_board.business

import com.knoldus.leader_board.infrastructure.ReadAllTimeReputation
import com.knoldus.leader_board.{KnolderReputation, Reputation}
import com.typesafe.scalalogging._

class ReputationPerKnolderImpl(overallReputation: OverallReputation, readAllTimeReputation: ReadAllTimeReputation)
  extends ReputationPerKnolder with LazyLogging {

  /**
   * Gets knolder id of knolders from all time reputation table.
   *
   * @return List of reputation of knolders along with their knolder id fetched from all time reputation table.
   */
  override def getKnolderReputation: List[KnolderReputation] = {
    val reputationOfKnolders: List[Reputation] = overallReputation.calculateReputation
    logger.info("Fetching knolder id of knolders from all time reputation table.")
    reputationOfKnolders.map { reputationOfKnolder =>
      KnolderReputation(readAllTimeReputation.fetchKnolderIdFromAllTimeReputation(reputationOfKnolder.knolderId),
        reputationOfKnolder)
    }
  }
}
