package com.knoldus.leader_board.business

import akka.actor.{Actor, ActorRef}
import com.knoldus.leader_board._
import com.knoldus.leader_board.infrastructure.StoreBlogs
import com.typesafe.scalalogging._

case object WriteMonthlyContribution

class BlogScriptActor(storeBlogs: StoreBlogs, blogs: Blogs, knolderMonthlyContributionActorRef: ActorRef) extends Actor
  with LazyLogging {
  override def receive: Receive = {
    case ExecuteBlogsScript => logger.info("Storing blogs.")
      val latestBlogs = blogs.getLatestBlogsFromAPI
      storeBlogs.insertBlog(latestBlogs)
      logger.info("Blogs stored successfully.")
      self ! WriteMonthlyContribution
      sender() ! "stored blogs"

    case WriteMonthlyContribution => logger.info("write monthly contribution table")
      knolderMonthlyContributionActorRef ! UpdateAndInsertMonthlyContribution
      sender() ! "stored monthly contribution details"

    case _ => sender() ! "invalid message"
  }
}
