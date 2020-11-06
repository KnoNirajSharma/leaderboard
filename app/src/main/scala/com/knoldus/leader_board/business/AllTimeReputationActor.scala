package com.knoldus.leader_board.business

import akka.actor.Actor
import com.knoldus.leader_board.WriteAllTimeReputation
import com.knoldus.leader_board.infrastructure.WriteAllTimeReputation
import com.typesafe.scalalogging.LazyLogging

class AllTimeReputationActor(allTimeReputation: AllTimeReputation,
                             writeAllTimeReputation: WriteAllTimeReputation) extends Actor with LazyLogging {
  override def receive: Receive = {
    case WriteAllTimeReputation => val allTimeReputations = allTimeReputation.getKnolderReputation
      logger.info("Storing or updating all time reputation of knolder.")
      writeAllTimeReputation.insertAllTimeReputationData(allTimeReputations)
      writeAllTimeReputation.updateAllTimeReputationData(allTimeReputations)
      sender() ! AllTimeReputationSaved
    case _ => sender() ! InvalidMessage
  }
}
