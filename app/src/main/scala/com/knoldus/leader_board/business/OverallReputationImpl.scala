package com.knoldus.leader_board.business

import com.knoldus.leader_board.infrastructure.ReadAllTime
import com.knoldus.leader_board.{GetAllTime, GetScore, Reputation}
import com.typesafe.config.Config
import com.typesafe.scalalogging._

class OverallReputationImpl(readAllTime: ReadAllTime, config: Config) extends OverallReputation with LazyLogging {

  /**
   * Calculates reputation of each Knolder by using list of all time data of Knolders.
   *
   * @return List of reputation of each Knolder.
   */
  override def calculateReputation: List[Reputation] = {
    val allTimeData: List[GetAllTime] = readAllTime.fetchAllTimeData
    logger.info("Fetched all time details of each knolder. ")

    logger.info("Calculating score of each knolder.")
    val scorePerKnolder: List[GetScore] = allTimeData.map { allTimeDataPerKnolder =>
      allTimeDataPerKnolder.numberOfBlogs match {
        case Some(blogCount) => GetScore(allTimeDataPerKnolder.knolderName,
          blogCount * config.getInt("scorePerBlog"))
        case None => GetScore(allTimeDataPerKnolder.knolderName, 0)
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
            reputationPerKnolder :+ Reputation(second.knolderName, second.score, reputationPerKnolder(index).rank + 1)
          }
          else {
            reputationPerKnolder :+ Reputation(second.knolderName, second.score, reputationPerKnolder(index).rank)
          }
        case first :: second :: rest =>
          if (second.score != first.score) {
            getReputation(second :: rest, reputationPerKnolder :+ Reputation(second.knolderName, second.score,
              reputationPerKnolder(index).rank + 1), index + 1)
          }
          else {
            getReputation(second :: rest, reputationPerKnolder :+ Reputation(second.knolderName, second.score,
              reputationPerKnolder(index).rank), index + 1)
          }
      }
    }

    val firstKnolder = scorePerKnolder match {
      case Nil => GetScore("", 0)
      case knolder :: Nil => knolder
      case knolder :: _ => knolder
    }
    logger.info("Calculating reputation of each knolder.")
    getReputation(scorePerKnolder, reputationPerKnolder :+ Reputation(firstKnolder.knolderName, firstKnolder.score, 1),
      0).toList
  }
}
