package com.knoldus.leader_board.business

import akka.actor.{Actor, ActorRef}
import com.knoldus.leader_board._
import com.knoldus.leader_board.infrastructure.StoreKnolx
import com.typesafe.scalalogging._

class KnolxScriptActor(storeKnolx: StoreKnolx, knolx: Knolxs, knolderMonthlyContributionActorRef: ActorRef
                      ) extends Actor
  with LazyLogging {
  override def receive: Receive = {
    case ExecuteKnolxScript => logger.info("Storing knolx.")
      val latestKnolx = knolx.getLatestKnolxFromAPI
      storeKnolx.insertKnolx(latestKnolx)
      logger.info("Knolx stored successfully.")
      self ! WriteMonthlyContribution
      sender() ! "stored knolx"

    case WriteMonthlyContribution => logger.info("write monthly contribution table")
      knolderMonthlyContributionActorRef ! UpdateAndInsertMonthlyContribution
      sender() ! "stored monthly contribution details"

    case _ => sender() ! "invalid message"
  }
}
