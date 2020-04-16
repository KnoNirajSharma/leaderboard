package com.knoldus.leader_board.infrastructure

import com.knoldus.leader_board.{AuthorScore, BlogAuthor, DatabaseConnection}
import scalikejdbc.{DB, DBSession, SQL}

class StoreData(databaseConnection: DatabaseConnection) {

  /**
   * Stores list of blogs in blog table.
   *
   * @param listOfBlogsAndAuthors BlogAuthor case class object which contains list of all blogs and list of
   *                              all knolders.
   * @return Message specifying data is stored and database connection is closed.
   */
  def insertKnolder(listOfBlogsAndAuthors: BlogAuthor): List[Int] = {
    implicit val session: DBSession = DB.autoCommitSession()
    try {
      listOfBlogsAndAuthors.authors.map { author =>
        SQL("INSERT INTO knolder(full_name, wordpress_id) SELECT ?, ? " +
          "WHERE NOT EXISTS (SELECT wordpress_id FROM knolder WHERE wordpress_id = ?)")
          .bind(author.authorName, author.authorLogin, author.authorLogin)
          .update().apply()
      }
    }
    finally {
      session.close()
    }
  }

  /**
   * Stores list of knolders in knolder table.
   *
   * @param listOfBlogsAndAuthors BlogAuthor case class object which contains list of all blogs and list of
   *                              all knolders.
   * @return Message specifying data is stored and database connection is closed.
   */
  def insertBlog(listOfBlogsAndAuthors: BlogAuthor): List[Int] = {
    implicit val session: DBSession = DB.autoCommitSession()
    try {
      listOfBlogsAndAuthors.blogs.map { blog =>
        SQL("INSERT INTO blog(id , wordpress_id, published_on, title)" +
          " SELECT ?, ?, ?, ? WHERE NOT EXISTS (SELECT id FROM blog WHERE id = ?)")
          .bind(blog.blogId, blog.authorLogin, blog.publishedOn, blog.title, blog.blogId)
          .update().apply()
      }
    }
    finally {
      session.close()
    }
  }

  /**
   * Stores overall score of knolder in all_time table.
   *
   * @param scores   AuthorScore case class object which contains score of each knolder.
   * @param authorId Knolder id wrapped in option.
   * @return Integer which display the status of query execution.
   */
  def insertAllTimeData(scores: AuthorScore, authorId: Option[Int]): Int = {
    implicit val session: DBSession = DB.autoCommitSession()
    try {
      SQL("INSERT INTO all_time(knolder_id, overall_score, overall_rank) values (?,?,?)")
        .bind(authorId, scores.score, 0).update().apply()
    }
    finally {
      session.close()
    }
  }
}
