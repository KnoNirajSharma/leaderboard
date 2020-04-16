package com.knoldus.leader_board.infrastructure

import com.knoldus.leader_board.{AuthorScore, BlogAuthor}

trait StoreData {
  def insertKnolder(listOfBlogsAndAuthors: BlogAuthor): List[Int]

  def insertBlog(listOfBlogsAndAuthors: BlogAuthor): List[Int]

  def insertAllTimeData(scores: AuthorScore, authorId: Option[Int]): Int
}