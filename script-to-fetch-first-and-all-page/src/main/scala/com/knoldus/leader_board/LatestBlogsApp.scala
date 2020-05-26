package com.knoldus.leader_board

import com.knoldus.leader_board.business.{Blogs, BlogsImpl}
import com.knoldus.leader_board.infrastructure.{FetchData, FetchDataImpl, StoreData, StoreDataImpl}
import com.typesafe.config.{Config, ConfigFactory}
import com.typesafe.scalalogging.LazyLogging

object LatestBlogsApp extends App with LazyLogging {
  val config: Config = ConfigFactory.load()

  val fetchData: FetchData = new FetchDataImpl(config)
  val storeData: StoreData = new StoreDataImpl(config)
  val blogs: Blogs = new BlogsImpl(fetchData, config)
  storeData.insertBlog(blogs.getLatestBlogsFromAPI)
  logger.info("Blogs are stored successfully.")
}
