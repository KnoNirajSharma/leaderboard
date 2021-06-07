package com.knoldus.leader_board.business

import com.knoldus.leader_board.{GetReputation, GetScore}
import com.typesafe.scalalogging._

class KnolderRankImpl extends KnolderRank with LazyLogging {

  /**
   * Calculates rank of each knolder.
   *
   * @return List of reputation of each knolder.
   */
  override def calculateRank(scorePerKnolder: List[GetScore]): List[GetReputation] = {
    val reputationPerKnolder = Vector.empty[GetReputation]

    @scala.annotation.tailrec
    def getReputation(scrPerKnolder: List[GetScore], repPerKnolder: Vector[GetReputation], index: Int):
    Vector[GetReputation] = {
      scrPerKnolder match {
        case Nil => repPerKnolder
        case _ :: Nil => repPerKnolder
        case first :: second :: Nil =>
          if (second.score != first.score) {
            logger.info("List contains only two knolders and scores of first and second knolders are not same.")
            repPerKnolder :+ GetReputation(second.knolderId, second.knolderName, second.score,
              repPerKnolder(index).rank + 1)
          } else {
            logger.info("List contains only two knolders and scores of first and second knolders are same.")
            repPerKnolder :+ GetReputation(second.knolderId, second.knolderName, second.score,
              repPerKnolder(index).rank)
          }
        case first :: second :: rest =>
          if (second.score != first.score) {
            logger.info("List contains more than two knolders and scores of first and second knolders are not same.")
            getReputation(second :: rest, repPerKnolder :+ GetReputation(second.knolderId, second.knolderName,
              second.score, repPerKnolder(index).rank + 1), index + 1)
          } else {
            logger.info("List contains more than two knolders and scores of first and second knolders are same.")
            getReputation(second :: rest, repPerKnolder :+ GetReputation(second.knolderId, second.knolderName,
              second.score, repPerKnolder(index).rank), index + 1)
          }
      }
    }

    val firstKnolder = getFirstKnolder(scorePerKnolder)
    logger.info("Calculating reputation of each knolder.")
    getReputation(scorePerKnolder, reputationPerKnolder :+ GetReputation(firstKnolder.knolderId,
      firstKnolder.knolderName, firstKnolder.score, 1), 0).toList
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