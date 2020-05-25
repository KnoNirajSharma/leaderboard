package com.knoldus.leader_board.business

import com.knoldus.leader_board.infrastructure.{ReadAllTimeReputation, ReadBlog}
import com.knoldus.leader_board.{KnolderReputation, Reputation}
import com.typesafe.scalalogging._

class AllTimeReputationImpl(readBlog: ReadBlog, knolderRank: KnolderRank, knolderScore: KnolderScore,
                            readAllTimeReputation: ReadAllTimeReputation) extends AllTimeReputation with LazyLogging {

  /**
   * Gets knolder id of knolders along with reputation from all time reputation table.
   *
   * @return List of reputation of knolders along with their knolder id.
   */
  override def getKnolderReputation: List[KnolderReputation] = {
    val blogCounts = readBlog.fetchKnoldersWithBlogs
    val scorePerKnolder = knolderScore.calculateScore(blogCounts)
    val reputationOfKnolders: List[Reputation] = knolderRank.calculateRank(scorePerKnolder)
    logger.info("Fetching knolder id of knolders from all time reputation table.")
    reputationOfKnolders.map { reputationOfKnolder =>
      KnolderReputation(readAllTimeReputation.fetchKnolderIdFromAllTimeReputation(reputationOfKnolder.knolderId),
        reputationOfKnolder)
    }
  }
}
