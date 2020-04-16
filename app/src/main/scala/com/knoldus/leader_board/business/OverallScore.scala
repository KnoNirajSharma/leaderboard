package com.knoldus.leader_board.business

import com.knoldus.leader_board.{AuthorScore, BlogAuthor}

trait OverallScore {
  def calculateScore(listOfBlogsAndAuthors: BlogAuthor): List[AuthorScore]

  def manageScores(listOfScores: List[AuthorScore]): List[Int]
}
