package com.knoldus.leader_board

import com.knoldus.leader_board.business.{KnolxImpl, Knolxs, URLResponse}
import com.knoldus.leader_board.infrastructure.{StoreKnolx, StoreKnolxImpl}
import com.typesafe.config.{Config, ConfigFactory}
import com.typesafe.scalalogging.LazyLogging

object DriverApp extends App with LazyLogging {
  val config: Config = ConfigFactory.load()
  val storeData: StoreKnolx = new StoreKnolxImpl(config)
  val URLResponse: URLResponse = new URLResponse
  val knolx: Knolxs = new KnolxImpl(URLResponse, config)
  val knolxList = knolx.getAllKnolxDetails
  storeData.insertKnolx(knolxList)
  logger.info("knolx are stored successfully.")
}
