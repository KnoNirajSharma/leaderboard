package com.knoldus.leader_board

import com.knoldus.leader_board.business._
import com.knoldus.leader_board.infrastructure._
import com.typesafe.config.{Config, ConfigFactory}
import com.typesafe.scalalogging._

object AllPagesBlogsApp extends App with LazyLogging {
  val config: Config = ConfigFactory.load()

  val fetchData: FetchData = new FetchDataImpl(config)
  val storeData: StoreData = new StoreDataImpl(config)
  val blogs: Blogs = new BlogsImpl(fetchData, config)
  storeData.insertBlog(blogs.getAllPagesBlogsFromAPI)
  logger.info("Blogs are stored successfully.")
}
