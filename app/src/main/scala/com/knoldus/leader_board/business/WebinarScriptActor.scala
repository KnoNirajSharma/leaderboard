package com.knoldus.leader_board.business

import akka.actor.{Actor, ActorRef}
import com.knoldus.leader_board._
import com.knoldus.leader_board.infrastructure.StoreWebinar
import com.typesafe.scalalogging._

class WebinarScriptActor(storeWebinar: StoreWebinar, webinar: WebinarSpreadSheetData,
                         knolderMonthlyContributionActorRef: ActorRef
                        ) extends Actor
  with LazyLogging {
  override def receive: Receive = {
    case ExecuteWebinarScript => logger.info("Storing webinars.")
      val webinarData = webinar.getWebinarData
      storeWebinar.insertWebinar(webinarData)
      logger.info("webinars stored successfully.")
      self ! WriteMonthlyContribution
      sender() ! "stored webinars"

    case WriteMonthlyContribution => logger.info("write monthly contribution table")
      knolderMonthlyContributionActorRef ! UpdateAndInsertMonthlyContribution
      sender() ! "stored monthly contribution details"

    case _ => sender() ! "invalid message"
  }
}
