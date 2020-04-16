package com.knoldus.leader_board

import akka.actor.ActorSystem
import akka.stream.SystemMaterializer
import com.knoldus.leader_board.application.{AllTimeDataOnAPI, AllTimeDataOnAPIImpl}
import com.knoldus.leader_board.business._
import com.knoldus.leader_board.infrastructure._
import com.typesafe.config.{Config, ConfigFactory}

import scala.concurrent.duration._
import scala.concurrent.{Await, ExecutionContextExecutor}

object DriverApp extends App {
  implicit val system: ActorSystem = ActorSystem()
  implicit val materializer: SystemMaterializer = SystemMaterializer(system)
  implicit val executionContext: ExecutionContextExecutor = system.dispatcher
  val config: Config = ConfigFactory.load()
  val databaseConnection = new DatabaseConnection(config)
  val fetchData: FetchData = new FetchDataImpl(databaseConnection)
  val storeData: StoreData = new StoreDataImpl(databaseConnection)
  val overallRank: OverallRank = new OverallRankImpl(fetchData)
  val updateData: UpdateData = new UpdateDataImpl(databaseConnection, overallRank)
  val allTimeDataOnAPI: AllTimeDataOnAPI = new AllTimeDataOnAPIImpl(fetchData, config)
  val blogs: Blogs = new BlogsImpl(fetchData, config)
  val overallScore: OverallScore = new OverallScoreImpl(fetchData, storeData, updateData)

  val getAllBlogsAndAuthors = blogs.getAllBlogsAndAuthors
  val listOfBlogsAndAuthors = Await.result(getAllBlogsAndAuthors, 3.minutes)
  val storedKnolders = storeData.insertKnolder(listOfBlogsAndAuthors)
  val storedBlogs = storeData.insertBlog(listOfBlogsAndAuthors)
  val calculatedScores = overallScore.calculateScore(listOfBlogsAndAuthors)
  val storedScores = overallScore.manageScores(calculatedScores)
  val updatedRank = updateData.updateRank()
  allTimeDataOnAPI.pushAllTimeDataOnAPI
}
