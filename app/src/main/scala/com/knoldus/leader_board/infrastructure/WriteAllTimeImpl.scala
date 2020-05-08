package com.knoldus.leader_board.infrastructure

import java.sql.Connection

import com.knoldus.leader_board.{DatabaseConnection, KnolderBlogCount}
import com.typesafe.config.Config
import com.typesafe.scalalogging.LazyLogging
import scalikejdbc.{DB, DBSession, SQL}

class WriteAllTimeImpl(config: Config) extends WriteAllTime with LazyLogging {
  implicit val connection: Connection = DatabaseConnection.connection(config)
  implicit val session: DBSession = DB.autoCommitSession()

  /**
   * Verifies whether knolder already exist in all_time table or not, if not then it inserts it into the table with its
   * blog count.
   *
   * @param numberOfBlogsOfKnolders List of blog count of knolders along with their knolder id fetched from all time
   *                                table.
   * @return List of Integer which displays the status of query execution.
   */
  override def insertAllTimeData(numberOfBlogsOfKnolders: List[KnolderBlogCount]): List[Int] = {
    logger.info("Inserting blog count of knolder in all time table.")
    numberOfBlogsOfKnolders.filter { numberOfBlogsOfKnolder =>
      numberOfBlogsOfKnolder.knolderId.isEmpty
    }.map { numberOfBlogsOfKnolder =>
      SQL("INSERT INTO all_time(knolder_id, number_of_blogs) VALUES (?,?)")
        .bind(numberOfBlogsOfKnolder.blogCount.knolderId, numberOfBlogsOfKnolder.blogCount.numberOfBlogs)
        .update().apply()
    }
  }

  /**
   * Verifies whether knolder already exist in all_time table or not, if it does then it updates its blog count.
   *
   * @param numberOfBlogsOfKnolders List of blog count of knolders along with their knolder id fetched from all time
   *                                table.
   * @return List of Integer which displays the status of query execution.
   */
  override def updateAllTimeData(numberOfBlogsOfKnolders: List[KnolderBlogCount]): List[Int] = {
    logger.info("Updating blog count of knolder in all time table.")
    numberOfBlogsOfKnolders.filter { numberOfBlogsOfKnolder =>
      numberOfBlogsOfKnolder.knolderId.isDefined
    }.map { numberOfBlogsOfKnolder =>
      SQL("UPDATE all_time SET number_of_blogs = ? WHERE knolder_id = ?")
        .bind(numberOfBlogsOfKnolder.blogCount.numberOfBlogs, numberOfBlogsOfKnolder.blogCount.knolderId)
        .update().apply()
    }
  }
}
