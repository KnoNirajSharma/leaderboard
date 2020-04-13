package com.knoldus.leader_board.store_rank

import java.sql.SQLException

import com.knoldus.leader_board.{GetRank, GetScore}

class OverallRank {
  /**
   * Calculates overall rank of each knolder based on their overall score.
   *
   * @param listOfScores List of GetScore case class objects, which specifies overall score for each knolder.
   * @return List of GetRank case class objects, which specifies overall rank for each knolder.
   */
  def calculateRank(listOfScores: List[GetScore]): List[GetRank] = {
    try {
      val rankList = List.empty[GetRank]

      @scala.annotation.tailrec
      def getRank(listOfScores: List[GetScore], rankList: List[GetRank], rankListIndex: Int): List[GetRank] = {
        listOfScores match {
          case Nil => rankList
          case _ :: Nil => rankList
          case first :: second :: Nil =>
            if (second.score != first.score) {
              rankList :+ GetRank(second.knolderId, rankList(rankListIndex).rank + 1)
            }
            else {
              rankList :+ GetRank(second.knolderId, rankList(rankListIndex).rank)
            }
          case first :: second :: rest =>
            if (second.score != first.score) {
              getRank(second :: rest, rankList :+ GetRank(second.knolderId, rankList(rankListIndex).rank + 1),
                rankListIndex + 1)
            }
            else {
              getRank(second :: rest, rankList :+ GetRank(second.knolderId, rankList(rankListIndex).rank),
                rankListIndex + 1)
            }
        }
      }

      val firstKnolderId = listOfScores match {
        case Nil => 0
        case knolder :: Nil => knolder.knolderId
        case knolder :: _ => knolder.knolderId
      }
      getRank(listOfScores, rankList :+ GetRank(firstKnolderId, 1), 0)
    }
    catch {
      case ex: SQLException => throw new SQLException(ex)
      case ex: Exception => throw new Exception(ex)
    }
  }
}
