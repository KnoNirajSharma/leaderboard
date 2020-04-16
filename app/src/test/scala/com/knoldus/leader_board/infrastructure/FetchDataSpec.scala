package com.knoldus.leader_board.infrastructure

import java.sql.{Connection, PreparedStatement, Timestamp}
import java.time.Instant

import com.knoldus.leader_board.{AuthorScore, DatabaseConnection, GetAuthorScore, GetScore}
import com.typesafe.config.ConfigFactory
import org.scalatest.{BeforeAndAfterEach, DoNotDiscover}

@DoNotDiscover
class FetchDataSpec extends DBSpec with BeforeAndAfterEach {
  val databaseConnection = new DatabaseConnection(ConfigFactory.load())
  implicit val connection: Connection = databaseConnection.connection
  val fetchData = new FetchData(databaseConnection)

  override def afterEach(): Unit = {
    cleanUpDatabase(connection)
  }

  override protected def beforeEach(): Unit = {
    cleanUpDatabase(connection)
    createTable(connection)
  }

  "FetchData" should {

    "return maximum publication date of blog" in {
      val insertBlog1: String =
        """
          |insert into blog(id, wordpress_id, published_on, title)
          |values (?,?,?,?)
""".stripMargin

      val preparedStmt1: PreparedStatement = connection.prepareStatement(insertBlog1)
      preparedStmt1.setInt(1, 1001)
      preparedStmt1.setString(2, "mukesh01")
      preparedStmt1.setTimestamp(3, Timestamp.from(Instant.parse("2020-04-13T14:56:40Z")))
      preparedStmt1.setString(4, "windows handling using selenium webdriver")
      preparedStmt1.execute
      preparedStmt1.close()

      val insertBlog2: String =
        """
          |insert into blog(id, wordpress_id, published_on, title)
          |values (?,?,?,?)
""".stripMargin

      val preparedStmt2: PreparedStatement = connection.prepareStatement(insertBlog2)
      preparedStmt2.setInt(1, 1002)
      preparedStmt2.setString(2, "abhishek02")
      preparedStmt2.setTimestamp(3, Timestamp.from(Instant.parse("2020-04-13T13:10:40Z")))
      preparedStmt2.setString(4, "ChatOps : Make your life easy")
      preparedStmt2.execute
      preparedStmt2.close()

      val insertBlog3: String =
        """
          |insert into blog(id, wordpress_id, published_on, title)
          |values (?,?,?,?)
""".stripMargin

      val preparedStmt3: PreparedStatement = connection.prepareStatement(insertBlog3)
      preparedStmt3.setInt(1, 1003)
      preparedStmt3.setString(2, "komal03")
      preparedStmt3.setTimestamp(3, Timestamp.from(Instant.parse("2020-04-13T12:57:27Z")))
      preparedStmt3.setString(4, "Automating Windows Controls in Selenium")
      preparedStmt3.execute
      preparedStmt3.close()

      val insertBlog4: String =
        """
          |insert into blog(id, wordpress_id, published_on, title)
          |values (?,?,?,?)
""".stripMargin

      val preparedStmt4: PreparedStatement = connection.prepareStatement(insertBlog4)
      preparedStmt4.setInt(1, 1004)
      preparedStmt4.setString(2, "mukesh01")
      preparedStmt4.setTimestamp(3, Timestamp.from(Instant.parse("2020-04-13T12:40:20Z")))
      preparedStmt4.setString(4, "Java 9: Enhance your Jav…ptional API enhancement")
      preparedStmt4.execute
      preparedStmt4.close()
      val result = fetchData.fetchMaxBlogPublicationDate
      result.map { date =>
        date shouldBe Timestamp.from(Instant.parse("2020-04-13T14:56:40Z"))
      }
    }

    "return scores of knolders" in {
      val insertAllTimeData1: String =
        """
          |insert into all_time(id, knolder_id, overall_score, overall_rank)
          |values (?,?,?,?)
""".stripMargin

      val preparedStmt5: PreparedStatement = connection.prepareStatement(insertAllTimeData1)
      preparedStmt5.setInt(1, 1)
      preparedStmt5.setInt(2, 1)
      preparedStmt5.setInt(3, 10)
      preparedStmt5.setInt(4, 0)
      preparedStmt5.execute
      preparedStmt5.close()

      val insertAllTimeData2: String =
        """
          |insert into all_time(id, knolder_id, overall_score, overall_rank)
          |values (?,?,?,?)
""".stripMargin

      val preparedStmt6: PreparedStatement = connection.prepareStatement(insertAllTimeData2)
      preparedStmt6.setInt(1, 2)
      preparedStmt6.setInt(2, 2)
      preparedStmt6.setInt(3, 5)
      preparedStmt6.setInt(4, 0)
      preparedStmt6.execute
      preparedStmt6.close()

      val insertAllTimeData3: String =
        """
          |insert into all_time(id, knolder_id, overall_score, overall_rank)
          |values (?,?,?,?)
""".stripMargin

      val preparedStmt7: PreparedStatement = connection.prepareStatement(insertAllTimeData3)
      preparedStmt7.setInt(1, 3)
      preparedStmt7.setInt(2, 3)
      preparedStmt7.setInt(3, 5)
      preparedStmt7.setInt(4, 0)
      preparedStmt7.execute
      preparedStmt7.close()
      val result = fetchData.fetchScores
      result shouldBe List(GetScore(1, 10), GetScore(3, 5), GetScore(2, 5))
    }

    "return id from knolder table" in {
      createTable(connection)
      val insertKnolder: String =
        """
          |insert into knolder(id, full_name, wordpress_id)
          |values (?,?,?)
""".stripMargin

      val preparedStmt: PreparedStatement = connection.prepareStatement(insertKnolder)
      preparedStmt.setInt(1, 1)
      preparedStmt.setString(2, "Mukesh Kumar")
      preparedStmt.setString(3, "mukesh01")
      preparedStmt.execute
      preparedStmt.close()
      val authorScore = AuthorScore(Option("mukesh01"), Option("Mukesh Kumar"), 10, 0)
      val result = fetchData.fetchKnolderIdFromKnolder(authorScore)
      cleanUpDatabase(connection)
      result.map { id =>
        id shouldBe 1
      }
    }

    "return knolder id from all_time table" in {
      val insertAllTimeData: String =
        """
          |insert into all_time(id, knolder_id, overall_score, overall_rank)
          |values (?,?,?,?)
""".stripMargin

      val preparedStmt: PreparedStatement = connection.prepareStatement(insertAllTimeData)
      preparedStmt.setInt(1, 1)
      preparedStmt.setInt(2, 1)
      preparedStmt.setInt(3, 10)
      preparedStmt.setInt(4, 0)
      preparedStmt.execute
      preparedStmt.close()
      val authorScore = AuthorScore(Option("mukesh01"), Option("Mukesh Kumar"), 10, 0)
      val result = fetchData.fetchKnolderIdFromAllTime(authorScore, Option(1))
      result.map { id =>
        id shouldBe 1
      }
    }

    "return all time data of knolders" in {
      val insertKnolder1: String =
        """
          |insert into knolder(id, full_name, wordpress_id)
          |values (?,?,?)
""".stripMargin

      val preparedStmt1: PreparedStatement = connection.prepareStatement(insertKnolder1)
      preparedStmt1.setInt(1, 1)
      preparedStmt1.setString(2, "Mukesh Kumar")
      preparedStmt1.setString(3, "mukesh01")
      preparedStmt1.execute
      preparedStmt1.close()

      val insertKnolder2: String =
        """
          |insert into knolder(id, full_name, wordpress_id)
          |values (?,?,?)
""".stripMargin

      val preparedStmt2: PreparedStatement = connection.prepareStatement(insertKnolder2)
      preparedStmt2.setInt(1, 2)
      preparedStmt2.setString(2, "Abhishek Baranwal")
      preparedStmt2.setString(3, "abhishek02")
      preparedStmt2.execute
      preparedStmt2.close()

      val insertKnolder3: String =
        """
          |insert into knolder(id, full_name, wordpress_id)
          |values (?,?,?)
""".stripMargin

      val preparedStmt3: PreparedStatement = connection.prepareStatement(insertKnolder3)
      preparedStmt3.setInt(1, 3)
      preparedStmt3.setString(2, "Komal Rajpal")
      preparedStmt3.setString(3, "komal03")
      preparedStmt3.execute
      preparedStmt3.close()

      val insertAllTimeData1: String =
        """
          |insert into all_time(id, knolder_id, overall_score, overall_rank)
          |values (?,?,?,?)
""".stripMargin

      val preparedStmt5: PreparedStatement = connection.prepareStatement(insertAllTimeData1)
      preparedStmt5.setInt(1, 1)
      preparedStmt5.setInt(2, 1)
      preparedStmt5.setInt(3, 10)
      preparedStmt5.setInt(4, 0)
      preparedStmt5.execute
      preparedStmt5.close()

      val insertAllTimeData2: String =
        """
          |insert into all_time(id, knolder_id, overall_score, overall_rank)
          |values (?,?,?,?)
""".stripMargin

      val preparedStmt6: PreparedStatement = connection.prepareStatement(insertAllTimeData2)
      preparedStmt6.setInt(1, 2)
      preparedStmt6.setInt(2, 2)
      preparedStmt6.setInt(3, 5)
      preparedStmt6.setInt(4, 0)
      preparedStmt6.execute
      preparedStmt6.close()

      val insertAllTimeData3: String =
        """
          |insert into all_time(id, knolder_id, overall_score, overall_rank)
          |values (?,?,?,?)
""".stripMargin

      val preparedStmt7: PreparedStatement = connection.prepareStatement(insertAllTimeData3)
      preparedStmt7.setInt(1, 3)
      preparedStmt7.setInt(2, 3)
      preparedStmt7.setInt(3, 5)
      preparedStmt7.setInt(4, 0)
      preparedStmt7.execute
      preparedStmt7.close()
      val listOfAuthorScores = List(GetAuthorScore("Mukesh Kumar", 10, 0),
        GetAuthorScore("Komal Rajpal", 5, 0),
        GetAuthorScore("Abhishek Baranwal", 5, 0))
      val result = fetchData.fetchAllTimeData
      result shouldBe listOfAuthorScores
    }
  }
}
