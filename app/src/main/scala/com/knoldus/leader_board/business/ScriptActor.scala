package com.knoldus.leader_board.business

import akka.actor.{Actor, ActorRef}
import com.knoldus.leader_board.Constant
import com.knoldus.leader_board.infrastructure.StoreBlogs
import com.typesafe.scalalogging._

class ScriptActor(allTimeReputationActorRef: ActorRef, monthlyReputationActorRef: ActorRef,
                  quarterlyReputationActorRef: ActorRef, storeBlogs: StoreBlogs, blogs: Blogs) extends Actor
  with LazyLogging {
  override def receive: Receive = {
    case "execute blogs script" => logger.info("Storing blogs.")
      val latestBlogs = blogs.getLatestBlogsFromAPI
      storeBlogs.insertBlog(latestBlogs)
      logger.info("Blogs stored successfully.")
      self ! "calculate reputation"
      sender() ! "stored blogs"

    case "calculate reputation" => logger.info("Calculating reputation")
      allTimeReputationActorRef ! "write all time reputation"
      monthlyReputationActorRef ! "write monthly reputation"
      val firstDayOfCurrentMonth = Constant.CURRENT_TIME.withDayOfMonth(1).toLocalDate
      val currentDayOfCurrentMonth = Constant.CURRENT_TIME.toLocalDate
      if (firstDayOfCurrentMonth == currentDayOfCurrentMonth) {
        quarterlyReputationActorRef ! "write quarterly reputation"
      }
      sender() ! "calculated and stored reputation"

    case _ => sender() ! "invalid message"
  }
}
