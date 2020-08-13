package com.knoldus.leader_board.business

import akka.actor.{Actor, ActorRef}
import com.knoldus.leader_board._
import com.knoldus.leader_board.infrastructure.StoreTechHub
import com.typesafe.scalalogging._

class TechHubScriptActor(allTimeReputationActorRef: ActorRef, monthlyReputationActorRef: ActorRef,
                         quarterlyReputationActorRef: ActorRef, storeTechHub: StoreTechHub, techHubData: TechHubData) extends Actor
  with LazyLogging {
  override def receive: Receive = {
    case ExecuteTechHubScript => logger.info("Storing techhub.")
      val latestTechHub = techHubData.getLatestTechHubTemplates
      storeTechHub.insertTechHub(latestTechHub)
      logger.info("Techhub stored successfully.")
      self ! CalculateReputation
      sender() ! "stored techhub"

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
