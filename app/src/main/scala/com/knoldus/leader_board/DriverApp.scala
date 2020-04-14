package com.knoldus.leader_board

import com.knoldus.leader_board.application.AllTimeDataOnAPI
import com.knoldus.leader_board.business.{Blogs, OverallRank, OverallScore}
import com.knoldus.leader_board.infrastructure.{FetchData, StoreData, UpdateData}

import scala.concurrent.Await
import scala.concurrent.duration._

object DriverApp extends App {
  val databaseConnection = new DatabaseConnection
  val fetchData = new FetchData(databaseConnection)
  val storeData = new StoreData(databaseConnection)
  val updateData = new UpdateData(databaseConnection)
  val allTimeDataOnAPI = new AllTimeDataOnAPI
  val blogs = new Blogs(fetchData)
  val overallRank = new OverallRank(fetchData)
  val overallScore = new OverallScore(fetchData, storeData, updateData)

  val getAllBlogsAndAuthors = blogs.getAllBlogsAndAuthors

  val listOfBlogsAndAuthors = Await.result(getAllBlogsAndAuthors, 3.minutes)

  val storedKnolders = storeData.insertKnolder(listOfBlogsAndAuthors)

  val storedBlogs = storeData.insertBlog(listOfBlogsAndAuthors)

  val calculatedScores = overallScore.calculateScore(listOfBlogsAndAuthors)

  val storedScores = overallScore.manageScores(calculatedScores)

  val calculatedRank = overallRank.calculateRank

  val updatedRank = updateData.updateRank(calculatedRank)

  val fetchedAllTimeData = fetchData.fetchAllTimeData

  allTimeDataOnAPI.pushAllTimeDataOnAPI(fetchedAllTimeData)
}
