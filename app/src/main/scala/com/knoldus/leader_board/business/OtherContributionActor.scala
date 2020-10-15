package com.knoldus.leader_board.business

import akka.actor.{Actor, ActorRef}
import com.knoldus.leader_board._
import com.knoldus.leader_board.infrastructure.{WriteAllTimeReputation => _, WriteMonthlyReputation => _, WriteQuarterlyReputation => _, _}
import com.typesafe.scalalogging._

class OtherContributionActor(allTimeReputationActorRef: ActorRef, monthlyReputationActorRef: ActorRef,
                             quarterlyReputationActorRef: ActorRef, storeOSContribution: StoreOSContributionDetails,
                             storeConferenceDetails: StoreConferenceDetails, storeBooksContribution: StoreBooksContribution,
                             storeResearchPapersContribution: StoreResearchPapersContribution, otherContributionData: OtherContributionData,
                             knolderMonthlyContribution: KnolderMonthlyContribution,
                             writeMonthlyContribution: WriteMonthlyContribution) extends Actor
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
      self ! UpdateAndInsertMonthlyContribution
      sender() ! "stored other contribution data"

    case UpdateAndInsertMonthlyContribution => val firstDayOfCurrentMonth = IndianTime.currentTime.withDayOfMonth(1).toLocalDate
      val currentDayOfCurrentMonth = IndianTime.currentTime.toLocalDate
      if (firstDayOfCurrentMonth == currentDayOfCurrentMonth) {
        logger.info("fetching and updating last month score")
        val month = IndianTime.currentTime.minusMonths(1).getMonth.toString
        val year = IndianTime.currentTime.minusMonths(1).getYear
        val knolderMonthlyContributionScore = knolderMonthlyContribution.getKnolderMonthlyContribution(month, year)
        writeMonthlyContribution.insertKnolderMonthlyContribution(knolderMonthlyContributionScore)
        writeMonthlyContribution.updateKnolderMonthlyContribution(knolderMonthlyContributionScore)
      } else {
        logger.info("fetching and updating current month score")
        val month = IndianTime.currentTime.getMonth.toString
        val year = IndianTime.currentTime.getYear
        val knolderMonthlyContributionScore = knolderMonthlyContribution.getKnolderMonthlyContribution(month, year)
        writeMonthlyContribution.insertKnolderMonthlyContribution(knolderMonthlyContributionScore)
        writeMonthlyContribution.updateKnolderMonthlyContribution(knolderMonthlyContributionScore)
      }
      self ! CalculateReputation

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
