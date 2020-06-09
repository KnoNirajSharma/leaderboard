package com.knoldus.leader_board

import java.time.LocalTime

import akka.actor.{ActorSystem, Props}
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
  val fetchBlogs: FetchBlogs = new FetchBlogsImpl(config)
  val storeBlogs: StoreBlogs = new StoreBlogsImpl(config)
  val URLResponse: URLResponse = new URLResponse
  val blogs: Blogs = new BlogsImpl(fetchBlogs, URLResponse, config)

  val allTimeReputationActorRef = system.actorOf(Props(new AllTimeReputationActor(allTimeReputation,
    writeAllTimeReputation)), "allTimeReputationActor")
  val monthlyReputationActorRef = system.actorOf(Props(new MonthlyReputationActor(monthlyReputation,
    writeMonthlyReputation)), "monthlyReputationActor")
  val quarterlyReputationActorRef = system.actorOf(Props(new QuarterlyReputationActor(quarterlyReputation,
    writeQuarterlyReputation)), "quarterlyReputationActor")
  val scriptActorRef = system.actorOf(Props(new ScriptActor(allTimeReputationActorRef, monthlyReputationActorRef,
    quarterlyReputationActorRef, storeBlogs, blogs)), "ScriptActor")

  val latestBlogs = blogs.getLatestBlogsFromAPI
  storeBlogs.insertBlog(latestBlogs)
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
  val startTimeToScriptExecution = LocalTime.of(1, 0, 0, 0).toSecondOfDay
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
  system.scheduler.scheduleAtFixedRate(timeForBlogScriptExecution.seconds, 24.hours, scriptActorRef,
    "execute blogs script")
}
