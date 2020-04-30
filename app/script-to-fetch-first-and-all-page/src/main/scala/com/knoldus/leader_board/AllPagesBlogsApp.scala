package com.knoldus.leader_board

import akka.actor.ActorSystem
import com.knoldus.leader_board.business._
import com.knoldus.leader_board.infrastructure._
import com.typesafe.config.{Config, ConfigFactory}
import com.typesafe.scalalogging._

import scala.concurrent.duration._
import scala.concurrent.{Await, ExecutionContextExecutor}

object AllPagesBlogsApp extends App with LazyLogging {
  implicit val system: ActorSystem = ActorSystem()
  implicit val executionContext: ExecutionContextExecutor = system.dispatcher
  val config: Config = ConfigFactory.load()

  val databaseConnection = new DatabaseConnection(config)
  val fetchData: FetchData = new FetchDataImpl(databaseConnection)
  val storeData: StoreData = new StoreDataImpl(databaseConnection)
  val blogs: Blogs = new BlogsImpl(fetchData, config)

  val getBlogsFromAPI = blogs.getAllPagesBlogsFromAPI
  val listOfBlogs = Await.result(getBlogsFromAPI, 3.minutes)
  storeData.insertBlog(listOfBlogs)
  logger.info("Blogs stored successfully!")
}
