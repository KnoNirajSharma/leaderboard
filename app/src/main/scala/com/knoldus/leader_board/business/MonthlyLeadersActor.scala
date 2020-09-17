package com.knoldus.leader_board.business

import akka.actor.Actor
import com.knoldus.leader_board.infrastructure.StoreTopFiveLeaders
import com.typesafe.scalalogging.LazyLogging

case object StoreMonthlyLeaders

class MonthlyLeadersActor(storeTopFiveLeaders: StoreTopFiveLeaders) extends Actor with LazyLogging {
  override def receive: Receive = {
    case StoreMonthlyLeaders =>
      logger.info("Storing top five leaders of every month in hall of fame table.")
      storeTopFiveLeaders.insertTopFiveLeaders
      sender() ! "stored top five leaders"

    case _ => sender() ! "invalid message"
  }
}
