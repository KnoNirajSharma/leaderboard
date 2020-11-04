package com.knoldus.leader_board

import java.time.LocalTime

import akka.actor.{ActorSystem, Props}
import com.knoldus.leader_board.application.{ReputationOnAPI, ReputationOnAPIImpl}
import com.knoldus.leader_board.business._
import com.knoldus.leader_board.infrastructure._
import com.knoldus.leader_board.utils.{SpreadSheetApi, SpreadSheetApiImpl}
import com.typesafe.akka.extension.quartz.QuartzSchedulerExtension
import com.typesafe.config.{Config, ConfigFactory}

import scala.concurrent.ExecutionContextExecutor
import scala.concurrent.duration._

object DriverApp extends App {
  implicit val system: ActorSystem = ActorSystem()
  implicit val executionContext: ExecutionContextExecutor = system.dispatcher
  val config: Config = ConfigFactory.load()
  val dateTimeFormat = new ParseDateTimeFormats
  val contributionScoreMultiplierAndSpikeMonth: ContributionScoreMultiplierAndSpikeMonth = new ContributionScoreMultiplierAndSpikeMonthImpl(config)
  val knolderScore: KnolderScore = new KnolderScoreImpl(contributionScoreMultiplierAndSpikeMonth,config)
  val knolderRank: KnolderRank = new KnolderRankImpl
  val readContribution = new ReadContributionImpl(config)
  val readAllTimeReputation: ReadAllTimeReputation = new ReadAllTimeReputationImpl(config)
  val writeAllTimeReputation: WriteAllTimeReputation = new WriteAllTimeReputationImpl(config)
  val allTimeReputation: AllTimeReputation =
    new AllTimeReputationImpl(readContribution, knolderRank, knolderScore, readAllTimeReputation)
  val readMonthlyReputation: ReadMonthlyReputation = new ReadMonthlyReputationImpl(config)
  val writeMonthlyReputation: WriteMonthlyReputation = new WriteMonthlyReputationImpl(config)
  val monthlyReputation: MonthlyReputation =
    new MonthlyReputationImpl(readContribution, knolderRank, knolderScore, readMonthlyReputation)
  val readQuarterlyReputation: ReadQuarterlyReputation = new ReadQuarterlyReputationImpl(config)
  val writeQuarterlyReputation: WriteQuarterlyReputation = new WriteQuarterlyReputationImpl(config)
  val quarterlyReputation: QuarterlyReputation =
    new QuarterlyReputationImpl(readContribution, knolderScore, readQuarterlyReputation)
  val fetchReputation: FetchReputation = new FetchReputationImpl(config)
  val fetchKnolderDetails: FetchKnolderContributionDetails = new FetchKnolderContributionDetailsImpl(config)
  val twelveMonthsContribution: TwelveMonthsContribution = new TwelveMonthsContributionImpl(readContribution)
  val fetchReputationWithCount: FetchCountWithReputation =
    new FetchCountWithReputationImpl(config, fetchReputation)
  val fetchMonthlyTopFiveLeaders: FetchMonthlyTopFiveLeaders = new FetchMonthlyTopFiveLeadersImpl(config)
  val readSpikeMonthAndScoreMultiplier: ReadSpikeMonthAndScoreMultiplier = new ReadSpikeMonthAndScoreMultiplierImpl(config,
    contributionScoreMultiplierAndSpikeMonth)
  val reputationOnAPI: ReputationOnAPI =
    new ReputationOnAPIImpl(readSpikeMonthAndScoreMultiplier, fetchMonthlyTopFiveLeaders,
      twelveMonthsContribution, fetchKnolderDetails, fetchReputationWithCount, config)
  val spreadSheetApiObj: SpreadSheetApi = new SpreadSheetApiImpl(config)
  val webinarSpreadSheetData: WebinarSpreadSheetData =
    new WebinarSpreadSheetDataImpl(dateTimeFormat, spreadSheetApiObj, config)
  val storeWebinar = new StoreWebinarImpl(config)
  val fetchBlogs: FetchBlogs = new FetchBlogsImpl(config)
  val fetchKnolx: FetchKnolx = new FetchKnolxImpl(config)
  val fetchTechHub: FetchTechHub = new FetchTechHubImpl(config)
  val storeBlogs: StoreBlogs = new StoreBlogsImpl(config)
  val storeKnolx: StoreKnolx = new StoreKnolxImpl(config)
  val storeTechHub: StoreTechHub = new StoreTechHubImpl(config)
  val storeOSContributionDetails: StoreOSContributionDetails = new StoreOSContributionDetailsImpl(config)
  val storeConferenceDetails: StoreConferenceDetails = new StoreConferenceDetailsImpl(config)
  val storeBooksContribution: StoreBooksContribution = new StoreBooksContributionImpl(config)
  val storeMeetupContribution: StoreMeetupContribution = new StoreMeetupContributionImpl(config)
  val storeResearchPapersContribution: StoreResearchPapersContribution = new StoreResearchPapersContributionImpl(config)
  val fetchAllTimeKnoldersReputation: FetchAllTimeReputation = new FetchAllTimeReputationImpl(config)
  val monthlyLeadersObj: MonthlyLeaders = new MonthlyLeadersImpl(readContribution, fetchAllTimeKnoldersReputation, knolderScore
    , knolderRank)
  val storeTopFiveLeaders: StoreTopFiveLeaders = new StoreTopFiveLeadersImpl(config, monthlyLeadersObj)
  val URLResponse: URLResponse = new URLResponse
  val techHubData: TechHubData = new TechHubDataImpl(fetchTechHub, URLResponse, config)
  val otherContributionDataObj: OtherContributionData =
    new OtherContributionDataImpl(dateTimeFormat, spreadSheetApiObj, config)
  val fetchMonthlyKnolderContribution: FetchMonthlyKnolderContribution = new FetchMonthlyKnolderContributionImpl(config)
  val knolderMonthlyContribution: KnolderMonthlyContribution = new KnolderMonthlyContributionImpl(readContribution, knolderScore,
    fetchMonthlyKnolderContribution)
  val writeMonthlyContribution: WriteMonthlyContribution = new WriteMonthlyContributionImpl(config)

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
        blogs, knolderMonthlyContribution, writeMonthlyContribution
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
        knolx, knolderMonthlyContribution, writeMonthlyContribution
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
        webinarSpreadSheetData, knolderMonthlyContribution, writeMonthlyContribution
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
        techHubData, knolderMonthlyContribution, writeMonthlyContribution
      )
    ),
    "TechHubScriptActor"
  )
  val otherContributionScriptActorRef = system.actorOf(
    Props(
      new OtherContributionActor(
        allTimeReputationActorRef,
        monthlyReputationActorRef,
        quarterlyReputationActorRef,
        storeOSContributionDetails,
        storeConferenceDetails,
        storeBooksContribution,
        storeResearchPapersContribution,
        otherContributionDataObj, knolderMonthlyContribution,writeMonthlyContribution
      )
    ),
    "OtherContributionScriptActor"
  )


  val monthlyLeadersActorRef = system.actorOf(
    Props(
      new MonthlyLeadersActor(storeTopFiveLeaders)
    ),
    "MonthlyLeaderActor"
  )

  val latestBlogs = blogs.getLatestBlogsFromAPI
  storeBlogs.insertBlog(latestBlogs)
  val latestKnolx = knolx.getLatestKnolxFromAPI
  storeKnolx.insertKnolx(latestKnolx)
  val webinarDetails = webinarSpreadSheetData.getWebinarData
  storeWebinar.insertWebinar(webinarDetails)
  val otherContributionDetails = otherContributionDataObj.getOtherContributionData
  storeOSContributionDetails.insertOSContribution(otherContributionDetails)
  storeConferenceDetails.insertConferenceDetails(otherContributionDetails)
  storeBooksContribution.insertBooksContributionDetails(otherContributionDetails)
  storeMeetupContribution.insertMeetupContributionDetails(otherContributionDetails)
  storeResearchPapersContribution.insertResearchPaperContributionDetails(otherContributionDetails)
  val techHubDataList = techHubData.getLatestTechHubTemplates
  storeTechHub.insertTechHub(techHubDataList)
  val month = IndianTime.currentTime.getMonth.toString
  val year = IndianTime.currentTime.getYear
  val knolderMonthlyContributionDetails = knolderMonthlyContribution.getKnolderMonthlyContribution(month, year)
  writeMonthlyContribution.insertKnolderMonthlyContribution(knolderMonthlyContributionDetails)
  writeMonthlyContribution.updateKnolderMonthlyContribution(knolderMonthlyContributionDetails)
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
   * Fetching latest blogs from Wordpress API and stored in blog table.
   */
  system.scheduler.scheduleAtFixedRate(
    timeForScriptExecution.seconds,
    24.hours,
    blogScriptActorRef,
    ExecuteBlogsScript
  )

  /**
   * Fetching latest other contribution from other contribution sheet and stored in other contribution table.
   */
  system.scheduler.scheduleAtFixedRate(
    timeForScriptExecution.seconds,
    24.hours,
    otherContributionScriptActorRef,
    ExecuteOtherContributionScript
  )

  /**
   * Fetching latest knolx from Knolx API and stored in knolx table.
   */
  QuartzSchedulerExtension
    .get(system)
    .createSchedule("knolxScriptScheduler", None, "0 0 0 ? * 7 *", None, IndianTime.indianTimezone)
  QuartzSchedulerExtension
    .get(system)
    .schedule("knolxScriptScheduler", knolxScriptActorRef, ExecuteKnolxScript)

  /**
   * Fetching latest webinar from webinar API and stored in webinar table.
   */
  QuartzSchedulerExtension
    .get(system)
    .createSchedule("WebinarScriptScheduler", None, "0 0 0 ? * 7 *", None, IndianTime.indianTimezone)
  QuartzSchedulerExtension
    .get(system)
    .schedule("WebinarScriptScheduler", webinarScriptActorRef, ExecuteWebinarScript)

  /**
   * Fetching latest techhub from techhub API and stored in techhub table.
   */
  QuartzSchedulerExtension
    .get(system)
    .createSchedule("TechHubScriptScheduler", None, "0 0 0 ? * 7 *", None, IndianTime.indianTimezone)
  QuartzSchedulerExtension
    .get(system)
    .schedule("TechHubScriptScheduler", techHubScriptActorRef, ExecuteTechHubScript)

  QuartzSchedulerExtension
    .get(system)
    .createSchedule("HallOfFameScheduler", None, "0 0 1 2 * ? *", None, IndianTime.indianTimezone)
  QuartzSchedulerExtension
    .get(system)
    .schedule("HallOfFameScheduler", monthlyLeadersActorRef, StoreMonthlyLeaders)
}
