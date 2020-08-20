package com.knoldus.leader_board.infrastructure

import java.sql.{Connection, PreparedStatement, Timestamp}

import com.knoldus.leader_board.{Contribution, ContributionDetails, DatabaseConnection, KnolderDetails}
import com.typesafe.config.ConfigFactory
import org.scalatest.{BeforeAndAfterEach, DoNotDiscover}

@DoNotDiscover
class FetchKnolderDetailsImplSpec extends DBSpec with BeforeAndAfterEach {
  implicit val connection: Connection = DatabaseConnection.connection(ConfigFactory.load())
  val fetchKnolderDetails: FetchKnolderDetails = new FetchKnolderDetailsImpl(ConfigFactory.load())

  override def afterEach(): Unit = {
    cleanUpDatabase(connection)
  }

  override def beforeEach(): Unit = {
    createTable(connection)
  }

  "fetch knolder details" should {
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

    "return monthly details of specific knolder" in {
      insertBlog
      insertKnolx
      insertWebinar
      insertTechHub
      insertKnolder
      insertOSContribution

      val osContributionTitles = List(ContributionDetails("Reactive Microservices", date.toString),
        ContributionDetails("Delta Lake", date.toString))

      val osContributionDetails = Option(Contribution("OSContribution", 2, 60, osContributionTitles))

      val blogTitles = List(ContributionDetails("windows handling using selenium webdriver", date.toString),
        ContributionDetails("Java 9: Enhance your Jav…ptional API enhancement", date.toString))
      val blogDetails = Option(Contribution("Blogs", 2, 10, blogTitles))

      val knolxTitles = List(ContributionDetails("Reactive Microservices", date.toString),
        ContributionDetails("Delta Lake", date.toString))
      val knolxDetails = Option(Contribution("Knolx", 2, 40, knolxTitles))

      val webinarTitles = List(ContributionDetails("Reactive Microservices", date.toString),
        ContributionDetails("Delta Lake", date.toString))

      val webinarDetails = Option(Contribution("Webinar", 2, 30, webinarTitles))
      val techhubTitles = List(ContributionDetails("Reactive Microservices", date.toString),
        ContributionDetails("Delta Lake", date.toString))

      val techhubDetails = Option(Contribution("TechHub", 2, 30, techhubTitles))
      val contributions = List(blogDetails, knolxDetails, webinarDetails, techhubDetails, osContributionDetails)
      val knolderDetails = KnolderDetails("Mukesh Gupta", 170, contributions)

      fetchKnolderDetails.fetchKnolderMonthlyDetails(1, 4, 2020).
        map(details => assert(details == knolderDetails))
    }

    "return all time details of specific knolder" in {

      insertBlog
      insertKnolx
      insertWebinar
      insertTechHub
      insertKnolder
      insertOSContribution

      val osContributionTitles = List(ContributionDetails("Reactive Microservices", date.toString),
        ContributionDetails("Delta Lake", date.toString))

      val osContributionDetails = Option(Contribution("OSContribution", 2, 60, osContributionTitles))
      val techhubTitles = List(ContributionDetails("Reactive Microservices", date.toString),
        ContributionDetails("Delta Lake", date.toString))

      val techhubDetails = Option(Contribution("TechHub", 2, 30, techhubTitles))


      val blogTitles = List(ContributionDetails("windows handling using selenium webdriver", date.toString),
        ContributionDetails("Java 9: Enhance your Jav…ptional API enhancement", date.toString))
      val blogDetails = Option(Contribution("Blogs", 2, 10, blogTitles))

      val knolxTitles = List(ContributionDetails("Reactive Microservices", date.toString),
        ContributionDetails("Delta Lake", date.toString))
      val knolxDetails = Option(Contribution("Knolx", 2, 40, knolxTitles))

      val webinarTitles = List(ContributionDetails("Reactive Microservices", date.toString),
        ContributionDetails("Delta Lake", date.toString))

      val webinarDetails = Option(Contribution("Webinar", 2, 30, webinarTitles))

      val contributions = List(blogDetails, knolxDetails, webinarDetails, techhubDetails, osContributionDetails)
      val knolderDetails = KnolderDetails("Mukesh Gupta", 170, contributions)

      fetchKnolderDetails.fetchKnolderAllTimeDetails(1).
        map(details => assert(details == knolderDetails))
    }
    "return monthly details of blogs of knolder" in {

      insertBlog
      insertKnolder

      val blogTitles = List(ContributionDetails("windows handling using selenium webdriver", date.toString),
        ContributionDetails("Java 9: Enhance your Jav…ptional API enhancement", date.toString))
      val blogDetails = Option(Contribution("Blogs", 2, 10, blogTitles))

      assert(fetchKnolderDetails.fetchKnolderMonthlyBlogDetails(4, 2020, 1) == blogDetails)

    }
    "return monthly details of knolx of knolder" in {


      insertKnolx
      insertKnolder


      val knolxTitles = List(ContributionDetails("Reactive Microservices", date.toString),
        ContributionDetails("Delta Lake", date.toString))
      val knolxDetails = Option(Contribution("Knolx", 2, 40, knolxTitles))

      assert(fetchKnolderDetails.fetchKnolderMonthlyKnolxDetails(4, 2020, 1) == knolxDetails)

    }

    "return monthly details of techhub of knolder" in {

      insertTechHub
      insertKnolder

      val techhubTitles = List(ContributionDetails("Reactive Microservices", date.toString),
        ContributionDetails("Delta Lake", date.toString))
      val techhubDetails = Option(Contribution("TechHub", 2, 30, techhubTitles))

      assert(fetchKnolderDetails.fetchKnolderMonthlyTechHubDetails(4, 2020, 1) == techhubDetails)
    }

    "return monthly details of webinar of knolder" in {

      insertWebinar
      insertKnolder


      val webinarTitles = List(ContributionDetails("Reactive Microservices", date.toString),
        ContributionDetails("Delta Lake", date.toString))
      val webinarDetails = Option(Contribution("Webinar", 2, 30, webinarTitles))

      assert(fetchKnolderDetails.fetchKnolderMonthlyWebinarDetails(4, 2020, 1) == webinarDetails)
    }

    "return all time details of webinar of knolder" in {
      insertWebinar
      insertKnolder

      val webinarTitles = List(ContributionDetails("Reactive Microservices", date.toString),
        ContributionDetails("Delta Lake", date.toString))
      val webinarDetails = Option(Contribution("Webinar", 2, 30, webinarTitles))

      assert(fetchKnolderDetails.fetchAllTimeWebinarDetails(1) == webinarDetails)

    }
    "return all time details of knolx of knolder" in {

      insertKnolx
      insertKnolder

      val knolxTitles = List(ContributionDetails("Reactive Microservices", date.toString),
        ContributionDetails("Delta Lake", date.toString))
      val knolxDetails = Option(Contribution("Knolx", 2, 40, knolxTitles))

      assert(fetchKnolderDetails.fetchAllTimeknolxDetails(1) == knolxDetails)
    }
    "return all time details of blogs of knolder" in {
      insertBlog
      insertKnolder

      val blogTitles = List(ContributionDetails("windows handling using selenium webdriver", date.toString),
        ContributionDetails("Java 9: Enhance your Jav…ptional API enhancement", date.toString))
      val blogDetails = Option(Contribution("Blogs", 2, 10, blogTitles))

      assert(fetchKnolderDetails.fetchAllTimeBlogDetails(1) == blogDetails)

    }
    "return all time details of techhub of knolder" in {

      insertTechHub
      insertKnolder


      val techhubTitles = List(ContributionDetails("Reactive Microservices", date.toString),
        ContributionDetails("Delta Lake", date.toString))

      val techhubDetails = Option(Contribution("TechHub", 2, 30, techhubTitles))

      assert(fetchKnolderDetails.fetchAllTimeTechHubDetails(1) == techhubDetails)
    }
    "return monthly details of oscontribution of knolder" in {


      insertKnolder
      insertOSContribution
      val osContributionTitles = List(ContributionDetails("Reactive Microservices", date.toString),
        ContributionDetails("Delta Lake", date.toString))

      val osContributionDetails = Option(Contribution("OSContribution", 2, 60, osContributionTitles))


      assert(fetchKnolderDetails.fetchKnolderMonthlyOsContributionDetails(4, 2020, 1) == osContributionDetails)

    }
    "return all time details of oscontribution of knolder" in {

      insertKnolder
      insertOSContribution

      val osContributionTitles = List(ContributionDetails("Reactive Microservices", date.toString),
        ContributionDetails("Delta Lake", date.toString))

      val osContributionDetails = Option(Contribution("OSContribution", 2, 60, osContributionTitles))

      assert(fetchKnolderDetails.fetchAllTimeOsContributionDetails(1) == osContributionDetails)
    }
  }
}
