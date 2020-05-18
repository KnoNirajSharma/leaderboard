package com.knoldus.leader_board

import java.time.{LocalTime, ZoneId, ZonedDateTime}

import akka.actor.ActorSystem
import com.knoldus.leader_board.application.{AllTimeDataOnAPI, AllTimeDataOnAPIImpl}
import com.knoldus.leader_board.business._
import com.knoldus.leader_board.infrastructure._
import com.typesafe.config.{Config, ConfigFactory}

import scala.concurrent.ExecutionContextExecutor
import scala.concurrent.duration._

object DriverApp extends App {
  implicit val system: ActorSystem = ActorSystem()
  implicit val executionContext: ExecutionContextExecutor = system.dispatcher
  val config: Config = ConfigFactory.load()

  val readBlog = new ReadBlogImpl(config)
  val readAllTime = new ReadAllTimeImpl(config)
  val writeAllTime = new WriteAllTimeImpl(config)
  val numberOfBlogsPerKnolder: NumberOfBlogsPerKnolder = new NumberOfBlogsPerKnolderImpl(readBlog, readAllTime)
  val overallReputation: OverallReputation = new OverallReputationImpl(readAllTime, config)
  val readAllTimeReputation: ReadAllTimeReputation = new ReadAllTimeReputationImpl(config)
  val reputationPerKnolder: ReputationPerKnolder = new ReputationPerKnolderImpl(overallReputation, readAllTimeReputation)
  val writeAllTimeReputation: WriteAllTimeReputation = new WriteAllTimeReputationImpl(config)
  val allTimeDataOnAPI: AllTimeDataOnAPI = new AllTimeDataOnAPIImpl(readAllTimeReputation, config)

  val indiaCurrentTime = ZonedDateTime.now(ZoneId.of("Asia/Calcutta"))
  val startTimeOfCalculateBlogCount = LocalTime.of(0, 0, 0,0).toSecondOfDay
  val totalSecondsOfDayTillCurrentTime = indiaCurrentTime.toLocalTime.toSecondOfDay
  val startTimeToCalculateScoreAndRank = LocalTime.of(1, 0, 0,0).toSecondOfDay
  val secondsInDay=24*60*60

  val calculatedTimeWhenBlogCountTaskStart =
    if (startTimeOfCalculateBlogCount - totalSecondsOfDayTillCurrentTime < 0) {
      (secondsInDay + startTimeOfCalculateBlogCount - totalSecondsOfDayTillCurrentTime)
    } else {
      startTimeOfCalculateBlogCount - totalSecondsOfDayTillCurrentTime
    }
  val calculatedTimeWhenStoreScoreAndRankTaskStart =
    if (startTimeToCalculateScoreAndRank - totalSecondsOfDayTillCurrentTime < 0) {
      (secondsInDay + startTimeToCalculateScoreAndRank - totalSecondsOfDayTillCurrentTime)
    } else {
      startTimeToCalculateScoreAndRank - totalSecondsOfDayTillCurrentTime
    }

  val taskCountAndStoreBlogs = new Runnable {
    override def run() {
      val knolderBlogCounts = numberOfBlogsPerKnolder.getKnolderBlogCount
      writeAllTime.insertAllTimeData(knolderBlogCounts)
      writeAllTime.updateAllTimeData(knolderBlogCounts)
    }
  }

  val taskCalculateAndStoreRank = new Runnable {
    override def run() {
      val knolderReputations = reputationPerKnolder.getKnolderReputation
      writeAllTimeReputation.insertAllTimeReputationData(knolderReputations)
      writeAllTimeReputation.updateAllTimeReputationData(knolderReputations)
    }
  }

  system.scheduler.scheduleAtFixedRate(calculatedTimeWhenBlogCountTaskStart.seconds, 24.hours)(taskCountAndStoreBlogs)
  system.scheduler.scheduleAtFixedRate(calculatedTimeWhenStoreScoreAndRankTaskStart.seconds, 24.hours)(taskCalculateAndStoreRank)
  allTimeDataOnAPI.displayAllTimeDataOnAPI
}

