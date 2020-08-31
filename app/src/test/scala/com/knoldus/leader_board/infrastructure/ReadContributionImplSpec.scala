package com.knoldus.leader_board.infrastructure

import java.sql.{Connection, PreparedStatement, Timestamp}
import java.time.Instant

import com.knoldus.leader_board.{DatabaseConnection, GetContributionCount, IndianTime}
import com.typesafe.config.ConfigFactory
import org.scalatest.{BeforeAndAfterEach, DoNotDiscover}

@DoNotDiscover
class ReadContributionImplSpec extends DBSpec with BeforeAndAfterEach {
  implicit val connection: Connection = DatabaseConnection.connection(ConfigFactory.load())
  val readContribution: ReadContribution = new ReadContributionImpl(ConfigFactory.load())

  override def afterEach(): Unit = {
    cleanUpDatabase(connection)
  }

  override def beforeEach(): Unit = {
    createTable(connection)
  }

  "read contribution" should {
    val currentMonth = Timestamp.valueOf(IndianTime.currentTime
      .withDayOfMonth(1).toLocalDate.atStartOfDay())
    val firstMonth = Timestamp.valueOf(IndianTime.currentTime
      .withDayOfMonth(1).toLocalDate.minusMonths(3).atStartOfDay())
    val secondMonth = Timestamp.valueOf(IndianTime.currentTime
      .withDayOfMonth(1).toLocalDate.minusMonths(2).atStartOfDay())
    val thirdMonth = Timestamp.valueOf(IndianTime.currentTime
      .withDayOfMonth(1).toLocalDate.minusMonths(1).atStartOfDay())

    val date = Timestamp.valueOf("2020-06-01 21:32:37")

    def insertKnolder {
      val insertKnolderOne: String =
        """
          |insert into knolder(id, full_name, wordpress_id, email_id, active_status)
          |values (?,?,?,?,?)
""".stripMargin

      val preparedStmtOne: PreparedStatement = connection.prepareStatement(insertKnolderOne)
      preparedStmtOne.setInt(1, 1)
      preparedStmtOne.setString(2, "Mukesh Kumar")
      preparedStmtOne.setString(3, "mukesh01")
      preparedStmtOne.setString(4, "mukesh.kumar@knoldus.com")
      preparedStmtOne.setBoolean(5, true)
      preparedStmtOne.execute
      preparedStmtOne.close()

      val insertKnolderTwo: String =
        """
          |insert into knolder(id, full_name, wordpress_id, email_id, active_status)
          |values (?,?,?,?,?)
""".stripMargin

      val preparedStmtTwo: PreparedStatement = connection.prepareStatement(insertKnolderTwo)
      preparedStmtTwo.setInt(1, 2)
      preparedStmtTwo.setString(2, "Abhishek Baranwal")
      preparedStmtTwo.setString(3, "abhishek02")
      preparedStmtTwo.setString(4, "abhishek.baranwal@knoldus.com")
      preparedStmtTwo.setBoolean(5, true)
      preparedStmtTwo.execute
      preparedStmtTwo.close()

      val insertKnolderThree: String =
        """
          |insert into knolder(id, full_name, wordpress_id, email_id, active_status)
          |values (?,?,?,?,?)
""".stripMargin

      val preparedStmtThree: PreparedStatement = connection.prepareStatement(insertKnolderThree)
      preparedStmtThree.setInt(1, 3)
      preparedStmtThree.setString(2, "Komal Rajpal")
      preparedStmtThree.setString(3, "komal03")
      preparedStmtThree.setString(4, "komal.rajpal@knoldus.com")
      preparedStmtThree.setBoolean(5, true)
      preparedStmtThree.execute
      preparedStmtThree.close()
    }

    def insertBlogs(dateColumnValue: Timestamp) {
      val insertBlogOne: String =
        """|insert into blog(id, wordpress_id, published_on, title)
           |values (?,?,?,?)
""".stripMargin
      val preparedStmtOne: PreparedStatement = connection.prepareStatement(insertBlogOne)
      preparedStmtOne.setInt(1, 1001)
      preparedStmtOne.setString(2, "mukesh01")
      preparedStmtOne.setTimestamp(3, dateColumnValue)
      preparedStmtOne.setString(4, "windows handling using selenium webdriver")
      preparedStmtOne.execute
      preparedStmtOne.close()
      val insertBlogTwo: String =
        """
          |insert into blog(id, wordpress_id, published_on, title)
          |values (?,?,?,?)
""".stripMargin
      val preparedStmtTwo: PreparedStatement = connection.prepareStatement(insertBlogTwo)
      preparedStmtTwo.setInt(1, 1002)
      preparedStmtTwo.setString(2, "abhishek02")
      preparedStmtTwo.setTimestamp(3, dateColumnValue)
      preparedStmtTwo.setString(4, "ChatOps : Make your life easy")
      preparedStmtTwo.execute
      preparedStmtTwo.close()

      val insertBlogThree: String =
        """|insert into blog(id, wordpress_id, published_on, title)
           |values (?,?,?,?)
""".stripMargin
      val preparedStmtThree: PreparedStatement = connection.prepareStatement(insertBlogThree)
      preparedStmtThree.setInt(1, 1003)
      preparedStmtThree.setString(2, "komal03")
      preparedStmtThree.setTimestamp(3, dateColumnValue)
      preparedStmtThree.setString(4, "Automating Windows Controls in Selenium")
      preparedStmtThree.execute
      preparedStmtThree.close()
      val insertBlogFour: String =
        """
          |insert into blog(id, wordpress_id, published_on, title)
          |values (?,?,?,?)
""".stripMargin

      val preparedStmtFour: PreparedStatement = connection.prepareStatement(insertBlogFour)
      preparedStmtFour.setInt(1, 1004)
      preparedStmtFour.setString(2, "mukesh01")
      preparedStmtFour.setTimestamp(3, dateColumnValue)
      preparedStmtFour.setString(4, "Java 9: Enhance your Jav…ptional API enhancement")
      preparedStmtFour.execute
      preparedStmtFour.close()
    }

    def insertKnolx(dateColumnValue: Timestamp) {
      val insertKnolxOne: String =
        """
          |insert into knolx(id, email_id, delivered_on, title)
          |values (?,?,?,?)
""".stripMargin

      val preparedStmtOne: PreparedStatement = connection.prepareStatement(insertKnolxOne)
      preparedStmtOne.setInt(1, 1)
      preparedStmtOne.setString(2, "mukesh.kumar@knoldus.com")
      preparedStmtOne.setTimestamp(3, dateColumnValue)
      preparedStmtOne.setString(4, "Reactive Microservices")
      preparedStmtOne.execute
      preparedStmtOne.close()

      val insertKnolxTwo: String =
        """
          |insert into knolx(id, email_id, delivered_on, title)
          |values (?,?,?,?)
""".stripMargin

      val preparedStmtTwo: PreparedStatement = connection.prepareStatement(insertKnolxTwo)
      preparedStmtTwo.setInt(1, 4)
      preparedStmtTwo.setString(2, "mukesh.kumar@knoldus.com")
      preparedStmtTwo.setTimestamp(3, dateColumnValue)
      preparedStmtTwo.setString(4, "Delta Lake")
      preparedStmtTwo.execute
      preparedStmtTwo.close()
    }

    def insertWebinar(dateColumnValue: Timestamp): Unit = {
      val insertWebinarOne: String =
        """
          |insert into webinar(id, email_id, delivered_on, title)
          |values (?,?,?,?)
""".stripMargin

      val preparedStmtOne: PreparedStatement = connection.prepareStatement(insertWebinarOne)
      preparedStmtOne.setString(1, "1")
      preparedStmtOne.setString(2, "mukesh.kumar@knoldus.com")
      preparedStmtOne.setTimestamp(3, dateColumnValue)
      preparedStmtOne.setString(4, "Reactive Microservices")
      preparedStmtOne.execute
      preparedStmtOne.close()

      val insertWebinarTwo: String =
        """
          |insert into Webinar(id, email_id, delivered_on, title)
          |values (?,?,?,?)
""".stripMargin

      val preparedStmtTwo: PreparedStatement = connection.prepareStatement(insertWebinarTwo)
      preparedStmtTwo.setString(1, "4")
      preparedStmtTwo.setString(2, "mukesh.kumar@knoldus.com")
      preparedStmtTwo.setTimestamp(3, dateColumnValue)
      preparedStmtTwo.setString(4, "Delta Lake")
      preparedStmtTwo.execute
      preparedStmtTwo.close()
    }

    def insertTechHub(dateColumnValue: Timestamp): Unit = {
      val insertTechhubOne: String =
        """
          |insert into techhub(id, email_id, uploaded_on, title)
          |values (?,?,?,?)
""".stripMargin

      val preparedStmtOne: PreparedStatement = connection.prepareStatement(insertTechhubOne)
      preparedStmtOne.setString(1, "1")
      preparedStmtOne.setString(2, "mukesh.kumar@knoldus.com")
      preparedStmtOne.setTimestamp(3, dateColumnValue)
      preparedStmtOne.setString(4, "Reactive Microservices")
      preparedStmtOne.execute
      preparedStmtOne.close()

      val insertTechhubTwo: String =
        """
          |insert into techhub(id, email_id, uploaded_on, title)
          |values (?,?,?,?)
""".stripMargin

      val preparedStmtTwo: PreparedStatement = connection.prepareStatement(insertTechhubTwo)
      preparedStmtTwo.setString(1, "4")
      preparedStmtTwo.setString(2, "mukesh.kumar@knoldus.com")
      preparedStmtTwo.setTimestamp(3, dateColumnValue)
      preparedStmtTwo.setString(4, "Delta Lake")
      preparedStmtTwo.execute
      preparedStmtTwo.close()
    }

    def insertConference(dateColumnValue: Timestamp): Unit = {
      val insertConferenceOne: String =
        """
          |insert into conference(id, email_id, delivered_on, title)
          |values (?,?,?,?)
""".stripMargin

      val preparedStmtOne: PreparedStatement = connection.prepareStatement(insertConferenceOne)
      preparedStmtOne.setString(1, "1")
      preparedStmtOne.setString(2, "mukesh.kumar@knoldus.com")
      preparedStmtOne.setTimestamp(3, dateColumnValue)
      preparedStmtOne.setString(4, "Reactive Microservices")
      preparedStmtOne.execute
      preparedStmtOne.close()

      val insertConferenceTwo: String =
        """
          |insert into conference(id, email_id, delivered_on, title)
          |values (?,?,?,?)
""".stripMargin

      val preparedStmtTwo: PreparedStatement = connection.prepareStatement(insertConferenceTwo)
      preparedStmtTwo.setString(1, "4")
      preparedStmtTwo.setString(2, "mukesh.kumar@knoldus.com")
      preparedStmtTwo.setTimestamp(3, dateColumnValue)
      preparedStmtTwo.setString(4, "Delta Lake")
      preparedStmtTwo.execute
      preparedStmtTwo.close()
    }

    "return number of contributions of each knolder" in {
      val date = Timestamp.from(Instant.parse("2020-04-13T12:57:27Z"))
      insertKnolder
      insertBlogs(date)
      insertKnolx(date)
      insertWebinar(date)
      insertTechHub(date)
      insertConference(date)


      val knoldersWithBlogs = List(GetContributionCount(1, "Mukesh Kumar", 2, 2, 2, 2, 0, 2),
        GetContributionCount(3, "Komal Rajpal", 1, 0, 0, 0, 0, 0),
        GetContributionCount(2, "Abhishek Baranwal", 1, 0, 0, 0, 0, 0))

      val result = readContribution.fetchKnoldersWithContributions
      result shouldBe knoldersWithBlogs
    }

    "return number of contributions of each knolder in current month" in {
      insertKnolder
      insertBlogs(currentMonth)
      insertKnolx(currentMonth)
      insertWebinar(currentMonth)
      insertTechHub(currentMonth)
      insertConference(currentMonth)

      val knoldersWithMonthlyBlogs = List(GetContributionCount(1, "Mukesh Kumar", 2, 2, 2, 2, 0, 2),
        GetContributionCount(3, "Komal Rajpal", 1, 0, 0, 0, 0, 0),
        GetContributionCount(2, "Abhishek Baranwal", 1, 0, 0, 0, 0, 0))

      val result = readContribution.fetchKnoldersWithMonthlyContributions
      result shouldBe knoldersWithMonthlyBlogs
    }

    "return number of contributions of each knolder in first month of quarter" in {
      insertKnolder
      insertBlogs(firstMonth)
      insertKnolx(firstMonth)
      insertWebinar(firstMonth)
      insertTechHub(firstMonth)
      insertConference(firstMonth)

      val knoldersWithQuarterlyBlogs = List(GetContributionCount(1, "Mukesh Kumar", 2, 2, 2, 2, 0, 2),
        GetContributionCount(3, "Komal Rajpal", 1, 0, 0, 0, 0, 0),
        GetContributionCount(2, "Abhishek Baranwal", 1, 0, 0, 0, 0, 0))

      val result = readContribution.fetchKnoldersWithQuarterFirstMonthContributions
      result shouldBe knoldersWithQuarterlyBlogs
    }

    "return number of contributiobns of each knolder in second month of quarter" in {
      insertKnolder
      insertBlogs(secondMonth)
      insertKnolx(secondMonth)
      insertWebinar(secondMonth)
      insertTechHub(secondMonth)
      insertConference(secondMonth)

      val knoldersWithQuarterlyBlogs = List(GetContributionCount(1, "Mukesh Kumar", 2, 2, 2, 2, 0, 2),
        GetContributionCount(3, "Komal Rajpal", 1, 0, 0, 0, 0, 0),
        GetContributionCount(2, "Abhishek Baranwal", 1, 0, 0, 0, 0, 0))

      val result = readContribution.fetchKnoldersWithQuarterSecondMonthContributions
      result shouldBe knoldersWithQuarterlyBlogs
    }

    "return number of contributions of each knolder in third month of quarter" in {

      insertKnolder
      insertBlogs(thirdMonth)
      insertKnolx(thirdMonth)
      insertWebinar(thirdMonth)
      insertTechHub(thirdMonth)
      insertConference(thirdMonth)

      val knoldersWithQuarterlyBlogs = List(GetContributionCount(1, "Mukesh Kumar", 2, 2, 2, 2, 0, 2),
        GetContributionCount(3, "Komal Rajpal", 1, 0, 0, 0, 0, 0),
        GetContributionCount(2, "Abhishek Baranwal", 1, 0, 0, 0, 0, 0))

      val result = readContribution.fetchKnoldersWithQuarterThirdMonthContributions
      result shouldBe knoldersWithQuarterlyBlogs
    }

    "return score of particular knolder in particular month" in {


      insertKnolder
      insertBlogs(date)
      insertKnolx(date)
      insertWebinar(date)
      insertTechHub(date)
      insertConference(date)

      val result = readContribution.fetchKnoldersWithTwelveMonthContributions(6, 2020, 1)
      result shouldBe Option(10, 40, 30, 30, 0, 200)
    }
  }
}
