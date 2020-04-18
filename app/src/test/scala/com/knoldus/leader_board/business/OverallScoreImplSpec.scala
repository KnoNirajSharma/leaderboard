package com.knoldus.leader_board.business

import java.sql.Timestamp
import java.time.Instant

import com.knoldus.leader_board._
import com.knoldus.leader_board.infrastructure.{FetchDataImpl, StoreDataImpl, UpdateDataImpl}
import org.mockito.MockitoSugar
import org.scalatest.flatspec.AnyFlatSpec

class OverallScoreImplSpec extends AnyFlatSpec with MockitoSugar {
  val listOfBlogs = List(
    Blog(Option(1001), Option("mukesh01"),
      Timestamp.from(Instant.parse("2020-04-13T14:56:40Z")),
      Option("windows handling using selenium webdriver")),
    Blog(Option(1002), Option("abhishek02"),
      Timestamp.from(Instant.parse("2020-04-13T13:10:40Z")),
      Option("ChatOps : Make your life easy")),
    Blog(Option(1003), Option("komal03"),
      Timestamp.from(Instant.parse("2020-04-13T12:57:27Z")),
      Option("Automating Windows Controls in Selenium")),
    Blog(Option(1004), Option("mukesh01"),
      Timestamp.from(Instant.parse("2020-04-13T12:40:20Z")),
      Option("Java 9: Enhance your Jav…ptional API enhancement")))

  val listOfAuthors = List(
    Author(Option("Mukesh Kumar"), Option("mukesh01")),
    Author(Option("Abhishek Baranwal"), Option("abhishek02")),
    Author(Option("Komal Rajpal"), Option("komal03"))
  )

  val blogAuthor: BlogAuthor = BlogAuthor(listOfBlogs, listOfAuthors)

  val listOfAuthorScores = List(AuthorScore(Option("mukesh01"), Option("Mukesh Kumar"), 10, 0),
    AuthorScore(Option("abhishek02"), Option("Abhishek Baranwal"), 5, 0),
    AuthorScore(Option("komal03"), Option("Komal Rajpal"), 5, 0))

  val mockFetchData: FetchDataImpl = mock[FetchDataImpl]
  val mockStoreData: StoreDataImpl = mock[StoreDataImpl]
  val mockUpdateData: UpdateDataImpl = mock[UpdateDataImpl]

  val overallScore: OverallScore = new OverallScoreImpl(mockFetchData, mockStoreData, mockUpdateData)

  "calculate score" should "calculate overall score of each knolder" in {
    val actualResult = overallScore.calculateScore(blogAuthor)
    val expectedResult = listOfAuthorScores
    assert(actualResult == expectedResult)
  }

  val authorScore: AuthorScore = AuthorScore(Option("mukesh01"), Option("Mukesh Kumar"), 15, 0)
  val listOfAuthorScore: List[AuthorScore] = List(AuthorScore(Option("mukesh01"), Option("Mukesh Kumar"), 15, 0))

  "manage scores" should "update scores in all_time table" in {
    when(mockFetchData.fetchKnolderIdFromKnolder(authorScore))
      .thenReturn(Option(1))
    when(mockFetchData.fetchKnolderIdFromAllTime(authorScore, Option(1)))
      .thenReturn(Option(1))
    when(mockUpdateData.updateAllTimeData(authorScore, Option(1)))
      .thenReturn(1)
    assert(overallScore.manageScores(listOfAuthorScore).sum == 1)
  }

  "manage scores" should "insert scores in all_time table" in {
    when(mockFetchData.fetchKnolderIdFromKnolder(authorScore))
      .thenReturn(Option(1))
    when(mockFetchData.fetchKnolderIdFromAllTime(authorScore, Option(1)))
      .thenReturn(None)
    when(mockStoreData.insertAllTimeData(authorScore, Option(1)))
      .thenReturn(1)
    assert(overallScore.manageScores(listOfAuthorScore).sum == 1)
  }
}
