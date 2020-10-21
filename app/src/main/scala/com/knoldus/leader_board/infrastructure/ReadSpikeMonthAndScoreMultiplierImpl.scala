package com.knoldus.leader_board.infrastructure

import com.knoldus.leader_board.IndianTime
import com.typesafe.config.Config
import com.typesafe.scalalogging.LazyLogging

final case class PointsAndMultiplier(points: Int, pointsMultiplier: Int)

final case class ContributionPointsWithMultiplier(blog: PointsAndMultiplier, knolx: PointsAndMultiplier, techHubTemplate: PointsAndMultiplier,
                                                  webinar: PointsAndMultiplier, osContribution: PointsAndMultiplier, conference: PointsAndMultiplier,
                                                  researchPaper: PointsAndMultiplier, book: PointsAndMultiplier)

class ReadSpikeMonthAndScoreMultiplierImpl(config:Config,contributionScoreMultiplierAndSpikeMonth: ContributionScoreMultiplierAndSpikeMonth)
  extends ReadSpikeMonthAndScoreMultiplier with LazyLogging {

  /**
   * getting default points of contribution and their multipliers
   * @return Contribution Points with the points multiplier of the current month
   */

  def readContributionScoreMultiplierAndSpikeMonth: ContributionPointsWithMultiplier = {

    val scorePerBlog: Int = config.getInt("scorePerBlog")
    val scorePerWebinar: Int = config.getInt("scorePerWebinar")
    val scorePerKnolx: Int = config.getInt("scorePerKnolx")
    val scorePerTechHub: Int = config.getInt("scorePerTechHub")
    val scorePerOsContribution: Int = config.getInt("scorePerOsContribution")
    val scorePerConference: Int = config.getInt("scorePerConference")
    val scorePerBook: Int = config.getInt("scorePerBook")
    val scorePerResearchPaper: Int = config.getInt("scorePerResearchPaper")
    val month = IndianTime.currentTime.getMonth.toString
    val year = IndianTime.currentTime.getYear
    val score = contributionScoreMultiplierAndSpikeMonth.fetchContributionScoreMultiplierAndSpikeMonthImpl(month, year)
    score match {
      case Some(value) => logger.info("getting default points of contribution and the contribution multiplier")
        ContributionPointsWithMultiplier(PointsAndMultiplier(scorePerBlog,value.blogScoreMultiplier),
      PointsAndMultiplier(scorePerKnolx,value.knolxScoreMultiplier),PointsAndMultiplier(scorePerTechHub,value.techHubScoreMultiplier),
      PointsAndMultiplier(scorePerWebinar,value.webinarScoreMultiplier),PointsAndMultiplier(scorePerOsContribution,value.osContributionScoreMultiplier),
      PointsAndMultiplier(scorePerConference,value.conferenceScoreMultiplier),PointsAndMultiplier(scorePerResearchPaper,value.researchPaperScoreMultiplier),
      PointsAndMultiplier(scorePerBook,value.bookScoreMultiplier))

      case None =>  logger.info("getting default points of contribution with default contribution multiplier")
        ContributionPointsWithMultiplier(PointsAndMultiplier(scorePerBlog,1),
        PointsAndMultiplier(scorePerKnolx,1),PointsAndMultiplier(scorePerTechHub,1),
        PointsAndMultiplier(scorePerWebinar,1),PointsAndMultiplier(scorePerOsContribution,1),
        PointsAndMultiplier(scorePerConference,1),PointsAndMultiplier(scorePerResearchPaper,1),
        PointsAndMultiplier(scorePerBook,1))
    }
  }
}
