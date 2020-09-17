package com.knoldus.leader_board.business

import com.knoldus.leader_board.infrastructure.{ReadContribution, ReadQuarterlyReputation}
import com.knoldus.leader_board.{GetStreak, KnolderStreak}
import com.typesafe.scalalogging.LazyLogging

class QuarterlyReputationImpl(readContribution: ReadContribution, knolderScore: KnolderScore,
                              readQuarterlyReputation: ReadQuarterlyReputation)
  extends LazyLogging with QuarterlyReputation {

  /**
   * Gets knolder id of knolders along with reputation from quarterly reputation table.
   *
   * @return List of quarterly reputation of knolders along with their knolder id.
   */
  override def getKnolderQuarterlyReputation: List[KnolderStreak] = {
    val firstMonthContribution = readContribution.fetchKnoldersWithQuarterFirstMonthContributions
    val scoresForFirstMonth = knolderScore.calculateScore(firstMonthContribution).sortBy(knolder => knolder.knolderId)

    val secondMonthContribution = readContribution.fetchKnoldersWithQuarterSecondMonthContributions
    val scoresForSecondMonth = knolderScore.calculateScore(secondMonthContribution).sortBy(knolder => knolder.knolderId)

    val thirdMonthContribution = readContribution.fetchKnoldersWithQuarterThirdMonthContributions
    val scoresForThirdMonth = knolderScore.calculateScore(thirdMonthContribution).sortBy(knolder => knolder.knolderId)

    val reputationOfKnolders = (scoresForThirdMonth, scoresForSecondMonth, scoresForFirstMonth).zipped.toList.map {
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
