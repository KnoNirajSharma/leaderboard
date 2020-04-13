package com.knoldus.leader_board

import com.knoldus.leader_board.display_all_time_data.{AllTimeData, AllTimeDataOnAPI}
import com.knoldus.leader_board.store_blogs.{BlogsAndAuthors, BlogsDataFromAPI, StoreBlogsAndKnolders}
import com.knoldus.leader_board.store_rank.{OverallRank, ScoresOfKnolders, UpdateRankOfKnolders}
import com.knoldus.leader_board.store_scores.{OverallScore, StoreScoresOfKnolders}

import scala.concurrent.Await
import scala.concurrent.duration._

object DriverApp extends App {
  val databaseConnection = new DatabaseConnection
  val blogsDataFromAPI = new BlogsDataFromAPI(databaseConnection)
  val blogsAndAuthors = new BlogsAndAuthors(blogsDataFromAPI, databaseConnection)
  val storeBlogsAndKnolders = new StoreBlogsAndKnolders(databaseConnection)
  val overallScore = new OverallScore
  val storeScoresOfKnolders = new StoreScoresOfKnolders(databaseConnection)
  val scoresOfKnolders = new ScoresOfKnolders(databaseConnection)
  val overallRank = new OverallRank
  val updateRankOfKnolders = new UpdateRankOfKnolders(databaseConnection)
  val allTimeData = new AllTimeData(databaseConnection)
  val allTimeDataOnAPI = new AllTimeDataOnAPI

  val getBlogsAndAuthors = blogsAndAuthors.getAllBlogsAndAuthors

  val listOfBlogsAndAuthors = Await.result(getBlogsAndAuthors, 3.minutes)

  val storedBlogsAndKnolders = storeBlogsAndKnolders.storeBlogsAndKnolders(listOfBlogsAndAuthors)

  val calculatedScores = overallScore.calculateScore(listOfBlogsAndAuthors)

  val storedScores = storeScoresOfKnolders.storeScores(calculatedScores)

  val fetchedScores = scoresOfKnolders.fetchScores

  val calculatedRank = overallRank.calculateRank(fetchedScores)

  val updatedRank = updateRankOfKnolders.updateRank(calculatedRank)

  val fetchedAllTimeData = allTimeData.queryAllTimeData

  allTimeDataOnAPI.pushAllTimeDataOnAPI(fetchedAllTimeData)
}
