package com.knoldus.leader_board.infrastructure

import java.sql.{Connection, PreparedStatement, Timestamp}
import java.time.Instant

import com.knoldus.leader_board.DatabaseConnection
import com.typesafe.config.ConfigFactory
import org.scalatest.{BeforeAndAfterEach, DoNotDiscover}

@DoNotDiscover
class FetchDataImplSpec extends DBSpec with BeforeAndAfterEach {
  implicit val connection: Connection = DatabaseConnection.connection(ConfigFactory.load())
  val fetchData: FetchData = new FetchDataImpl(ConfigFactory.load())

  override def afterEach(): Unit = {
    cleanUpDatabase(connection)
  }

  override protected def beforeEach(): Unit = {
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
  }
}
