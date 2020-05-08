package com.knoldus.leader_board.business

import com.knoldus.leader_board.infrastructure.ReadAllTimeReputation
import com.knoldus.leader_board.{KnolderReputation, Reputation}

class ReputationPerKnolderImpl(overallReputation: OverallReputation, readAllTimeReputation: ReadAllTimeReputation)
  extends ReputationPerKnolder {
  override def getKnolderReputation: List[KnolderReputation] = {
    val reputationOfKnolders: List[Reputation] = overallReputation.calculateReputation
    reputationOfKnolders.map { reputationOfKnolder =>
      KnolderReputation(readAllTimeReputation.knolderIdFromAllTimeReputation(reputationOfKnolder.knolderId),
        reputationOfKnolder)
    }
  }
}
