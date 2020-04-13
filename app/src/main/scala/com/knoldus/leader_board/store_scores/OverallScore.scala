package com.knoldus.leader_board.store_scores

import java.sql.SQLException

import com.knoldus.leader_board.{AuthorScore, BlogAuthor, BlogCount}

class OverallScore {
  /**
   * Calculates overall rank of each knolder based on their overall score.
   *
   * @param listOfBlogsAndAuthors BlogAuthor case class object which contains list of all blogs and list of
   *                              all knolders.
   * @return List of AuthorScore case class objects specifying wordpress id, name and overall score of each knolder.
   */
  def calculateScore(listOfBlogsAndAuthors: BlogAuthor): List[AuthorScore] = {
    try {
      val numberOfBlogsPerKnolder: List[BlogCount] = listOfBlogsAndAuthors.authors.map { author =>
        BlogCount(author.authorLogin, author.authorName, listOfBlogsAndAuthors.blogs
          .count(blog => blog.authorLogin == author.authorLogin))
      }
      numberOfBlogsPerKnolder.map {
        blogs => AuthorScore(blogs.authorLogin, blogs.authorName, blogs.numberOfBlogs * 5, 0)
      }
    }
    catch {
      case ex: SQLException => throw new SQLException(ex)
      case ex: Exception => throw new Exception(ex)
    }
  }
}
