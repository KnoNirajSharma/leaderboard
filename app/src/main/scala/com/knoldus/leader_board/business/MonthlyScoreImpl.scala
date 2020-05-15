package com.knoldus.leader_board.business

import com.knoldus.leader_board.GetScore
import com.knoldus.leader_board.infrastructure.ReadBlog
import com.typesafe.config.Config
import com.typesafe.scalalogging.LazyLogging

class MonthlyScoreImpl(readBlog: ReadBlog, config: Config) extends MonthlyScore with LazyLogging {

  /**
   * Calculates monthly score of each knolder.
   *
   * @return List of scores of knolders.
   */
  override def calculateMonthlyScore: List[GetScore] = {
    logger.info("Fetching number of blogs of each knolder in current month from blog table.")
    val monthlyBlogCountPerKnolder = readBlog.fetchKnoldersWithMonthlyBlogs
    monthlyBlogCountPerKnolder.map(monthlyData => GetScore(monthlyData.knolderId, monthlyData.knolderName,
      monthlyData.numberOfBlogs * config.getInt("scorePerBlog")))
      .sortBy(knolder => knolder.score).reverse
  }
}
