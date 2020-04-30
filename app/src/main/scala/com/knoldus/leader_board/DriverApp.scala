package com.knoldus.leader_board

import akka.actor.ActorSystem
import com.knoldus.leader_board.application.{AllTimeDataOnAPI, AllTimeDataOnAPIImpl}
import com.knoldus.leader_board.business._
import com.knoldus.leader_board.infrastructure._
import com.typesafe.config.{Config, ConfigFactory}

import scala.concurrent.ExecutionContextExecutor

object DriverApp extends App {
  implicit val system: ActorSystem = ActorSystem()
  implicit val executionContext: ExecutionContextExecutor = system.dispatcher
  val config: Config = ConfigFactory.load()

  val databaseConnection = new DatabaseConnection(config)
  val readKnolder = new ReadKnolderImpl(databaseConnection)
  val readBlog = new ReadBlogImpl(databaseConnection)
  val readAllTime = new ReadAllTimeImpl(databaseConnection)
  val writeAllTime = new WriteAllTimeImpl(databaseConnection)
  val numberOfBlogsPerKnolder: NumberOfBlogsPerKnolder = new NumberOfBlogsPerKnolderImpl(readKnolder: ReadKnolder,
    readBlog: ReadBlog, readAllTime: ReadAllTime, writeAllTime: WriteAllTime)
  val overallReputation: OverallReputation = new OverallReputationImpl(readAllTime, config)
  val allTimeDataOnAPI: AllTimeDataOnAPI = new AllTimeDataOnAPIImpl(overallReputation, config)

  val blogCounts = numberOfBlogsPerKnolder.getNumberOfBlogsPerKnolder
  val knolderBlogCounts = numberOfBlogsPerKnolder.getKnolderBlogCount(blogCounts)
  numberOfBlogsPerKnolder.insertBlogCount(knolderBlogCounts)
  numberOfBlogsPerKnolder.updateBlogCount(knolderBlogCounts)
  allTimeDataOnAPI.pushAllTimeDataOnAPI
}
