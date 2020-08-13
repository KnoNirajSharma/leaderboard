package com.knoldus.leader_board

import java.time.LocalTime

import akka.actor.{ActorSystem, Props}
import com.knoldus.leader_board.application.{ReputationOnAPI, ReputationOnAPIImpl}
import com.knoldus.leader_board.business._
import com.knoldus.leader_board.infrastructure._
import com.knoldus.leader_board.utils.SpreadSheetApi
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
  val allTimeReputation: AllTimeReputation =
    new AllTimeReputationImpl(readBlog, knolderRank, knolderScore, readAllTimeReputation)
  val readMonthlyReputation: ReadMonthlyReputation = new ReadMonthlyReputationImpl(config)
  val writeMonthlyReputation: WriteMonthlyReputation = new WriteMonthlyReputationImpl(config)
  val monthlyReputation: MonthlyReputation =
    new MonthlyReputationImpl(readBlog, knolderRank, knolderScore, readMonthlyReputation)
  val readQuarterlyReputation: ReadQuarterlyReputation = new ReadQuarterlyReputationImpl(config)
  val writeQuarterlyReputation: WriteQuarterlyReputation = new WriteQuarterlyReputationImpl(config)
  val quarterlyReputation: QuarterlyReputation =
    new QuarterlyReputationImpl(readBlog, knolderScore, readQuarterlyReputation)
  val fetchReputation: FetchReputation = new FetchReputationImpl(config)
  val fetchKnolderDetails: FetchKnolderDetails = new FetchKnolderDetailsImpl(config)
  val twelveMonthsContribution: TwelveMonthsContribution = new TwelveMonthsContributionImpl(readBlog)
  val fetchReputationWithCount: FetchCountWithReputation =
    new FetchCountWithReputationImpl(config, fetchReputation)
  val reputationOnAPI: ReputationOnAPI =
    new ReputationOnAPIImpl(twelveMonthsContribution, fetchKnolderDetails, fetchReputationWithCount, config)
  val spreadSheetApiObj = new SpreadSheetApi(config)
  val webinarSpreadSheetData: WebinarSpreadSheetData =
    new WebinarSpreadSheetDataImpl(spreadSheetApiObj, config)
  val storeWebinar = new StoreWebinarImpl(config)
  val fetchBlogs: FetchBlogs = new FetchBlogsImpl(config)
  val fetchKnolx: FetchKnolx = new FetchKnolxImpl(config)
  val fetchTechHub: FetchTechHub = new FetchTechHubImpl(config)
  val storeBlogs: StoreBlogs = new StoreBlogsImpl(config)
  val storeKnolx: StoreKnolx = new StoreKnolxImpl(config)
  val storeTechHub: StoreTechHub = new StoreTechHubImpl(config)
  val storeOSContributionDetails: StoreOSContributionDetails = new StoreOSContributionDetailsImpl(config)
  val URLResponse: URLResponse = new URLResponse
  val techHubData: TechHubData = new TechHubDataImpl(fetchTechHub, URLResponse, config)
  val osContributionDataObj: OSContributionData = new OSContributionDataImpl(spreadSheetApiObj, config)

  val blogs: Blogs = new BlogsImpl(fetchBlogs, URLResponse, config)
  val knolx: Knolxs = new KnolxImpl(fetchKnolx, URLResponse, config)
  val allTimeReputationActorRef = system.actorOf(
    Props(new AllTimeReputationActor(allTimeReputation, writeAllTimeReputation)),
    "allTimeReputationActor"
  )
  val monthlyReputationActorRef = system.actorOf(
    Props(new MonthlyReputationActor(monthlyReputation, writeMonthlyReputation)),
    "monthlyReputationActor"
  )
  val quarterlyReputationActorRef = system.actorOf(
    Props(new QuarterlyReputationActor(quarterlyReputation, writeQuarterlyReputation)),
    "quarterlyReputationActor"
  )
  val blogScriptActorRef = system.actorOf(
    Props(
      new BlogScriptActor(
        allTimeReputationActorRef,
        monthlyReputationActorRef,
        quarterlyReputationActorRef,
        storeBlogs,
        blogs
      )
    ),
    "BlogScriptActor"
  )
  val knolxScriptActorRef = system.actorOf(
    Props(
      new KnolxScriptActor(
        allTimeReputationActorRef,
        monthlyReputationActorRef,
        quarterlyReputationActorRef,
        storeKnolx,
        knolx
      )
    ),
    "KnolxScriptActor"
  )
  val webinarScriptActorRef = system.actorOf(
    Props(
      new WebinarScriptActor(
        allTimeReputationActorRef,
        monthlyReputationActorRef,
        quarterlyReputationActorRef,
        storeWebinar,
        webinarSpreadSheetData
      )
    ),
    "WebinarScriptActor"
  )
  val techHubScriptActorRef = system.actorOf(
    Props(
      new TechHubScriptActor(
        allTimeReputationActorRef,
        monthlyReputationActorRef,
        quarterlyReputationActorRef,
        storeTechHub,
        techHubData
      )
    ),
    "TechHubScriptActor"
  )
  val latestBlogs = blogs.getLatestBlogsFromAPI
  storeBlogs.insertBlog(latestBlogs)
  val latestKnolx = knolx.getLatestKnolxFromAPI
  storeKnolx.insertKnolx(latestKnolx)
  val webinarDetails = webinarSpreadSheetData.getWebinarData
  storeWebinar.insertWebinar(webinarDetails)
  val osContributionDetails = osContributionDataObj.getOSContributionData
  storeOSContributionDetails.insertOSContribution(osContributionDetails)
  val techHubDataList = techHubData.getLatestTechHubTemplates
  storeTechHub.insertTechHub(techHubDataList)
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
  val indiaCurrentTime = IndianTime.currentTime
  val totalSecondsOfDayTillCurrentTime = indiaCurrentTime.toLocalTime.toSecondOfDay
  val startTimeToScriptExecution = LocalTime.of(0, 0, 0, 0).toSecondOfDay
  val secondsInDay = 24 * 60 * 60
  val timeForScriptExecution =
    if (startTimeToScriptExecution - totalSecondsOfDayTillCurrentTime < 0) {
      secondsInDay + startTimeToScriptExecution - totalSecondsOfDayTillCurrentTime
    } else {
      startTimeToScriptExecution - totalSecondsOfDayTillCurrentTime
    }

  /**
   * Fetching latest blogs from Wordpress API and storing in database.
   */
  system.scheduler.scheduleAtFixedRate(
    timeForScriptExecution.seconds,
    24.hours,
    blogScriptActorRef,
    ExecuteBlogsScript
  )

  /**
   * Fetching latest knolx from Knolx API and storing in database.
   */
  QuartzSchedulerExtension
    .get(system)
    .createSchedule("knolxScriptScheduler", None, "0 0 0 ? * 7 *", None, IndianTime.indianTimezone)
  QuartzSchedulerExtension
    .get(system)
    .schedule("knolxScriptScheduler", knolxScriptActorRef, ExecuteKnolxScript)

  /**
   * Fetching latest webinar from webinar API and storing in database.
   */

  QuartzSchedulerExtension
    .get(system)
    .createSchedule("WebinarScriptScheduler", None, "0 0 0 ? * 7 *", None, IndianTime.indianTimezone)
  QuartzSchedulerExtension
    .get(system)
    .schedule("WebinarScriptScheduler", webinarScriptActorRef, ExecuteWebinarScript)

  /**
   * Fetching latest techhub from techhub API and storing in database.
   */

  QuartzSchedulerExtension
    .get(system)
    .createSchedule("TechHubScriptScheduler", None, "0 0 0 ? * 7 *", None, IndianTime.indianTimezone)
  QuartzSchedulerExtension
    .get(system)
    .schedule("TechHubScriptScheduler", techHubScriptActorRef, ExecuteTechHubScript)
}
