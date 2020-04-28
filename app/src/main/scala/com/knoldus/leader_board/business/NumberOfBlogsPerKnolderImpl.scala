package com.knoldus.leader_board.business

import com.knoldus.leader_board.BlogCount
import com.knoldus.leader_board.infrastructure.{FetchData, StoreData, UpdateData}
import com.typesafe.scalalogging._

class NumberOfBlogsPerKnolderImpl(fetchData: FetchData, updateData: UpdateData, storeData: StoreData)
  extends NumberOfBlogsPerKnolder with LazyLogging {

  /**
   * Calculates number of blogs of each Knolder by using list of Knolders with Blogs and list of all Knolders.
   *
   * @return List of number of blogs of each Knolder.
   */
  override def getNumberOfBlogsPerKnolder: List[BlogCount] = {
    val knolders = fetchData.fetchKnolders
    logger.info("Fetched details of knolders.")
    val knoldersWithBlogs = fetchData.fetchKnoldersWithBlogs
    logger.info("Fetched details of knolders with blogs.")
    logger.info("Calculating number of blogs of each knolder.")
    knolders.map { knolder =>
      BlogCount(knolder.knolderId, knolder.wordPressId, knolder.fullName,
        knoldersWithBlogs.count(knolderWithBlogs => knolderWithBlogs.wordpressId == knolder.wordPressId))
    }.filter(blogCount => blogCount.numberOfBlogs != 0)
  }

  /**
   * Verifies whether knolder already exist in all_time table or not, if not then it inserts it into the table with its
   * blog count, if it does then it updates its blog count.
   *
   * @return List of Integer which displays the status of query execution.
   */
  override def manageAllTimeData(numberOfBlogsOfKnolders: List[BlogCount]): List[Int] = {
    numberOfBlogsOfKnolders.map { numberOfBlogsOfKnolder =>
      val knolderId = fetchData.fetchKnolderIdFromAllTime(numberOfBlogsOfKnolder.knolderId)
      logger.info("Fetched knolder's id from all time table.")
      knolderId match {
        case Some(_) => logger.info("Update latest blog count of knolder in all time table.")
          updateData.updateAllTimeData(numberOfBlogsOfKnolder)
        case None => logger.info("Insert blog count of knolder in all time table.")
          storeData.insertAllTimeData(numberOfBlogsOfKnolder)
      }
    }
  }
}
