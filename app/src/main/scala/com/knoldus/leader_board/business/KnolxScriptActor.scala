package com.knoldus.leader_board.business

import akka.actor.{Actor, ActorRef}
import com.knoldus.leader_board._
import com.knoldus.leader_board.infrastructure.StoreKnolx
import com.typesafe.scalalogging._

class KnolxScriptActor(allTimeReputationActorRef: ActorRef, monthlyReputationActorRef: ActorRef,
                       quarterlyReputationActorRef: ActorRef, storeKnolx: StoreKnolx, knolx: Knolxs) extends Actor
  with LazyLogging {
  override def receive: Receive = {
    case ExecuteKnolxScript => logger.info("Storing knolx.")
      val latestKnolx = knolx.getLatestKnolxFromAPI
      storeKnolx.insertKnolx(latestKnolx)
      logger.info("Knolx stored successfully.")
      self ! CalculateReputation
      sender() ! "stored knolx"

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
