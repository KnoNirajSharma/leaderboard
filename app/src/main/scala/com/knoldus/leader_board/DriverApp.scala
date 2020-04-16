package com.knoldus.leader_board

import com.knoldus.leader_board.application.AllTimeDataOnAPI
import com.knoldus.leader_board.business.{Blogs, OverallRank, OverallScore}
import com.knoldus.leader_board.infrastructure.{FetchData, StoreData, UpdateData}
import com.typesafe.config.{Config, ConfigFactory}

import scala.concurrent.Await
import scala.concurrent.duration._

object DriverApp extends App {
  val config: Config = ConfigFactory.load()
  val databaseConnection = new DatabaseConnection(config)
  val fetchData = new FetchData(databaseConnection)
  val storeData = new StoreData(databaseConnection)
  val overallRank = new OverallRank(fetchData)
  val updateData = new UpdateData(databaseConnection, overallRank)
  val allTimeDataOnAPI = new AllTimeDataOnAPI(fetchData, config)
  val blogs = new Blogs(fetchData, config)
  val overallScore = new OverallScore(fetchData, storeData, updateData)

  val getAllBlogsAndAuthors = blogs.getAllBlogsAndAuthors
  val listOfBlogsAndAuthors = Await.result(getAllBlogsAndAuthors, 3.minutes)
  val storedKnolders = storeData.insertKnolder(listOfBlogsAndAuthors)
  val storedBlogs = storeData.insertBlog(listOfBlogsAndAuthors)
  val calculatedScores = overallScore.calculateScore(listOfBlogsAndAuthors)
  val storedScores = overallScore.manageScores(calculatedScores)
  val updatedRank = updateData.updateRank()
  allTimeDataOnAPI.pushAllTimeDataOnAPI
}
