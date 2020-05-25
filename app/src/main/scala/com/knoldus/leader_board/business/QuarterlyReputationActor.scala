package com.knoldus.leader_board.business

import akka.actor.Actor
import com.knoldus.leader_board.infrastructure.WriteQuarterlyReputation

import scala.concurrent.ExecutionContextExecutor

class QuarterlyReputationActor(quarterlyReputation: QuarterlyReputation,
                               writeQuarterlyReputation: WriteQuarterlyReputation) extends Actor {
  implicit val executionContext: ExecutionContextExecutor = context.dispatcher

  override def receive: Receive = {
    case "write quarterly reputation" => val quarterlyReputations = quarterlyReputation.getKnolderQuarterlyReputation
      writeQuarterlyReputation.insertQuarterlyReputationData(quarterlyReputations)
      writeQuarterlyReputation.updateQuarterlyReputationData(quarterlyReputations)
      sender() ! "quarterly reputation saved"
  }
}
