package com.knoldus.leader_board.business

import akka.actor.{Actor, ActorRef}
import com.knoldus.leader_board._
import com.knoldus.leader_board.infrastructure.StoreBlogs
import com.typesafe.scalalogging._

class BlogScriptActor(allTimeReputationActorRef: ActorRef, monthlyReputationActorRef: ActorRef,
                      quarterlyReputationActorRef: ActorRef, storeBlogs: StoreBlogs, blogs: Blogs) extends Actor
  with LazyLogging {
  override def receive: Receive = {
    case ExecuteBlogsScript => logger.info("Storing blogs.")
      val latestBlogs = blogs.getLatestBlogsFromAPI
      storeBlogs.insertBlog(latestBlogs)
      logger.info("Blogs stored successfully.")
      self ! CalculateReputation
      sender() ! "stored blogs"

    case CalculateReputation => logger.info("Calculating reputation")
      allTimeReputationActorRef ! WriteAllTimeReputation
      monthlyReputationActorRef ! WriteMonthlyReputation
      val firstDayOfCurrentMonth = Constant.CURRENT_TIME.withDayOfMonth(1).toLocalDate
      val currentDayOfCurrentMonth = Constant.CURRENT_TIME.toLocalDate
      if (firstDayOfCurrentMonth == currentDayOfCurrentMonth) {
        quarterlyReputationActorRef ! WriteQuarterlyReputation
      }
      sender() ! "calculated and stored reputation"

    case _ => sender() ! "invalid message"
  }
}
