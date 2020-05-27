package com.knoldus.leader_board

import java.time.LocalTime

import akka.actor.{ActorSystem, Props}
import com.knoldus.leader_board.application.{ReputationOnAPI, ReputationOnAPIImpl}
import com.knoldus.leader_board.business._
import com.knoldus.leader_board.infrastructure._
import com.typesafe.akka.extension.quartz.QuartzSchedulerExtension
import com.typesafe.config.{Config, ConfigFactory}

import scala.concurrent.ExecutionContextExecutor
import scala.concurrent.duration._

object DriverApp extends App {
  implicit val system: ActorSystem = ActorSystem()
  val scheduler = QuartzSchedulerExtension
  implicit val executionContext: ExecutionContextExecutor = system.dispatcher
  val config: Config = ConfigFactory.load()

  val knolderScore: KnolderScore = new KnolderScoreImpl(config)
  val knolderRank: KnolderRank = new KnolderRankImpl
  val readBlog = new ReadBlogImpl(config)
  val readAllTimeReputation: ReadAllTimeReputation = new ReadAllTimeReputationImpl(config)
  val writeAllTimeReputation: WriteAllTimeReputation = new WriteAllTimeReputationImpl(config)
  val allTimeReputation: AllTimeReputation = new AllTimeReputationImpl(readBlog, knolderRank, knolderScore,
    readAllTimeReputation)
  val readMonthlyReputation: ReadMonthlyReputation = new ReadMonthlyReputationImpl(config)
  val writeMonthlyReputation: WriteMonthlyReputation = new WriteMonthlyReputationImpl(config)
  val monthlyReputation: MonthlyReputation = new MonthlyReputationImpl(readBlog, knolderRank, knolderScore,
    readMonthlyReputation)
  val readQuarterlyReputation: ReadQuarterlyReputation = new ReadQuarterlyReputationImpl(config)
  val writeQuarterlyReputation: WriteQuarterlyReputation = new WriteQuarterlyReputationImpl(config)
  val quarterlyReputation: QuarterlyReputation = new QuarterlyReputationImpl(readBlog, knolderScore,
    readQuarterlyReputation)
  val fetchReputation: FetchReputation = new FetchReputationImpl(config)
  val fetchKnolderDetails: FetchKnolderDetails = new FetchKnolderDetailsImpl(config)
  val reputationOnAPI: ReputationOnAPI = new ReputationOnAPIImpl(fetchKnolderDetails, fetchReputation, config)

  val allTimeReputations = allTimeReputation.getKnolderReputation
  writeAllTimeReputation.insertAllTimeReputationData(allTimeReputations)
  writeAllTimeReputation.updateAllTimeReputationData(allTimeReputations)
  val monthlyReputations = monthlyReputation.getKnolderMonthlyReputation
  writeMonthlyReputation.insertMonthlyReputationData(monthlyReputations)
  writeMonthlyReputation.updateMonthlyReputationData(monthlyReputations)
  val quarterlyReputations = quarterlyReputation.getKnolderQuarterlyReputation
  writeQuarterlyReputation.insertQuarterlyReputationData(quarterlyReputations)
  writeQuarterlyReputation.updateQuarterlyReputationData(quarterlyReputations)
  reputationOnAPI.displayReputationOnAPI

  val indiaCurrentTime = Constant.CURRENT_TIME
  val totalSecondsOfDayTillCurrentTime = indiaCurrentTime.toLocalTime.toSecondOfDay
  val startTimeToCalculateAllTimeReputation = LocalTime.of(1, 0, 0, 0).toSecondOfDay
  val startTimeToCalculateMonthlyReputation = LocalTime.of(1, 0, 0, 0).toSecondOfDay
  val secondsInDay = 24 * 60 * 60
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
  val taskToCalculateAndStoreAllTimeReputation = new Runnable {
    override def run() {
      val allTimeReputations = allTimeReputation.getKnolderReputation
      writeAllTimeReputation.insertAllTimeReputationData(allTimeReputations)
      writeAllTimeReputation.updateAllTimeReputationData(allTimeReputations)
    }
  }
  val taskToCalculateAndStoreMonthlyReputation = new Runnable {
    override def run() {
      val monthlyReputations = monthlyReputation.getKnolderMonthlyReputation
      writeMonthlyReputation.insertMonthlyReputationData(monthlyReputations)
      writeMonthlyReputation.updateMonthlyReputationData(monthlyReputations)
    }
  }
  system.scheduler.scheduleAtFixedRate(timeForAllTimeReputation.seconds, 24.hours)(taskToCalculateAndStoreAllTimeReputation)
  system.scheduler.scheduleAtFixedRate(timeForMonthlyReputation.seconds, 24.hours)(taskToCalculateAndStoreMonthlyReputation)

  /**
   * Calculating and storing quarterly reputation at first day of every month using Akka Quartz Scheduler by providing
   * the required cron expression.
   */
  val quarterlyReputationActorRef = system.actorOf(Props(new QuarterlyReputationActor(quarterlyReputation,
    writeQuarterlyReputation)), "quarterlyReputationActor")
  QuartzSchedulerExtension.get(system).createSchedule("quarterlyReputationScheduler", None,
    "0 0 1 1 1/1 ? *", None, Constant.INDIAN_TIMEZONE)
  QuartzSchedulerExtension.get(system).schedule("quarterlyReputationScheduler", quarterlyReputationActorRef,
    "write quarterly reputation")
}
