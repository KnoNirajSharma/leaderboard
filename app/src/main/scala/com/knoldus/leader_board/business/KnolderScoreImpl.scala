package com.knoldus.leader_board.business

import com.knoldus.leader_board.infrastructure.{ContributionScoreMultiplierAndSpikeMonth, KnolderContributionScore}
import com.knoldus.leader_board.{GetContributionCount, GetScore}
import com.typesafe.config.Config
import com.typesafe.scalalogging.LazyLogging

final case class KnolderEachContrbutionScore(knolderId: Int, knolderName: String, blogScore: Int, knolxScore: Int, webinarScore: Int, techHubScore: Int,
                                             osContributionScore: Int, conferenceScore: Int, bookScore: Int, researchPaperScore: Int,
                                             MeetupScore:Int,  month: String, year: Int)

class KnolderScoreImpl(fetchSpikeMonth: ContributionScoreMultiplierAndSpikeMonth, config: Config) extends KnolderScore with LazyLogging {
  val scorePerBlog: Int = config.getInt("scorePerBlog")
  val scorePerWebinar: Int = config.getInt("scorePerWebinar")
  val scorePerKnolx: Int = config.getInt("scorePerKnolx")
  val scorePerTechHub: Int = config.getInt("scorePerTechHub")
  val scorePerOsContribution: Int = config.getInt("scorePerOsContribution")
  val scorePerConference: Int = config.getInt("scorePerConference")
  val scorePerBook: Int = config.getInt("scorePerBook")
  val scorePerResearchPaper: Int = config.getInt("scorePerResearchPaper")
  val scorePerMeetup: Int = config.getInt("scorePerMeetup")

  /**
   * Calculates overall score of each knolder and if score is not available
   * then put the default score as 0.
   * @return List of scores of knolders.
   */
  override def calculateScore(score: List[KnolderContributionScore]): List[GetScore] = {
    logger.info("calculating overall contribution score ")


    score.map(contributionScore => GetScore(contributionScore.knolderId, contributionScore.knolderName,
      contributionScore.blogScore.getOrElse(0) + contributionScore.knolxScore.getOrElse(0) + contributionScore.webinarScore.getOrElse(0)
        + contributionScore.techHubScore.getOrElse(0) + contributionScore.osContributionScore.getOrElse(0) + contributionScore.conferenceScore.getOrElse(0)
        + contributionScore.bookScore.getOrElse(0) + contributionScore.researchPaperScore.getOrElse(0) + contributionScore.MeetupScore.getOrElse(0)))
      .sortBy(knolder => knolder.score).reverse
  }

  /**
   * Calculates each contribution score of each knolder.
   *
   * @return List of each contribution scores of knolders.
   */

  def calculateEachContributionScore(month: String, year: Int, contributionCount: List[GetContributionCount]): List[KnolderEachContrbutionScore] = {


    fetchSpikeMonth.fetchContributionScoreMultiplierAndSpikeMonthImpl(month, year) match {
      case Some(value) => logger.info("calculating each contribution score if the month is spike")
        contributionCount.map(count => KnolderEachContrbutionScore(count.knolderId, count.knolderName,
          value.blogScoreMultiplier * count.numberOfBlogs * scorePerBlog, value.knolxScoreMultiplier *
            count.numberOfKnolx * scorePerKnolx, value.webinarScoreMultiplier * count.numberOfWebinar * scorePerWebinar,
          value.techHubScoreMultiplier * count.numberOfTechHub * scorePerTechHub, value.osContributionScoreMultiplier *
            count.numberOfOSContribution * scorePerOsContribution, value.conferenceScoreMultiplier * count.numberOfConferences * scorePerConference
          , value.bookScoreMultiplier * count.numberOfBooks * scorePerBook,
          value.researchPaperScoreMultiplier * count.numberOfResearchPapers * scorePerResearchPaper,
          value.MeetupScoreMultiplier * count.numberOfMeetup * scorePerMeetup, value.month, value.year))


      case None => logger.info("calculating each contribution score if the month is not spike")
        contributionCount.map(count => KnolderEachContrbutionScore(count.knolderId, count.knolderName,
          count.numberOfBlogs * scorePerBlog,
          count.numberOfKnolx * scorePerKnolx, count.numberOfWebinar * scorePerWebinar, count.numberOfTechHub * scorePerTechHub
          , count.numberOfOSContribution * scorePerOsContribution, count.numberOfConferences * scorePerConference
          , count.numberOfBooks * scorePerBook, count.numberOfResearchPapers * scorePerResearchPaper,count.numberOfMeetup * scorePerMeetup, month, year))

    }
  }
}
