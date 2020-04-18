package com.knoldus.leader_board.infrastructure

import java.sql.Timestamp

import com.knoldus.leader_board.{AuthorScore, GetAuthorScore, GetScore}

trait FetchData {
  def fetchMaxBlogPublicationDate: Option[Timestamp]

  def fetchScores: List[GetScore]

  def fetchKnolderIdFromKnolder(scores: AuthorScore): Option[Int]

  def fetchKnolderIdFromAllTime(scores: AuthorScore, authorId: Option[Int]): Option[Int]

  def fetchAllTimeData: List[GetAuthorScore]
}
