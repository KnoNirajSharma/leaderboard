package com.knoldus.leader_board.business

import com.knoldus.leader_board.infrastructure.ReadAllTime
import com.knoldus.leader_board.{GetAllTime, GetScore, Reputation}
import com.typesafe.config.Config
import com.typesafe.scalalogging._

class OverallReputationImpl(readAllTime: ReadAllTime, config: Config) extends OverallReputation with LazyLogging {
  /**
   * Calculates reputation of each knolder by using list of all time data of knolders.
   *
   * @return List of reputation of each knolder.
   */
  override def calculateReputation: List[Reputation] = {
    val allTimeData: List[GetAllTime] = readAllTime.fetchAllTimeData
    logger.info("Calculating score of each knolder.")
    val scorePerKnolder: List[GetScore] = allTimeData.map { allTimeDataPerKnolder =>
      allTimeDataPerKnolder.numberOfBlogs match {
        case Some(blogCount) => logger.info("Alloted score to the knolder who has written any blog.")
          GetScore(allTimeDataPerKnolder.knolderId, allTimeDataPerKnolder.knolderName,
            blogCount * config.getInt("scorePerBlog"))
        case None => logger.info("Alloted score zero to the knolder who has not written any blog.")
          GetScore(allTimeDataPerKnolder.knolderId, allTimeDataPerKnolder.knolderName, 0)
      }
    }.sortBy(knolder => knolder.score).reverse
    val reputationPerKnolder = Vector.empty[Reputation]

    @scala.annotation.tailrec
    def getReputation(scorePerKnolder: List[GetScore], reputationPerKnolder: Vector[Reputation], index: Int):
    Vector[Reputation] = {
      scorePerKnolder match {
        case Nil => reputationPerKnolder
        case _ :: Nil => reputationPerKnolder
        case first :: second :: Nil =>
          if (second.score != first.score) {
            logger.info("List contains only two knolders and scores of first and second knolders are not same.")
            reputationPerKnolder :+ Reputation(second.knolderId, second.knolderName, second.score,
              reputationPerKnolder(index).rank + 1)
          } else {
            logger.info("List contains only two knolders and scores of first and second knolders are same.")
            reputationPerKnolder :+ Reputation(second.knolderId, second.knolderName, second.score,
              reputationPerKnolder(index).rank)
          }
        case first :: second :: rest =>
          if (second.score != first.score) {
            logger.info("List contains more than two knolders and scores of first and second knolders are not same.")
            getReputation(second :: rest, reputationPerKnolder :+ Reputation(second.knolderId, second.knolderName,
              second.score, reputationPerKnolder(index).rank + 1), index + 1)
          } else {
            logger.info("List contains more than two knolders and scores of first and second knolders are same.")
            getReputation(second :: rest, reputationPerKnolder :+ Reputation(second.knolderId, second.knolderName,
              second.score, reputationPerKnolder(index).rank), index + 1)
          }
      }
    }

    val firstKnolder = getFirstKnolder(scorePerKnolder)
    logger.info("Calculating reputation of each knolder.")
    getReputation(scorePerKnolder, reputationPerKnolder :+ Reputation(firstKnolder.knolderId, firstKnolder.knolderName,
      firstKnolder.score, 1), 0).toList
  }

  private def getFirstKnolder(scorePerKnolder: List[GetScore]): GetScore = {
    scorePerKnolder match {
      case Nil => logger.info("No knolder is present in the list.")
        GetScore(0, "", 0)
      case knolder :: Nil => logger.info("Only one knolder is present in the list.")
        knolder
      case knolder :: _ => logger.info("More than one knolders are present in the list.")
        knolder
    }
  }
}
