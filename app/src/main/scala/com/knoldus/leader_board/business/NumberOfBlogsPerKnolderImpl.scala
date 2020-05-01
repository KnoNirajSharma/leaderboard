package com.knoldus.leader_board.business

import com.knoldus.leader_board.infrastructure.{ReadAllTime, ReadBlog, ReadKnolder, WriteAllTime}
import com.knoldus.leader_board.{BlogCount, KnolderBlogCount}
import com.typesafe.scalalogging._

class NumberOfBlogsPerKnolderImpl(readKnolder: ReadKnolder, readBlog: ReadBlog, readAllTime: ReadAllTime,
                                  writeAllTime: WriteAllTime)
  extends NumberOfBlogsPerKnolder with LazyLogging {

  /**
   * Calculates number of blogs of each knolder by using list of knolders with blogs and list of all knolders.
   *
   * @return List of number of blogs of each Knolder.
   */
  override def getNumberOfBlogsPerKnolder: List[BlogCount] = {
    val knolders = readKnolder.fetchKnolders
    logger.info("Fetching details of knolders with blogs.")
    val knoldersWithBlogs = readBlog.fetchKnoldersWithBlogs
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
    logger.info("Inserting blog count of knolder in all time table.")
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
    logger.info("Updating blog count of knolder in all time table.")
    numberOfBlogsOfKnolders.filter { numberOfBlogsOfKnolder =>
      numberOfBlogsOfKnolder.knolderId.isDefined
    }.map { numberOfBlogsOfKnolder =>
      writeAllTime.updateAllTimeData(numberOfBlogsOfKnolder.blogCount)
    }
  }
}
