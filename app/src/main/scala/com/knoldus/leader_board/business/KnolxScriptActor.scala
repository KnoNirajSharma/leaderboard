package com.knoldus.leader_board.business

import akka.actor.{Actor, ActorRef}
import com.knoldus.leader_board._
import com.knoldus.leader_board.infrastructure.{StoreKnolx, WriteMonthlyContribution}
import com.typesafe.scalalogging._

class KnolxScriptActor(allTimeReputationActorRef: ActorRef, monthlyReputationActorRef: ActorRef,
                       quarterlyReputationActorRef: ActorRef, storeKnolx: StoreKnolx, knolx: Knolxs,
                       knolderMonthlyContribution: KnolderMonthlyContribution,
                       writeMonthlyContribution: WriteMonthlyContribution) extends Actor
  with LazyLogging {
  override def receive: Receive = {
    case ExecuteKnolxScript => logger.info("Storing knolx.")
      val latestKnolx = knolx.getLatestKnolxFromAPI
      storeKnolx.insertKnolx(latestKnolx)
      logger.info("Knolx stored successfully.")
      self ! UpdateAndInsertMonthlyContribution
      sender() ! "stored knolx"

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
