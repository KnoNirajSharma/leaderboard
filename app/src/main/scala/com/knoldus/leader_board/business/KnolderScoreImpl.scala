package com.knoldus.leader_board.business

import com.knoldus.leader_board.{GetCount, GetScore}
import com.typesafe.config.Config
import com.typesafe.scalalogging.LazyLogging

class KnolderScoreImpl(config: Config) extends KnolderScore with LazyLogging {
  /**
   * Calculates score of each knolder.
   *
   * @return List of scores of knolders.
   */
  override def calculateScore(counts: List[GetCount]): List[GetScore] = {
    logger.info("Calculating score of each knolder.")
    counts.map(count => GetScore(count.knolderId, count.knolderName,
      count.numberOfBlogs * config.getInt("scorePerBlog") +
        (count.numberOfKnolx * config.getInt("scorePerKnolx") ) + (count.numberOfWebinar * config.getInt("scorePerWebinar") )))
      .sortBy(knolder => knolder.score).reverse
  }
}
