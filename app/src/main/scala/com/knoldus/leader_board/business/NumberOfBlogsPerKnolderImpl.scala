package com.knoldus.leader_board.business

import com.knoldus.leader_board.infrastructure.{ReadAllTime, ReadBlog, ReadKnolder, WriteAllTime}
import com.knoldus.leader_board.{BlogCount, KnolderBlogCount}
import com.typesafe.scalalogging._

class NumberOfBlogsPerKnolderImpl(readKnolder: ReadKnolder, readBlog: ReadBlog, readAllTime: ReadAllTime,
                                  writeAllTime: WriteAllTime)
  extends NumberOfBlogsPerKnolder with LazyLogging {

  /**
   * Calculates number of blogs of each Knolder by using list of Knolders with Blogs and list of all Knolders.
   *
   * @return List of number of blogs of each Knolder.
   */
  override def getNumberOfBlogsPerKnolder: List[BlogCount] = {
    val knolders = readKnolder.fetchKnolders
    logger.info("Fetched details of knolders.")
    val knoldersWithBlogs = readBlog.fetchKnoldersWithBlogs
    logger.info("Fetched details of knolders with blogs.")
    logger.info("Calculating number of blogs of each knolder.")
    knolders.map { knolder =>
      BlogCount(knolder.knolderId, knolder.wordPressId, knolder.fullName,
        knoldersWithBlogs.count(knolderWithBlogs => knolderWithBlogs.wordpressId == knolder.wordPressId))
    }.filter(blogCount => blogCount.numberOfBlogs != 0)
  }

  /**
   * Gets knolder id of knolders from all time table.
   *
   * @param numberOfBlogsOfKnolders List of blog count of knolders.
   * @return List of blog count of knolders along with their knolder id fetched from all time table.
   */
  override def getKnolderBlogCount(numberOfBlogsOfKnolders: List[BlogCount]): List[KnolderBlogCount] = {
    numberOfBlogsOfKnolders.map { numberOfBlogsOfKnolder =>
      KnolderBlogCount(readAllTime.fetchKnolderIdFromAllTime(numberOfBlogsOfKnolder.knolderId),
        numberOfBlogsOfKnolder)
    }
  }

  /**
   * Verifies whether knolder already exist in all_time table or not, if not then it inserts it into the table with its
   * blog count.
   *
   * @param numberOfBlogsOfKnolders List of blog count of knolders along with their knolder id fetched from all time
   *                                table.
   * @return List of Integer which displays the status of query execution.
   */
  def insertBlogCount(numberOfBlogsOfKnolders: List[KnolderBlogCount]): List[Int] = {
    logger.info("Insert blog count of knolder in all time table.")
    numberOfBlogsOfKnolders.filter { numberOfBlogsOfKnolder =>
      numberOfBlogsOfKnolder.knolderId.isEmpty
    }.map { numberOfBlogsOfKnolder =>
      writeAllTime.insertAllTimeData(numberOfBlogsOfKnolder.blogCount)
    }
  }

  /**
   * Verifies whether knolder already exist in all_time table or not, if it does then it updates its blog count.
   *
   * @param numberOfBlogsOfKnolders List of blog count of knolders along with their knolder id fetched from all time
   *                                table.
   * @return List of Integer which displays the status of query execution.
   */
  def updateBlogCount(numberOfBlogsOfKnolders: List[KnolderBlogCount]): List[Int] = {
    logger.info("Update blog count of knolder in all time table.")
    numberOfBlogsOfKnolders.filter { numberOfBlogsOfKnolder =>
      numberOfBlogsOfKnolder.knolderId.isDefined
    }.map { numberOfBlogsOfKnolder =>
      writeAllTime.updateAllTimeData(numberOfBlogsOfKnolder.blogCount)
    }
  }
}
