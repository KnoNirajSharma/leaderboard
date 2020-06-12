package com.knoldus.leader_board.infrastructure

import java.sql.{Connection, PreparedStatement, Timestamp}
import java.time.Instant

import com.knoldus.leader_board._
import com.typesafe.config.ConfigFactory
import org.scalatest.{BeforeAndAfterEach, DoNotDiscover}

@DoNotDiscover
class StoreBlogsImplSpec extends DBSpec with BeforeAndAfterEach {
  implicit val connection: Connection = DatabaseConnection.connection(ConfigFactory.load())
  val storeData: StoreBlogs = new StoreBlogsImpl(ConfigFactory.load())

  override def afterEach(): Unit = {
    cleanUpDatabase(connection)
  }

  override protected def beforeEach(): Unit = {
    cleanUpDatabase(connection)
    createTable(connection)
  }

  "Store Blogs" should {

    "return number of rows affected when insertion in blog table" in {
      val insertKnolder1: String =
        """
          |insert into knolder(id, full_name, wordpress_id, email_id)
          |values (?,?,?,?)
""".stripMargin

      val preparedStmt1: PreparedStatement = connection.prepareStatement(insertKnolder1)
      preparedStmt1.setInt(1, 1)
      preparedStmt1.setString(2, "Mukesh Kumar")
      preparedStmt1.setString(3, "mukesh01")
      preparedStmt1.setString(4, "mukesh.kumar@knoldus.com")
      preparedStmt1.execute
      preparedStmt1.close()

      val insertKnolder2: String =
        """
          |insert into knolder(id, full_name, wordpress_id, email_id)
          |values (?,?,?,?)
""".stripMargin

      val preparedStmt2: PreparedStatement = connection.prepareStatement(insertKnolder2)
      preparedStmt2.setInt(1, 2)
      preparedStmt2.setString(2, "Abhishek Baranwal")
      preparedStmt2.setString(3, "abhishek02")
      preparedStmt2.setString(4, "abhishek.baranwal@knoldus.com")
      preparedStmt2.execute
      preparedStmt2.close()

      val insertKnolder3: String =
        """
          |insert into knolder(id, full_name, wordpress_id, email_id)
          |values (?,?,?,?)
""".stripMargin

      val preparedStmt3: PreparedStatement = connection.prepareStatement(insertKnolder3)
      preparedStmt3.setInt(1, 3)
      preparedStmt3.setString(2, "Komal Rajpal")
      preparedStmt3.setString(3, "komal03")
      preparedStmt3.setString(4, "komal.rajpal@knoldus.com")
      preparedStmt3.execute
      preparedStmt3.close()

      val blogs = List(Blog(Option(1001), Option("mukesh01"),
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

      val result = storeData.insertBlog(blogs)
      result.sum shouldBe 4
    }
  }
}
