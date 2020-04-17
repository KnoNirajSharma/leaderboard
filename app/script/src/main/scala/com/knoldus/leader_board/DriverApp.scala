package com.knoldus.leader_board

import akka.actor.ActorSystem
import akka.stream.SystemMaterializer
import com.knoldus.leader_board.business.{Blogs, BlogsImpl}
import com.knoldus.leader_board.infrastructure.{FetchData, FetchDataImpl, StoreData, StoreDataImpl}
import com.typesafe.config.{Config, ConfigFactory}

import scala.concurrent.{Await, ExecutionContextExecutor}
import scala.concurrent.duration._

object DriverApp extends App {
  implicit val system: ActorSystem = ActorSystem()
  implicit val materializer: SystemMaterializer = SystemMaterializer(system)
  implicit val executionContext: ExecutionContextExecutor = system.dispatcher
  val config: Config = ConfigFactory.load()
  val databaseConnection = new DatabaseConnection(config)
  val fetchData: FetchData = new FetchDataImpl(databaseConnection)
  val storeData: StoreData = new StoreDataImpl(databaseConnection)
  val blogs: Blogs = new BlogsImpl(fetchData, config)

  val getAllBlogsAndAuthors = blogs.getAllBlogsAndAuthors
  val listOfBlogsAndAuthors = Await.result(getAllBlogsAndAuthors, 3.minutes)
  val storedKnolders = storeData.insertKnolder(listOfBlogsAndAuthors)
  val storedBlogs = storeData.insertBlog(listOfBlogsAndAuthors)
}
