package com.knoldus.leader_board.business

import akka.actor.{Actor, ActorRef}
import com.knoldus.leader_board._
import com.knoldus.leader_board.infrastructure.{StoreTechHub, WriteMonthlyContribution}
import com.typesafe.scalalogging._

class TechHubScriptActor(allTimeReputationActorRef: ActorRef, monthlyReputationActorRef: ActorRef,
                         quarterlyReputationActorRef: ActorRef, storeTechHub: StoreTechHub, techHubData: TechHubData,
                         knolderMonthlyContribution: KnolderMonthlyContribution,
                         writeMonthlyContribution: WriteMonthlyContribution) extends Actor
  with LazyLogging {
  override def receive: Receive = {
    case ExecuteTechHubScript => logger.info("Storing techhub.")
      val latestTechHub = techHubData.getLatestTechHubTemplates
      storeTechHub.insertTechHub(latestTechHub)
      logger.info("Techhub stored successfully.")
      self ! UpdateAndInsertMonthlyContribution
      sender() ! "stored techhub"

    case UpdateAndInsertMonthlyContribution => val firstDayOfCurrentMonth = IndianTime.currentTime.withDayOfMonth(1).toLocalDate
      val currentDayOfCurrentMonth = IndianTime.currentTime.toLocalDate
      if (firstDayOfCurrentMonth == currentDayOfCurrentMonth) {
        logger.info("fetching and updating last month score if the 1st day of month after fetching techhub details")
        val month = IndianTime.currentTime.minusMonths(1).getMonth.toString
        val year = IndianTime.currentTime.minusMonths(1).getYear
        val knolderMonthlyContributionScore = knolderMonthlyContribution.getKnolderMonthlyContribution(month, year)
        writeMonthlyContribution.insertKnolderMonthlyContribution(knolderMonthlyContributionScore)
        writeMonthlyContribution.updateKnolderMonthlyContribution(knolderMonthlyContributionScore)
      } else {
        logger.info("fetching and updating current month score if there is no 1st day of month after fetching techhub details")
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
