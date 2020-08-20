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
    val scorePerBlog = config.getInt("scorePerBlog")
    val scorePerWebinar = config.getInt("scorePerWebinar")
    val scorePerKnolx = config.getInt("scorePerKnolx")
    val scorePerTechHub = config.getInt("scorePerTechHub")
    val scorePerOsContribution = config.getInt("scorePerOsContribution")

    counts.map(count => GetScore(count.knolderId, count.knolderName,
      count.numberOfBlogs * scorePerBlog +
        (count.numberOfKnolx * scorePerKnolx) + (count.numberOfWebinar * scorePerWebinar) + (count.numberOfTechHub * scorePerTechHub)
        + (count.numberOfOSContribution * scorePerOsContribution)))
      .sortBy(knolder => knolder.score).reverse
  }
}
