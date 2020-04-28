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
  val fetchData: FetchData = new FetchDataImpl(databaseConnection)
  val storeData: StoreData = new StoreDataImpl(databaseConnection)
  val updateData: UpdateData = new UpdateDataImpl(databaseConnection)
  val numberOfBlogsPerKnolder: NumberOfBlogsPerKnolder = new NumberOfBlogsPerKnolderImpl(fetchData, updateData, storeData)
  val overallReputation: OverallReputation = new OverallReputationImpl(fetchData, config)
  val allTimeDataOnAPI: AllTimeDataOnAPI = new AllTimeDataOnAPIImpl(overallReputation, config)

  val blogCount = numberOfBlogsPerKnolder.getNumberOfBlogsPerKnolder
  numberOfBlogsPerKnolder.manageAllTimeData(blogCount)
  allTimeDataOnAPI.pushAllTimeDataOnAPI
}
