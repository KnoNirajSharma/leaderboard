package com.knoldus.leader_board.business

import akka.actor.Actor
import com.knoldus.leader_board.infrastructure.WriteMonthlyReputation
import com.typesafe.scalalogging.LazyLogging

class MonthlyReputationActor(monthlyReputation: MonthlyReputation,
                             writeMonthlyReputation: WriteMonthlyReputation) extends Actor with LazyLogging {
  override def receive: Receive = {
    case "write monthly reputation" => val monthlyReputations = monthlyReputation.getKnolderMonthlyReputation
      logger.info("Storing or updating monthly reputation of knolder.")
      writeMonthlyReputation.insertMonthlyReputationData(monthlyReputations)
      writeMonthlyReputation.updateMonthlyReputationData(monthlyReputations)
      sender() ! "monthly reputation saved"
    case _ => sender() ! "invalid message"
  }
}
