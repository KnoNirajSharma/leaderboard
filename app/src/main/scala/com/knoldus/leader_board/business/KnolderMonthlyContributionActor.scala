package com.knoldus.leader_board.business

import akka.actor.{Actor, ActorRef}
import com.knoldus.leader_board._
import com.knoldus.leader_board.infrastructure.WriteMonthlyContribution
import com.typesafe.scalalogging.LazyLogging

case object UpdateAndInsertMonthlyContribution

class KnolderMonthlyContributionActor(allTimeReputationActorRef: ActorRef, monthlyReputationActorRef: ActorRef,
                                      quarterlyReputationActorRef: ActorRef, knolderMonthlyContribution: KnolderMonthlyContribution,
                                      writeMonthlyContribution: WriteMonthlyContribution) extends Actor with LazyLogging {


  override def receive: Receive = {

    case UpdateAndInsertMonthlyContribution => val firstDayOfCurrentMonth = IndianTime.currentTime.withDayOfMonth(1).toLocalDate
      val currentDayOfCurrentMonth = IndianTime.currentTime.toLocalDate
      if (firstDayOfCurrentMonth == currentDayOfCurrentMonth) {
        logger.info("fetching and updating last month score if the 1st day of month after fetching blog details")
        val month = IndianTime.currentTime.minusMonths(1).getMonth.toString
        val year = IndianTime.currentTime.minusMonths(1).getYear
        val knolderMonthlyContributionScore = knolderMonthlyContribution.getKnolderMonthlyContribution(month, year)
        writeMonthlyContribution.insertKnolderMonthlyContribution(knolderMonthlyContributionScore)
        writeMonthlyContribution.updateKnolderMonthlyContribution(knolderMonthlyContributionScore)
      } else {
        logger.info("fetching and updating current month score if there is no 1st day of month after fetching blog details")
        val month = IndianTime.currentTime.getMonth.toString
        val year = IndianTime.currentTime.getYear
        val knolderMonthlyContributionScore = knolderMonthlyContribution.getKnolderMonthlyContribution(month, year)
        writeMonthlyContribution.insertKnolderMonthlyContribution(knolderMonthlyContributionScore)
        writeMonthlyContribution.updateKnolderMonthlyContribution(knolderMonthlyContributionScore)
      }
      self ! CalculateReputation
      sender() ! "monthly contribution saved"

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
