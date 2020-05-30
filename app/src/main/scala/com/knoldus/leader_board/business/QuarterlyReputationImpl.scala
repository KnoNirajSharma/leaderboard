package com.knoldus.leader_board.business

import com.knoldus.leader_board.infrastructure.{ReadBlog, ReadQuarterlyReputation}
import com.knoldus.leader_board.{GetStreak, KnolderStreak}
import com.typesafe.scalalogging.LazyLogging

class QuarterlyReputationImpl(readBlog: ReadBlog, knolderScore: KnolderScore,
                              readQuarterlyReputation: ReadQuarterlyReputation)
  extends LazyLogging with QuarterlyReputation {

  /**
   * Gets knolder id of knolders along with reputation from quarterly reputation table.
   *
   * @return List of quarterly reputation of knolders along with their knolder id.
   */
  override def getKnolderQuarterlyReputation: List[KnolderStreak] = {
    val firstMonthBlogCount = readBlog.fetchKnoldersWithQuarterFirstMonthBlogs
    val scoresForFirstMonth = knolderScore.calculateScore(firstMonthBlogCount).sortBy(knolder => knolder.knolderId)

    val secondMonthBlogCount = readBlog.fetchKnoldersWithQuarterSecondMonthBlogs
    val scoresForSecondMonth = knolderScore.calculateScore(secondMonthBlogCount).sortBy(knolder => knolder.knolderId)

    val thirdMonthBlogCount = readBlog.fetchKnoldersWithQuarterThirdMonthBlogs
    val scoresForThirdMonth = knolderScore.calculateScore(thirdMonthBlogCount).sortBy(knolder => knolder.knolderId)

    val reputationOfKnolders = (scoresForFirstMonth, scoresForSecondMonth, scoresForThirdMonth).zipped.toList.map {
      knolder =>
        GetStreak(knolder._1.knolderId,
          knolder._1.knolderName,
          s"${knolder._1.score.toString}-${knolder._2.score.toString}-${knolder._3.score.toString}")
    }

    logger.info("Fetching knolder id of knolders from quarterly reputation table.")
    reputationOfKnolders.map {
      knolder =>
        KnolderStreak(readQuarterlyReputation.fetchKnolderIdFromQuarterlyReputation(knolder.knolderId),
          knolder)
    }
  }
}
