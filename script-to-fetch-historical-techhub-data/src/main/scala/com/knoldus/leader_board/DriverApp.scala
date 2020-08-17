package com.knoldus.leader_board

import com.knoldus.leader_board.business.{TechHubData, TechHubDataImpl, URLResponse}
import com.knoldus.leader_board.infrastructure.{StoreTechHub, StoreTechHubImpl}
import com.typesafe.config.{Config, ConfigFactory}
import com.typesafe.scalalogging.LazyLogging

object DriverApp extends App with LazyLogging {
  val config: Config = ConfigFactory.load()
  val storeData: StoreTechHub = new StoreTechHubImpl(config)
  val URLResponse: URLResponse = new URLResponse
  val techHubObj: TechHubData = new TechHubDataImpl(URLResponse, config)
  val techHubList = techHubObj.getLatestTechHubTemplates
  storeData.insertTechHub(techHubList)
  logger.info("techhub data stored successfully.")
}
