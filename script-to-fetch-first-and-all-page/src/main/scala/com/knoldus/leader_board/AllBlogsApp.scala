package com.knoldus.leader_board

import com.knoldus.leader_board.business.{Blogs, BlogsImpl, URLResponse}
import com.knoldus.leader_board.infrastructure.{StoreData, StoreDataImpl}
import com.typesafe.config.{Config, ConfigFactory}
import com.typesafe.scalalogging.LazyLogging

object AllBlogsApp extends App with LazyLogging {
  val config: Config = ConfigFactory.load()
  val storeData: StoreData = new StoreDataImpl(config)
  val URLResponse: URLResponse = new URLResponse
  val blogs: Blogs = new BlogsImpl(URLResponse, config)
  val blogsList = blogs.getAllBlogsFromAPI
  storeData.insertBlog(blogsList)
  storeData.updateBlog(blogsList)
  logger.info("Blogs are stored successfully.")
}
