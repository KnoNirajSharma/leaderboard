package com.knoldus.leader_board.business

import akka.actor.{Actor, ActorRef}
import com.knoldus.leader_board._
import com.knoldus.leader_board.infrastructure.{WriteAllTimeReputation => _, WriteMonthlyReputation => _, WriteQuarterlyReputation => _, _}
import com.typesafe.scalalogging._

class OtherContributionActor(storeOSContribution: StoreOSContributionDetails,
                             storeConferenceDetails: StoreConferenceDetails, storeBooksContribution: StoreBooksContribution,
                             storeResearchPapersContribution: StoreResearchPapersContribution, otherContributionData: OtherContributionData,
                             knolderMonthlyContributionActorRef: ActorRef
                            ) extends Actor
  with LazyLogging {
  override def receive: Receive = {
    case ExecuteOtherContributionScript => logger.info("Storing other contribution data.")
      val otherContributionDataList = otherContributionData.getOtherContributionData
      storeOSContribution.insertOSContribution(otherContributionDataList)
      logger.info("os contribution data stored successfully.")
      storeConferenceDetails.insertConferenceDetails(otherContributionDataList)
      logger.info("conference contribution data stored successfully.")
      storeBooksContribution.insertBooksContributionDetails(otherContributionDataList)
      logger.info("books contribution data stored successfully.")
      storeResearchPapersContribution.insertResearchPaperContributionDetails(otherContributionDataList)
      logger.info("research paper contribution data stored successfully.")
      self ! WriteMonthlyContribution
      sender() ! StoredOtherContributionData

    case WriteMonthlyContribution => logger.info("write monthly contribution table")
      knolderMonthlyContributionActorRef ! UpdateAndInsertMonthlyContribution
      sender() ! StoredMonthlyContributionDetails

    case _ => sender() ! InvalidMessage
  }
}
