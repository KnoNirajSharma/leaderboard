package com.knoldus.leader_board.business

import com.knoldus.leader_board.infrastructure.ReadAllTime
import com.knoldus.leader_board.{GetAllTimeCount, GetScore}
import com.typesafe.config.Config
import com.typesafe.scalalogging._

class AllTimeScoreImpl(readAllTime: ReadAllTime, config: Config) extends AllTimeScore with LazyLogging {

  /**
   * Calculates all time score of each knolder.
   *
   * @return List of scores of each knolder.
   */
  override def calculateAllTimeScore: List[GetScore] = {
    val allTimeData: List[GetAllTimeCount] = readAllTime.fetchAllTimeData
    logger.info("Calculating score of each knolder.")
    allTimeData.map { allTimeDataPerKnolder =>
      allTimeDataPerKnolder.numberOfBlogs match {
        case Some(blogCount) => logger.info("Alloted score to the knolder who has written any blog.")
          GetScore(allTimeDataPerKnolder.knolderId, allTimeDataPerKnolder.knolderName,
            blogCount * config.getInt("scorePerBlog"))
        case None => logger.info("Alloted score zero to the knolder who has not written any blog.")
          GetScore(allTimeDataPerKnolder.knolderId, allTimeDataPerKnolder.knolderName, 0)
      }
    }.sortBy(knolder => knolder.score).reverse
  }
}
