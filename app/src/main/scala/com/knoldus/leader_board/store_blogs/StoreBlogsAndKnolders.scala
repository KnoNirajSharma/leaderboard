package com.knoldus.leader_board.store_blogs

import java.sql.SQLException

import com.knoldus.leader_board.{BlogAuthor, DatabaseConnection}

class StoreBlogsAndKnolders(databaseConnection: DatabaseConnection) {
  /**
   * Stores list of blogs on blog table and list of knolders in knolder table.
   *
   * @param listOfBlogsAndAuthors BlogAuthor case class object which contains list of all blogs and list of
   *                              all knolders.
   * @return Message specifying data is stored and database connection is closed.
   */
  def storeBlogsAndKnolders(listOfBlogsAndAuthors: BlogAuthor): String = {
    try {
      val connection = databaseConnection.connection
      listOfBlogsAndAuthors.authors.foreach { author =>
        val insertKnolder = connection
          .prepareStatement("INSERT INTO knolder(full_name, wordpress_id) SELECT ?, ? " +
            "WHERE NOT EXISTS (SELECT wordpress_id FROM knolder WHERE wordpress_id = ?)")
        author.authorName.foreach(authorName => insertKnolder.setString(1, authorName))
        author.authorLogin.foreach(authorLogin => insertKnolder.setString(2, authorLogin))
        author.authorLogin.foreach(authorLogin => insertKnolder.setString(3, authorLogin))
        insertKnolder.executeUpdate()
        insertKnolder.close()
      }

      listOfBlogsAndAuthors.blogs.foreach { blog =>
        val insertBlog = connection
          .prepareStatement("INSERT INTO blog(id , wordpress_id, published_on, title) SELECT ?, ?, ?, ? " +
            "WHERE NOT EXISTS (SELECT id FROM blog WHERE id = ?)")
        blog.blogId.foreach(blogId => insertBlog.setInt(1, blogId))
        blog.authorLogin.foreach(authorLogin => insertBlog.setString(2, authorLogin))
        insertBlog.setTimestamp(3, blog.publishedOn)
        blog.title.foreach(title => insertBlog.setString(4, title))
        blog.blogId.foreach(blogId => insertBlog.setInt(5, blogId))
        insertBlog.executeUpdate()
        insertBlog.close()
      }
      "connection closed"
    }
    catch {
      case ex: SQLException => throw new SQLException(ex)
      case ex: Exception => throw new Exception(ex)
    }
    finally {
      databaseConnection.connection.close()
    }
  }
}
