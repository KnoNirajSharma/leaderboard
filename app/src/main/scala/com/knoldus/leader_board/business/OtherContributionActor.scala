package com.knoldus.leader_board.business

import akka.actor.{Actor, ActorRef}
import com.knoldus.leader_board._
import com.knoldus.leader_board.infrastructure.{StoreConferenceDetails, StoreOSContributionDetails}
import com.typesafe.scalalogging._

class OtherContributionActor(allTimeReputationActorRef: ActorRef, monthlyReputationActorRef: ActorRef,
                          quarterlyReputationActorRef: ActorRef, storeOSContribution: StoreOSContributionDetails,
                          storeConferenceDetails: StoreConferenceDetails,otherContributionData: OtherContributionData) extends Actor
  with LazyLogging {
  override def receive: Receive = {
    case ExecuteOtherContributionScript => logger.info("Storing other contribution data.")
      val otherContributionDataList = otherContributionData.getOtherContributionData
      storeOSContribution.insertOSContribution(otherContributionDataList)
      logger.info("os contribution data stored successfully.")
      storeConferenceDetails.insertConferenceDetails(otherContributionDataList)
      logger.info("conference contribution data stored successfully.")
      self ! CalculateReputation
      sender() ! "stored other contribution data"

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
