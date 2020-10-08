package com.knoldus.leader_board.infrastructure

import java.sql.{Connection, PreparedStatement, Timestamp}

import com.knoldus.leader_board.{DatabaseConnection, GetContributionCount, GetReputation}
import com.typesafe.config.ConfigFactory
import org.mockito.MockitoSugar
import org.scalatest.matchers.should.Matchers
import org.scalatest.{BeforeAndAfterEach, DoNotDiscover}

@DoNotDiscover
class FetchMonthlyContributionCountImplSpec extends DBSpec with BeforeAndAfterEach with MockitoSugar with Matchers {
  implicit val connection: Connection = DatabaseConnection.connection(ConfigFactory.load())

  val fetchMonthlyContribution = new FetchMonthlyContributionCountImpl(ConfigFactory.load)

  override def afterEach(): Unit = {
    cleanUpDatabase(connection)
  }

  override protected def beforeEach(): Unit = {
    createTable(connection)
  }

  "FetchMonthlyContributionImpl" should {

    val date = Timestamp.valueOf("2020-04-13 13:10:40")

    def insertBlog: Unit = {
      val insertBlogOne: String =
        """
          |insert into blog(id, wordpress_id, published_on, title)
          |values (?,?,?,?)
""".stripMargin

      val preparedStmtOne: PreparedStatement = connection.prepareStatement(insertBlogOne)
      preparedStmtOne.setInt(1, 1001)
      preparedStmtOne.setString(2, "mukesh01")
      preparedStmtOne.setTimestamp(3, date)
      preparedStmtOne.setString(4, "windows handling using selenium webdriver")
      preparedStmtOne.execute
      preparedStmtOne.close()

      val insertBlogTwo: String =
        """
          |insert into blog(id, wordpress_id, published_on, title)
          |values (?,?,?,?)
""".stripMargin

      val preparedStmtTwo: PreparedStatement = connection.prepareStatement(insertBlogTwo)
      preparedStmtTwo.setInt(1, 1004)
      preparedStmtTwo.setString(2, "mukesh01")
      preparedStmtTwo.setTimestamp(3, date)
      preparedStmtTwo.setString(4, "Java 9: Enhance your Jav…ptional API enhancement")
      preparedStmtTwo.execute
      preparedStmtTwo.close()
    }

    def insertKnolx {
      val insertKnolxOne: String =
        """
          |insert into knolx(id, email_id, delivered_on, title)
          |values (?,?,?,?)
""".stripMargin

      val preparedStmtThree: PreparedStatement = connection.prepareStatement(insertKnolxOne)
      preparedStmtThree.setInt(1, 1)
      preparedStmtThree.setString(2, "mukesh.kumar@knoldus.com")
      preparedStmtThree.setTimestamp(3, date)
      preparedStmtThree.setString(4, "Reactive Microservices")
      preparedStmtThree.execute
      preparedStmtThree.close()

      val insertKnolxTwo: String =
        """
          |insert into knolx(id, email_id, delivered_on, title)
          |values (?,?,?,?)
""".stripMargin

      val preparedStmtFour: PreparedStatement = connection.prepareStatement(insertKnolxTwo)
      preparedStmtFour.setInt(1, 4)
      preparedStmtFour.setString(2, "mukesh.kumar@knoldus.com")
      preparedStmtFour.setTimestamp(3, date)
      preparedStmtFour.setString(4, "Delta Lake")
      preparedStmtFour.execute
      preparedStmtFour.close()
    }

    def insertWebinar {
      val insertWebinarOne: String =
        """
          |insert into webinar(id, email_id, delivered_on, title)
          |values (?,?,?,?)
""".stripMargin


      val preparedStmtFive: PreparedStatement = connection.prepareStatement(insertWebinarOne)
      preparedStmtFive.setInt(1, 1)
      preparedStmtFive.setString(2, "mukesh.kumar@knoldus.com")
      preparedStmtFive.setTimestamp(3, date)
      preparedStmtFive.setString(4, "Reactive Microservices")
      preparedStmtFive.execute
      preparedStmtFive.close()

      val insertWebinarTwo: String =
        """
          |insert into webinar(id, email_id, delivered_on, title)
          |values (?,?,?,?)
""".stripMargin

      val preparedStmtSix: PreparedStatement = connection.prepareStatement(insertWebinarTwo)
      preparedStmtSix.setInt(1, 4)
      preparedStmtSix.setString(2, "mukesh.kumar@knoldus.com")
      preparedStmtSix.setTimestamp(3, date)
      preparedStmtSix.setString(4, "Delta Lake")
      preparedStmtSix.execute
      preparedStmtSix.close()
    }

    def insertKnolder {
      val insertKnolder: String =
        """
          |insert into knolder(id, full_name, wordpress_id, email_id, active_status)
          |values (?,?,?,?,?)
""".stripMargin

      val preparedStmtSeven: PreparedStatement = connection.prepareStatement(insertKnolder)
      preparedStmtSeven.setInt(1, 1)
      preparedStmtSeven.setString(2, "Mukesh Gupta")
      preparedStmtSeven.setString(3, "mukesh01")
      preparedStmtSeven.setString(4, "mukesh.kumar@knoldus.com")
      preparedStmtSeven.setBoolean(5, true)
      preparedStmtSeven.execute
      preparedStmtSeven.close()
    }

    def insertTechHub() {
      val insertTechhubOne: String =
        """
          |insert into techhub(id, email_id, uploaded_on, title)
          |values (?,?,?,?)
""".stripMargin

      val preparedStmtEight: PreparedStatement = connection.prepareStatement(insertTechhubOne)
      preparedStmtEight.setInt(1, 1)
      preparedStmtEight.setString(2, "mukesh.kumar@knoldus.com")
      preparedStmtEight.setTimestamp(3, date)
      preparedStmtEight.setString(4, "Reactive Microservices")
      preparedStmtEight.execute
      preparedStmtEight.close()

      val insertTechhubTwo: String =
        """
          |insert into techhub(id, email_id, uploaded_on, title)
          |values (?,?,?,?)
""".stripMargin

      val preparedStmtNine: PreparedStatement = connection.prepareStatement(insertTechhubTwo)
      preparedStmtNine.setInt(1, 4)
      preparedStmtNine.setString(2, "mukesh.kumar@knoldus.com")
      preparedStmtNine.setTimestamp(3, date)
      preparedStmtNine.setString(4, "Delta Lake")
      preparedStmtNine.execute
      preparedStmtNine.close()
    }

    def insertOSContribution: Unit = {
      val insertOsContributionOne: String =
        """
          |insert into oscontribution(id, email_id, contributed_on, title)
          |values (?,?,?,?)
""".stripMargin

      val preparedStmtOne: PreparedStatement = connection.prepareStatement(insertOsContributionOne)
      preparedStmtOne.setInt(1, 1)
      preparedStmtOne.setString(2, "mukesh.kumar@knoldus.com")
      preparedStmtOne.setTimestamp(3, date)
      preparedStmtOne.setString(4, "Reactive Microservices")
      preparedStmtOne.execute
      preparedStmtOne.close()

      val insertOsContributionTwo: String =
        """
          |insert into oscontribution(id, email_id, contributed_on, title)
          |values (?,?,?,?)
""".stripMargin

      val preparedStmtTwo: PreparedStatement = connection.prepareStatement(insertOsContributionTwo)
      preparedStmtTwo.setInt(1, 4)
      preparedStmtTwo.setString(2, "mukesh.kumar@knoldus.com")
      preparedStmtTwo.setTimestamp(3, date)
      preparedStmtTwo.setString(4, "Delta Lake")
      preparedStmtTwo.execute
      preparedStmtTwo.close()
    }


    def insertConferenceContribution: Unit = {
      val insertConferenceOne: String =
        """
          |insert into conference(id, email_id, delivered_on, title)
          |values (?,?,?,?)
""".stripMargin

      val preparedStmtOne: PreparedStatement = connection.prepareStatement(insertConferenceOne)
      preparedStmtOne.setInt(1, 1)
      preparedStmtOne.setString(2, "mukesh.kumar@knoldus.com")
      preparedStmtOne.setTimestamp(3, date)
      preparedStmtOne.setString(4, "Reactive Microservices")
      preparedStmtOne.execute
      preparedStmtOne.close()

      val insertConferenceTwo: String =
        """
          |insert into conference(id, email_id, delivered_on, title)
          |values (?,?,?,?)
""".stripMargin

      val preparedStmtTwo: PreparedStatement = connection.prepareStatement(insertConferenceTwo)
      preparedStmtTwo.setInt(1, 4)
      preparedStmtTwo.setString(2, "mukesh.kumar@knoldus.com")
      preparedStmtTwo.setTimestamp(3, date)
      preparedStmtTwo.setString(4, "Delta Lake")
      preparedStmtTwo.execute
      preparedStmtTwo.close()
    }

    def insertBooksContribution: Unit = {
      val insertBookOne: String =
        """
          |insert into book(id, email_id, published_on, title)
          |values (?,?,?,?)
""".stripMargin

      val preparedStmtOne: PreparedStatement = connection.prepareStatement(insertBookOne)
      preparedStmtOne.setInt(1, 1)
      preparedStmtOne.setString(2, "mukesh.kumar@knoldus.com")
      preparedStmtOne.setTimestamp(3, date)
      preparedStmtOne.setString(4, "Reactive Microservices")
      preparedStmtOne.execute
      preparedStmtOne.close()

      val insertBookTwo: String =
        """
          |insert into book(id, email_id, published_on, title)
          |values (?,?,?,?)
""".stripMargin

      val preparedStmtTwo: PreparedStatement = connection.prepareStatement(insertBookTwo)
      preparedStmtTwo.setInt(1, 4)
      preparedStmtTwo.setString(2, "mukesh.kumar@knoldus.com")
      preparedStmtTwo.setTimestamp(3, date)
      preparedStmtTwo.setString(4, "Delta Lake")
      preparedStmtTwo.execute
      preparedStmtTwo.close()
    }

    def insertResearchPaperContribution: Unit = {
      val insertResearchPaperOne: String =
        """
          |insert into researchpaper(id, email_id, published_on, title)
          |values (?,?,?,?)
""".stripMargin

      val preparedStmtOne: PreparedStatement = connection.prepareStatement(insertResearchPaperOne)
      preparedStmtOne.setInt(1, 1)
      preparedStmtOne.setString(2, "mukesh.kumar@knoldus.com")
      preparedStmtOne.setTimestamp(3, date)
      preparedStmtOne.setString(4, "Reactive Microservices")
      preparedStmtOne.execute
      preparedStmtOne.close()

      val insertResearchPaperTwo: String =
        """
          |insert into researchpaper(id, email_id, published_on, title)
          |values (?,?,?,?)
""".stripMargin

      val preparedStmtTwo: PreparedStatement = connection.prepareStatement(insertResearchPaperTwo)
      preparedStmtTwo.setInt(1, 4)
      preparedStmtTwo.setString(2, "mukesh.kumar@knoldus.com")
      preparedStmtTwo.setTimestamp(3, date)
      preparedStmtTwo.setString(4, "Delta Lake")
      preparedStmtTwo.execute
      preparedStmtTwo.close()
    }

    "return  monthly contribution of knolders" in {

      insertBlog
      insertKnolx
      insertWebinar
      insertTechHub
      insertKnolder
      insertOSContribution
      insertConferenceContribution
      insertBooksContribution
      insertResearchPaperContribution


      val monthlyContributionCount = List(GetContributionCount(1, "Mukesh Gupta",2,2,2,2,2,2,2,2))

      assert(fetchMonthlyContribution.fetchMonthlyContribution(4,2020) == monthlyContributionCount)
    }
  }
}
