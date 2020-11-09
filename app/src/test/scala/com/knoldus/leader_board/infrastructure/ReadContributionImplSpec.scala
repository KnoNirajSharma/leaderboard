package com.knoldus.leader_board.infrastructure

import java.sql.{Connection, PreparedStatement, Timestamp}
import java.time.{ZoneId, ZonedDateTime}

import com.knoldus.leader_board.{ContributionScore, DatabaseConnection, GetContributionCount, IndianTime}
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

    def insertMonthlyContribution(month: String, year: Int): Unit = {
      val insertMonthlyContribution: String =
        """
          |insert into monthlycontribution(id, knolder_id,blog_score,knolx_score,webinar_score,techhub_score
          |,oscontribution_score,book_score,conference_score,researchpaper_score,meetup_score,month,year)
          |values (?,?,?,?,?,?,?,?,?,?,?,?,?)
""".stripMargin

      val preparedStmt: PreparedStatement = connection.prepareStatement(insertMonthlyContribution)
      preparedStmt.setInt(1, 1)
      preparedStmt.setInt(2, 1)
      preparedStmt.setInt(3, 10)
      preparedStmt.setInt(4, 40)
      preparedStmt.setInt(5, 30)
      preparedStmt.setInt(6, 30)
      preparedStmt.setInt(7, 60)
      preparedStmt.setInt(8, 200)
      preparedStmt.setInt(9, 200)
      preparedStmt.setInt(10, 100)
      preparedStmt.setInt(11, 30)
      preparedStmt.setString(12, month)
      preparedStmt.setInt(13, year)
      preparedStmt.execute
      preparedStmt.close()
    }

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

    def insertmeetup(dateColumnValue: Timestamp): Unit = {
      val insertmeetupOne: String =
        """
          |insert into Meetup(id, email_id, delivered_on, title)
          |values (?,?,?,?)
""".stripMargin

      val preparedStmtOne: PreparedStatement = connection.prepareStatement(insertmeetupOne)
      preparedStmtOne.setString(1, "1")
      preparedStmtOne.setString(2, "mukesh.kumar@knoldus.com")
      preparedStmtOne.setTimestamp(3, dateColumnValue)
      preparedStmtOne.setString(4, "Reactive Microservices")
      preparedStmtOne.execute
      preparedStmtOne.close()

      val insertmeetupTwo: String =
        """
          |insert into Meetup(id, email_id, delivered_on, title)
          |values (?,?,?,?)
""".stripMargin

      val preparedStmtTwo: PreparedStatement = connection.prepareStatement(insertmeetupTwo)
      preparedStmtTwo.setString(1, "4")
      preparedStmtTwo.setString(2, "mukesh.kumar@knoldus.com")
      preparedStmtTwo.setTimestamp(3, dateColumnValue)
      preparedStmtTwo.setString(4, "Delta Lake")
      preparedStmtTwo.execute
      preparedStmtTwo.close()
    }

    "return number of contributions of each knolder" in {
      val month = ZonedDateTime.now(ZoneId.of("Asia/Calcutta")).getMonth.toString
      val year = ZonedDateTime.now(ZoneId.of("Asia/Calcutta")).getYear
      insertKnolder
      insertMonthlyContribution(month, year)


      val knoldersWithContribution = List(KnolderContributionScore(2, "Abhishek Baranwal", None, None, None, None, None, None, None, None, None),
        KnolderContributionScore(1, "Mukesh Kumar", Option(10), Option(40), Option(30), Option(30), Option(60), Option(200), Option(200), Option(100), Option(30))
        , KnolderContributionScore(3, "Komal Rajpal", None, None, None, None, None, None, None, None, None))


      val result = readContribution.fetchKnoldersWithContributions
      result shouldBe knoldersWithContribution
    }

    "return number of contributions of each knolder in current month" in {
      insertKnolder
      insertBlogs(currentMonth)
      insertKnolx(currentMonth)
      insertWebinar(currentMonth)
      insertTechHub(currentMonth)
      insertConference(currentMonth)
      insertmeetup(currentMonth)
      val month = ZonedDateTime.now(ZoneId.of("Asia/Calcutta")).getMonth.toString
      val year = ZonedDateTime.now(ZoneId.of("Asia/Calcutta")).getYear

      val knoldersWithMonthlyContribution = List(GetContributionCount(1, "Mukesh Kumar", 2, 2, 2, 2, 0, 2, 0, 0, 0),
        GetContributionCount(3, "Komal Rajpal", 1, 0, 0, 0, 0, 0, 0, 0, 0),
        GetContributionCount(2, "Abhishek Baranwal", 1, 0, 0, 0, 0, 0, 0, 0, 0))

      val result = readContribution.fetchKnoldersWithMonthlyContributions(month, year)
      result shouldBe knoldersWithMonthlyContribution
    }

    "return number of contributions of each knolder in first month of quarter" in {
      val month = ZonedDateTime.now(ZoneId.of("Asia/Calcutta")).minusMonths(3).getMonth.toString
      val year = ZonedDateTime.now(ZoneId.of("Asia/Calcutta")).minusMonths(3).getYear
      insertKnolder
      insertMonthlyContribution(month, year)

      val knoldersWithQuarterlyContribution = List(KnolderContributionScore(1, "Mukesh Kumar", Option(10), Option(40), Option(30), Option(30), Option(60), Option(200), Option(200), Option(100), Option(30)),
        KnolderContributionScore(2, "Abhishek Baranwal", None, None, None, None, None, None, None, None, None),
        KnolderContributionScore(3, "Komal Rajpal", None, None, None, None, None, None, None, None, None))

      val result = readContribution.fetchKnoldersWithQuarterFirstMonthContributions
      result shouldBe knoldersWithQuarterlyContribution
    }

    "return number of contributiobns of each knolder in second month of quarter" in {
      val month = ZonedDateTime.now(ZoneId.of("Asia/Calcutta")).minusMonths(2).getMonth.toString
      val year = ZonedDateTime.now(ZoneId.of("Asia/Calcutta")).minusMonths(2).getYear
      insertKnolder
      insertMonthlyContribution(month, year)

      val knoldersWithQuarterlyContribution = List(KnolderContributionScore(1, "Mukesh Kumar", Option(10), Option(40), Option(30), Option(30), Option(60), Option(200), Option(200), Option(100), Option(30)),
        KnolderContributionScore(2, "Abhishek Baranwal", None, None, None, None, None, None, None, None, None),
        KnolderContributionScore(3, "Komal Rajpal", None, None, None, None, None, None, None, None, None))

      val result = readContribution.fetchKnoldersWithQuarterSecondMonthContributions
      result shouldBe knoldersWithQuarterlyContribution
    }

    "return number of contributions of each knolder in third month of quarter" in {

      val month = ZonedDateTime.now(ZoneId.of("Asia/Calcutta")).minusMonths(1).getMonth.toString
      val year = ZonedDateTime.now(ZoneId.of("Asia/Calcutta")).minusMonths(1).getYear
      insertKnolder
      insertMonthlyContribution(month, year)

      val knoldersWithQuarterlyContribution = List(KnolderContributionScore(1, "Mukesh Kumar", Option(10), Option(40), Option(30), Option(30), Option(60), Option(200), Option(200), Option(100), Option(30)),
        KnolderContributionScore(2, "Abhishek Baranwal", None, None, None, None, None, None, None, None, None),
        KnolderContributionScore(3, "Komal Rajpal", None, None, None, None, None, None, None, None, None))

      val result = readContribution.fetchKnoldersWithQuarterThirdMonthContributions
      result shouldBe knoldersWithQuarterlyContribution
    }

    "return score of particular knolder in particular month" in {
      val month = ZonedDateTime.now(ZoneId.of("Asia/Calcutta")).getMonth.toString
      val year = ZonedDateTime.now(ZoneId.of("Asia/Calcutta")).getYear
      insertMonthlyContribution(month, year)

      val result = readContribution.fetchKnoldersWithTwelveMonthContributions(ZonedDateTime.now(ZoneId.of("Asia/Calcutta")).getMonthValue
        , year, 1)
      result shouldBe Option(ContributionScore(10, 40, 30, 30, 60, 200, 200, 100, 30))
    }
    "return each contribution score of particular knolder in particular month" in {
      val month = ZonedDateTime.now(ZoneId.of("Asia/Calcutta")).getMonth.toString
      val year = ZonedDateTime.now(ZoneId.of("Asia/Calcutta")).getYear

      insertKnolder
      insertMonthlyContribution(month, year)

      val result = readContribution.fetchMonthlyContributionScore
      result shouldBe List(KnolderContributionScore(1, "Mukesh Kumar", Option(10), Option(40), Option(30), Option(30), Option(60), Option(200), Option(200), Option(100), Option(30)),
        KnolderContributionScore(2, "Abhishek Baranwal", None, None, None, None, None, None, None, None, None),
        KnolderContributionScore(3, "Komal Rajpal", None, None, None, None, None, None, None, None, None))
    }
  }
}
