package com.knoldus.leader_board.business

import java.time.Month

import com.knoldus.leader_board.{GetContributionCount, GetScore}
import com.typesafe.config.Config
import com.typesafe.scalalogging.LazyLogging

final case class KnolderEachContrbutionScore(knolderId: Int, knolderName: String, blogScore: Int, knolxScore: Int, webinarScore: Int, techHubScore: Int,
                                             osContributionScore: Int, conferenceScore: Int, bookScore: Int, researchPaperScore: Int, month: String, year: Int)

class KnolderScoreImpl(config: Config) extends KnolderScore with LazyLogging {
  /**
   * Calculates score of each knolder.
   *
   * @return List of scores of knolders.
   */
  override def calculateScore(month:Int,year:Int,counts: List[GetContributionCount]): List[KnolderEachContrbutionScore] = {
    logger.info("Calculating score of each knolder.")
    val scorePerBlog = config.getInt("scorePerBlog")
    val scorePerWebinar = config.getInt("scorePerWebinar")
    val scorePerKnolx = config.getInt("scorePerKnolx")
    val scorePerTechHub = config.getInt("scorePerTechHub")
    val scorePerOsContribution = config.getInt("scorePerOsContribution")
    val scorePerConference = config.getInt("scorePerConference")
    val scorePerBook = config.getInt("scorePerBook")
    val scorePerResearchPaper = config.getInt("scorePerResearchPaper")

    counts.map(count => KnolderEachContrbutionScore(count.knolderId, count.knolderName,
      count.numberOfBlogs * scorePerBlog,
      count.numberOfKnolx * scorePerKnolx, count.numberOfWebinar * scorePerWebinar, count.numberOfTechHub * scorePerTechHub
      , count.numberOfOSContribution * scorePerOsContribution, count.numberOfConferences * scorePerConference
      , count.numberOfBooks * scorePerBook, count.numberOfResearchPapers * scorePerResearchPaper, Month.of(month).toString, year))
  }
}
