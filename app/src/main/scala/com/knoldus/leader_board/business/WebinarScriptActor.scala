package com.knoldus.leader_board.business

import akka.actor.{Actor, ActorRef}
import com.knoldus.leader_board._
import com.knoldus.leader_board.infrastructure.StoreWebinar
import com.typesafe.scalalogging._

class WebinarScriptActor(allTimeReputationActorRef: ActorRef, monthlyReputationActorRef: ActorRef,
                         quarterlyReputationActorRef: ActorRef, storeWebinar: StoreWebinar, webinar: WebinarSpreadSheetData) extends Actor
  with LazyLogging {
  override def receive: Receive = {
    case ExecuteWebinarScript => logger.info("Storing webinars.")
      val webinarData = webinar.getWebinarData
      storeWebinar.insertWebinar(webinarData)
      logger.info("webinars stored successfully.")
      self ! CalculateReputation
      sender() ! "stored webinars"

    case CalculateReputation => logger.info("Calculating reputation")
      allTimeReputationActorRef ! WriteAllTimeReputation
      monthlyReputationActorRef ! WriteMonthlyReputation
      val firstDayOfCurrentMonth = IndianTime.currentTime.withDayOfMonth(1).toLocalDate
      val currentDayOfCurrentMonth = IndianTime.currentTime.toLocalDate
      if (firstDayOfCurrentMonth == currentDayOfCurrentMonth) {
        quarterlyReputationActorRef ! WriteQuarterlyReputation
      }
      sender() ! "calculated and stored reputation"

    case _ => sender() ! "invalid message"
  }
}
