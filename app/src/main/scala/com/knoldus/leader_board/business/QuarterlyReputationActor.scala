package com.knoldus.leader_board.business

import akka.actor.Actor
import com.knoldus.leader_board.WriteQuarterlyReputation
import com.knoldus.leader_board.infrastructure.WriteQuarterlyReputation
import com.typesafe.scalalogging.LazyLogging

class QuarterlyReputationActor(quarterlyReputation: QuarterlyReputation,
                               writeQuarterlyReputation: WriteQuarterlyReputation) extends Actor with LazyLogging {
  override def receive: Receive = {
    case WriteQuarterlyReputation => val quarterlyReputations = quarterlyReputation.getKnolderQuarterlyReputation
      logger.info("Storing or updating quarterly reputation of knolder.")
      writeQuarterlyReputation.insertQuarterlyReputationData(quarterlyReputations)
      writeQuarterlyReputation.updateQuarterlyReputationData(quarterlyReputations)
      sender() ! "quarterly reputation saved"
    case _ => sender() ! "invalid message"
  }
}
