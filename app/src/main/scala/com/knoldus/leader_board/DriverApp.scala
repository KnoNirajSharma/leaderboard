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
  implicit val executionContext: ExecutionContextExecutor = system.dispatcher
  val config: Config = ConfigFactory.load()
  val knolderScore: KnolderScore = new KnolderScoreImpl(config)
  val knolderRank: KnolderRank = new KnolderRankImpl
  val readBlog = new ReadContributionImpl(config)
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
  val fetchBlogs: FetchBlogs = new FetchBlogsImpl(config)
  val fetchKnolx: FetchKnolx = new FetchKnolxImpl(config)
  val storeBlogs: StoreBlogs = new StoreBlogsImpl(config)
  val storeKnolx: StoreKnolx = new StoreKnolxImpl(config)
  val URLResponse: URLResponse = new URLResponse
  val blogs: Blogs = new BlogsImpl(fetchBlogs, URLResponse, config)
  val knolx: Knolxs = new KnolxImpl(fetchKnolx, URLResponse, config)
  val allTimeReputationActorRef = system.actorOf(Props(new AllTimeReputationActor(allTimeReputation,
    writeAllTimeReputation)), "allTimeReputationActor")
  val monthlyReputationActorRef = system.actorOf(Props(new MonthlyReputationActor(monthlyReputation,
    writeMonthlyReputation)), "monthlyReputationActor")
  val quarterlyReputationActorRef = system.actorOf(Props(new QuarterlyReputationActor(quarterlyReputation,
    writeQuarterlyReputation)), "quarterlyReputationActor")
  val blogScriptActorRef = system.actorOf(Props(new BlogScriptActor(allTimeReputationActorRef,
    monthlyReputationActorRef, quarterlyReputationActorRef, storeBlogs, blogs)), "BlogScriptActor")
  val knolxScriptActorRef = system.actorOf(Props(new KnolxScriptActor(allTimeReputationActorRef,
    monthlyReputationActorRef, quarterlyReputationActorRef, storeKnolx, knolx)), "KnolxScriptActor")
  val latestBlogs = blogs.getLatestBlogsFromAPI
  storeBlogs.insertBlog(latestBlogs)
  val latestKnolx = knolx.getLatestKnolxFromAPI
  storeKnolx.insertKnolx(latestKnolx)
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
  val startTimeToScriptExecution = LocalTime.of(0, 0, 0, 0).toSecondOfDay
  val secondsInDay = 24 * 60 * 60
  val timeForBlogScriptExecution =
    if (startTimeToScriptExecution - totalSecondsOfDayTillCurrentTime < 0) {
      secondsInDay + startTimeToScriptExecution - totalSecondsOfDayTillCurrentTime
    } else {
      startTimeToScriptExecution - totalSecondsOfDayTillCurrentTime
    }
  /**
   * Fetching latest blogs from Wordpress API and storing in database.
   */
  system.scheduler.scheduleAtFixedRate(timeForBlogScriptExecution.seconds, 24.hours, blogScriptActorRef,
    ExecuteBlogsScript)
  /**
   * Fetching latest knolx from Knolx API and storing in database.
   */
  QuartzSchedulerExtension.get(system).createSchedule("knolxScriptScheduler", None,
    "0 0 0 ? * 6 *", None, Constant.INDIAN_TIMEZONE)
  QuartzSchedulerExtension.get(system).schedule("knolxScriptScheduler", knolxScriptActorRef,
    ExecuteKnolxScript)
}
