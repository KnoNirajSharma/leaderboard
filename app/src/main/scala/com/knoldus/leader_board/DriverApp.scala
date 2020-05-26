package com.knoldus.leader_board

import java.time.{LocalTime, ZoneId, ZonedDateTime}

import akka.actor.ActorSystem
import com.knoldus.leader_board.application.{ReputationOnAPI, ReputationOnAPIImpl}
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
  val knolderRank: KnolderRank = new KnolderRankImpl
  val readAllTimeReputation: ReadAllTimeReputation = new ReadAllTimeReputationImpl(config)
  val allTimeScore: AllTimeScore = new AllTimeScoreImpl(readAllTime, config)
  val reputationPerKnolder: ReputationPerKnolder = new ReputationPerKnolderImpl(knolderRank, allTimeScore,
    readAllTimeReputation)
  val writeAllTimeReputation: WriteAllTimeReputation = new WriteAllTimeReputationImpl(config)
  val readMonthlyReputation: ReadMonthlyReputation = new ReadMonthlyReputationImpl(config)
  val monthlyScore: MonthlyScore = new MonthlyScoreImpl(readBlog, config)
  val monthlyReputationPerKnolder: MonthlyReputationPerKnolder = new MonthlyReputationPerKnolderImpl(knolderRank,
    readMonthlyReputation, monthlyScore)
  val writeMonthlyReputation: WriteMonthlyReputation = new WriteMonthlyReputationImpl(config)
  val reputationOnAPI: ReputationOnAPI = new ReputationOnAPIImpl(readAllTimeReputation, readMonthlyReputation, config)

  val indiaCurrentTime = ZonedDateTime.now(ZoneId.of("Asia/Calcutta"))
  val startTimeToCalculateBlogCount = LocalTime.of(0, 0, 0, 0).toSecondOfDay
  val totalSecondsOfDayTillCurrentTime = indiaCurrentTime.toLocalTime.toSecondOfDay
  val startTimeToCalculateAllTimeReputation = LocalTime.of(1, 0, 0, 0).toSecondOfDay
  val startTimeToCalculateMonthlyReputation = LocalTime.of(0, 0, 0, 0).toSecondOfDay
  val secondsInDay = 24 * 60 * 60

  val timeForBlogCount =
    if (startTimeToCalculateBlogCount - totalSecondsOfDayTillCurrentTime < 0) {
      secondsInDay + startTimeToCalculateBlogCount - totalSecondsOfDayTillCurrentTime
    } else {
      startTimeToCalculateBlogCount - totalSecondsOfDayTillCurrentTime
    }

  val timeForAllTimeReputation =
    if (startTimeToCalculateAllTimeReputation - totalSecondsOfDayTillCurrentTime < 0) {
      secondsInDay + startTimeToCalculateAllTimeReputation - totalSecondsOfDayTillCurrentTime
    } else {
      startTimeToCalculateAllTimeReputation - totalSecondsOfDayTillCurrentTime
    }

  val timeForMonthlyReputation =
    if (startTimeToCalculateMonthlyReputation - totalSecondsOfDayTillCurrentTime < 0) {
      secondsInDay + startTimeToCalculateMonthlyReputation - totalSecondsOfDayTillCurrentTime
    } else {
      startTimeToCalculateMonthlyReputation - totalSecondsOfDayTillCurrentTime
    }

  val taskToCountAndStoreBlogs = new Runnable {
    override def run() {
      val knolderBlogCounts = numberOfBlogsPerKnolder.getKnolderBlogCount
      writeAllTime.insertAllTimeData(knolderBlogCounts)
      writeAllTime.updateAllTimeData(knolderBlogCounts)
    }
  }

  val taskToCalculateAndStoreAllTimeReputation = new Runnable {
    override def run() {
      val knolderReputations = reputationPerKnolder.getKnolderReputation
      writeAllTimeReputation.insertAllTimeReputationData(knolderReputations)
      writeAllTimeReputation.updateAllTimeReputationData(knolderReputations)
    }
  }

  val taskToCalculateAndStoreMonthlyReputation = new Runnable {
    override def run() {
      val monthlyReputation = monthlyReputationPerKnolder.getKnolderMonthlyReputation
      writeMonthlyReputation.insertMonthlyReputationData(monthlyReputation)
      writeMonthlyReputation.updateMonthlyReputationData(monthlyReputation)
    }
  }

  system.scheduler.scheduleAtFixedRate(timeForBlogCount.seconds, 24.hours)(taskToCountAndStoreBlogs)
  system.scheduler.scheduleAtFixedRate(timeForAllTimeReputation.seconds, 24.hours)(taskToCalculateAndStoreAllTimeReputation)
  system.scheduler.scheduleAtFixedRate(timeForMonthlyReputation.seconds, 24.hours)(taskToCalculateAndStoreMonthlyReputation)

  reputationOnAPI.displayReputationOnAPI
}
