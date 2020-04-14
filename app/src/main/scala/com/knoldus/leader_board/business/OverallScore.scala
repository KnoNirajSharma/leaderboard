package com.knoldus.leader_board.business

import com.knoldus.leader_board.infrastructure.{FetchData, StoreData, UpdateData}
import com.knoldus.leader_board.{AuthorScore, BlogAuthor, BlogCount}

class OverallScore(fetchData: FetchData, storeData: StoreData, updateData: UpdateData) {

  /**
   * Calculates overall rank of each knolder based on their overall score.
   *
   * @param listOfBlogsAndAuthors BlogAuthor case class object which contains list of all blogs and list of
   *                              all knolders.
   * @return List of AuthorScore case class objects specifying wordpress id, name and overall score of each knolder.
   */
  def calculateScore(listOfBlogsAndAuthors: BlogAuthor): List[AuthorScore] = {
    val numberOfBlogsPerKnolder: List[BlogCount] = listOfBlogsAndAuthors.authors.map { author =>
      BlogCount(author.authorLogin, author.authorName, listOfBlogsAndAuthors.blogs
        .count(blog => blog.authorLogin == author.authorLogin))
    }
    numberOfBlogsPerKnolder.map {
      blogs => AuthorScore(blogs.authorLogin, blogs.authorName, blogs.numberOfBlogs * 5, 0)
    }
  }

  /**
   * Verifies whether knolder already exist in all_time table or not, if not then it inserts it into the table with its
   * overall score, if it does then it updates its overall score.
   *
   * @param listOfScores List of AuthorScore case class objects which contains score of each knolder.
   * @return List of Integers which display the status of query execution.
   */
  def manageScores(listOfScores: List[AuthorScore]): List[Int] = {
    listOfScores.map { scores =>
      val authorId = fetchData.fetchKnolderIdFromKnolder(scores)
      val knolderId = fetchData.fetchKnolderIdFromAllTime(scores, authorId)
      knolderId match {
        case Some(_) => updateData.updateAllTimeData(scores, authorId)
        case None => storeData.insertAllTimeData(scores, authorId)
      }
    }
  }
}
