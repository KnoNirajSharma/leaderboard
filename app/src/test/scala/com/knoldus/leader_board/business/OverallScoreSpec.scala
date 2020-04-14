package com.knoldus.leader_board.business

import java.sql.Timestamp
import java.time.Instant

import com.knoldus.leader_board._
import com.knoldus.leader_board.infrastructure.{FetchData, StoreData, UpdateData}
import org.scalatest.flatspec.AnyFlatSpec

class OverallScoreSpec extends AnyFlatSpec {
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

  val overallScore = new OverallScore(fetchData = new FetchData(new DatabaseConnection),
    new StoreData(new DatabaseConnection), new UpdateData(new DatabaseConnection))

  "calculate score" should "calculate overall score of each knolder" in {
    val actualResult = overallScore.calculateScore(blogAuthor)
    val expectedResult = listOfAuthorScores
    assert(actualResult == expectedResult)
  }
}
