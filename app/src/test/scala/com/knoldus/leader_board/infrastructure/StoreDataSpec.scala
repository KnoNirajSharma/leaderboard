package com.knoldus.leader_board.infrastructure

import java.sql.{Connection, Timestamp}
import java.time.Instant

import com.knoldus.leader_board._
import com.typesafe.config.ConfigFactory
import org.scalatest.{BeforeAndAfterEach, DoNotDiscover}

@DoNotDiscover
class StoreDataSpec extends DBSpec with BeforeAndAfterEach {
  val databaseConnection = new DatabaseConnection(ConfigFactory.load())
  implicit val connection: Connection = databaseConnection.connection
  val storeData = new StoreData(databaseConnection)

  override def afterEach(): Unit = {
    cleanUpDatabase(connection)
  }

  override protected def beforeEach(): Unit = {
    cleanUpDatabase(connection)
    createTable(connection)
  }

  "StoreData" should {

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

    "return number of rows affected when insertion in knolder" in {
      val result = storeData.insertKnolder(blogAuthor)
      result.sum shouldBe 3
    }

    "return number of rows affected when insertion in blog" in {
      val result = storeData.insertBlog(blogAuthor)
      result.sum shouldBe 4
    }

    "return number of rows affected when insertion in all_time" in {
      val result = storeData.insertAllTimeData(AuthorScore(Option("mukesh01"), Option("Mukesh Kumar"), 10, 0),
        Option(1))
      result shouldBe 1
    }
  }
}
