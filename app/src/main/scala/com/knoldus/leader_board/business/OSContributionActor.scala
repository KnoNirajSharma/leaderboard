package com.knoldus.leader_board.business

import akka.actor.{Actor, ActorRef}
import com.knoldus.leader_board._
import com.knoldus.leader_board.infrastructure.StoreOSContributionDetails
import com.typesafe.scalalogging._

class OSContributionActor(allTimeReputationActorRef: ActorRef, monthlyReputationActorRef: ActorRef,
                          quarterlyReputationActorRef: ActorRef, storeOSContribution: StoreOSContributionDetails,
                          osContributionData: OSContributionData) extends Actor
  with LazyLogging {
  override def receive: Receive = {
    case ExecuteOSContributionScript => logger.info("Storing os contribution data.")
      val osContributionDataList = osContributionData.getOSContributionData
      storeOSContribution.insertOSContribution(osContributionDataList)
      logger.info("os contribution data stored successfully.")
      self ! CalculateReputation
      sender() ! "stored os contribution data"

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
