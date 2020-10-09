package com.knoldus.leader_board.business

import com.knoldus.leader_board.IndianTime
import com.knoldus.leader_board.infrastructure.{FetchMonthlyKnolderContribution, ReadContribution}
import com.typesafe.scalalogging.LazyLogging

final case class KnolderIdWithKnolderContributionScore(knolderId: Option[Int], knolderContributionScore: KnolderEachContrbutionScore)

class KnolderMonthlyContributionImpl(readContribution: ReadContribution, knolderScore: KnolderScore,
                                     fetchKnolderId: FetchMonthlyKnolderContribution) extends KnolderMonthlyContribution with LazyLogging {

  /**
   * Gets knolder id of knolders along with contribution from knolder monthly contribution table.
   *
   * @return List of each contribution scores of knolders along with their knolder id.
   */

  def getKnolderMonthlyContribution: List[KnolderIdWithKnolderContributionScore] = {
    logger.info("getting contribution scores of each knolder along with their knolder id")

    val month = IndianTime.currentTime.getMonth.toString
    val year = IndianTime.currentTime.getYear
    val contributions = readContribution.fetchKnoldersWithMonthlyContributions

    val knolderEachContributionScore = knolderScore.calculateEachContributionScore(month, year, contributions)
    knolderEachContributionScore.map(knolderContributionScore => KnolderIdWithKnolderContributionScore
    (fetchKnolderId.fetchKnolderIdIfKnolderMonthlyContributionExist(knolderContributionScore.knolderId, knolderContributionScore.month
      , knolderContributionScore.year), knolderContributionScore))
  }
}
