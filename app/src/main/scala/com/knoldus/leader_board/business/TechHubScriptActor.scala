package com.knoldus.leader_board.business

import akka.actor.{Actor, ActorRef}
import com.knoldus.leader_board._
import com.knoldus.leader_board.infrastructure.StoreTechHub
import com.typesafe.scalalogging._

class TechHubScriptActor(storeTechHub: StoreTechHub, techHubData: TechHubData, knolderMonthlyContributionActorRef: ActorRef
                        ) extends Actor
  with LazyLogging {
  override def receive: Receive = {
    case ExecuteTechHubScript => logger.info("Storing techhub.")
      val latestTechHub = techHubData.getLatestTechHubTemplates
      storeTechHub.insertTechHub(latestTechHub)
      logger.info("Techhub stored successfully.")
      self ! WriteMonthlyContribution
      sender() ! "stored techhub"

    case WriteMonthlyContribution => logger.info("write monthly contribution table")
      knolderMonthlyContributionActorRef ! UpdateAndInsertMonthlyContribution
      sender() ! "stored monthly contribution details"

    case _ => sender() ! "invalid message"
  }
}
