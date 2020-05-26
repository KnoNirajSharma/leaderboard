package com.knoldus.leader_board.business

import com.knoldus.leader_board.{GetScore, Reputation}
import com.typesafe.scalalogging._

class KnolderRankImpl extends KnolderRank with LazyLogging {

  /**
   * Calculates rank of each knolder.
   *
   * @return List of reputation of each knolder.
   */
  override def calculateRank(scorePerKnolder: List[GetScore]): List[Reputation] = {
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
