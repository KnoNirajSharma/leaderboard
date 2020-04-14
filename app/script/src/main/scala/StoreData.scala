import java.sql.SQLException

import scalikejdbc.{DB, SQL}

class StoreData(databaseConnection: DatabaseConnection) {


  /**
   * Stores list of blogs in blog table.
   *
   * @param listOfBlogsAndAuthors BlogAuthor case class object which contains list of all blogs and list of
   *                              all knolders.
   * @return Message specifying data is stored and database connection is closed.
   */
  def insertKnolder(listOfBlogsAndAuthors: BlogAuthor): List[Int] = {
    implicit val session = DB.autoCommitSession()

    try {

      listOfBlogsAndAuthors.authors.map { author =>

        SQL("INSERT INTO knolder(full_name, wordpress_id) SELECT ?, ? " +
          "WHERE NOT EXISTS (SELECT wordpress_id FROM knolder WHERE wordpress_id = ?)")
          .bind(author.authorName, author.authorLogin, author.authorLogin)
          .update().apply()


      }

    }
    catch {
      case ex: SQLException => throw new SQLException(ex)
      case ex: Exception => throw new Exception(ex)
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
    implicit val session = DB.autoCommitSession()

    try {

      listOfBlogsAndAuthors.blogs.map { blog =>

        SQL("INSERT INTO blog(id , wordpress_id, published_on, title)" +
          " SELECT ?, ?, ?, ? WHERE NOT EXISTS (SELECT id FROM blog WHERE id = ?)")
          .bind(blog.blogId, blog.authorLogin, blog.publishedOn, blog.title, blog.blogId)
          .update().apply()

      }

    }
    catch {
      case ex: SQLException => throw new SQLException(ex)
      case ex: Exception => throw new Exception(ex)
    }
    finally {
      session.close()
    }

  }

}
