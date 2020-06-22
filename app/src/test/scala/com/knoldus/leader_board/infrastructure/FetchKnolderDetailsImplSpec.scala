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

    "return monthly details of specific knolder" in {
      val insertAllTimeReputationData: String =
        """
          |insert into all_time_reputation(id, knolder_id, score, rank)
          |values (?,?,?,?)
""".stripMargin

      val preparedStmt1: PreparedStatement = connection.prepareStatement(insertAllTimeReputationData)
      preparedStmt1.setInt(1, 1)
      preparedStmt1.setInt(2, 1)
      preparedStmt1.setInt(3, 10)
      preparedStmt1.setInt(4, 1)
      preparedStmt1.execute
      preparedStmt1.close()

      val insertMonthlyReputationData: String =
        """
          |insert into monthly_reputation(id, knolder_id, score, rank)
          |values (?,?,?,?)
""".stripMargin

      val preparedStmt2: PreparedStatement = connection.prepareStatement(insertMonthlyReputationData)
      preparedStmt2.setInt(1, 1)
      preparedStmt2.setInt(2, 1)
      preparedStmt2.setInt(3, 10)
      preparedStmt2.setInt(4, 1)
      preparedStmt2.execute
      preparedStmt2.close()

      val insertBlog1: String =
        """
          |insert into blog(id, wordpress_id, published_on, title)
          |values (?,?,?,?)
""".stripMargin

      val preparedStmt3: PreparedStatement = connection.prepareStatement(insertBlog1)
      preparedStmt3.setInt(1, 1001)
      preparedStmt3.setString(2, "mukesh01")
      preparedStmt3.setTimestamp(3, date)
      preparedStmt3.setString(4, "windows handling using selenium webdriver")
      preparedStmt3.execute
      preparedStmt3.close()

      val insertBlog2: String =
        """
          |insert into blog(id, wordpress_id, published_on, title)
          |values (?,?,?,?)
""".stripMargin

      val preparedStmt4: PreparedStatement = connection.prepareStatement(insertBlog2)
      preparedStmt4.setInt(1, 1004)
      preparedStmt4.setString(2, "mukesh01")
      preparedStmt4.setTimestamp(3, date)
      preparedStmt4.setString(4, "Java 9: Enhance your Jav…ptional API enhancement")
      preparedStmt4.execute
      preparedStmt4.close()

      val insertKnolx1: String =
        """
          |insert into knolx(id, email_id, delivered_on, title)
          |values (?,?,?,?)
""".stripMargin

      val preparedStmt8: PreparedStatement = connection.prepareStatement(insertKnolx1)
      preparedStmt8.setInt(1, 1)
      preparedStmt8.setString(2, "mukesh.kumar@knoldus.com")
      preparedStmt8.setTimestamp(3, date)
      preparedStmt8.setString(4, "Reactive Microservices")
      preparedStmt8.execute
      preparedStmt8.close()

      val insertKnolx2: String =
        """
          |insert into knolx(id, email_id, delivered_on, title)
          |values (?,?,?,?)
""".stripMargin

      val preparedStmt9: PreparedStatement = connection.prepareStatement(insertKnolx2)
      preparedStmt9.setInt(1, 4)
      preparedStmt9.setString(2, "mukesh.kumar@knoldus.com")
      preparedStmt9.setTimestamp(3, date)
      preparedStmt9.setString(4, "Delta Lake")
      preparedStmt9.execute
      preparedStmt9.close()

      val insertKnolder: String =
        """
          |insert into knolder(id, full_name, wordpress_id, email_id, active_status)
          |values (?,?,?,?,?)
""".stripMargin

      val preparedStmt7: PreparedStatement = connection.prepareStatement(insertKnolder)
      preparedStmt7.setInt(1, 1)
      preparedStmt7.setString(2, "Mukesh Gupta")
      preparedStmt7.setString(3, "mukesh01")
      preparedStmt7.setString(4, "mukesh.kumar@knoldus.com")
      preparedStmt7.setBoolean(5, true)
      preparedStmt7.execute
      preparedStmt7.close()

      val blogTitles = List(ContributionDetails("windows handling using selenium webdriver", date.toString),
        ContributionDetails("Java 9: Enhance your Jav…ptional API enhancement", date.toString))
      val blogDetails = Option(Contribution("Blogs", 2, 10, blogTitles))

      val knolxTitles = List(ContributionDetails("Reactive Microservices", date.toString),
        ContributionDetails("Delta Lake", date.toString))
      val knolxDetails = Option(Contribution("Knolx", 2, 20, knolxTitles))

      val contributions = List(blogDetails, knolxDetails)
      val knolderDetails = KnolderDetails("Mukesh Gupta", 30, contributions)

      fetchKnolderDetails.fetchKnolderMonthlyDetails(1, 4, 2020).
        map(details => assert(details == knolderDetails))
    }

    "return all time details of specific knolder" in {
      val insertAllTimeReputationData: String =
        """
          |insert into all_time_reputation(id, knolder_id, score, rank)
          |values (?,?,?,?)
""".stripMargin

      val preparedStmt1: PreparedStatement = connection.prepareStatement(insertAllTimeReputationData)
      preparedStmt1.setInt(1, 1)
      preparedStmt1.setInt(2, 1)
      preparedStmt1.setInt(3, 10)
      preparedStmt1.setInt(4, 1)
      preparedStmt1.execute
      preparedStmt1.close()

      val insertMonthlyReputationData: String =
        """
          |insert into monthly_reputation(id, knolder_id, score, rank)
          |values (?,?,?,?)
""".stripMargin

      val preparedStmt2: PreparedStatement = connection.prepareStatement(insertMonthlyReputationData)
      preparedStmt2.setInt(1, 1)
      preparedStmt2.setInt(2, 1)
      preparedStmt2.setInt(3, 10)
      preparedStmt2.setInt(4, 1)
      preparedStmt2.execute
      preparedStmt2.close()

      val insertBlog1: String =
        """
          |insert into blog(id, wordpress_id, published_on, title)
          |values (?,?,?,?)
""".stripMargin

      val preparedStmt3: PreparedStatement = connection.prepareStatement(insertBlog1)
      preparedStmt3.setInt(1, 1001)
      preparedStmt3.setString(2, "mukesh01")
      preparedStmt3.setTimestamp(3, date)
      preparedStmt3.setString(4, "windows handling using selenium webdriver")
      preparedStmt3.execute
      preparedStmt3.close()

      val insertBlog2: String =
        """
          |insert into blog(id, wordpress_id, published_on, title)
          |values (?,?,?,?)
""".stripMargin

      val preparedStmt4: PreparedStatement = connection.prepareStatement(insertBlog2)
      preparedStmt4.setInt(1, 1004)
      preparedStmt4.setString(2, "mukesh01")
      preparedStmt4.setTimestamp(3, date)
      preparedStmt4.setString(4, "Java 9: Enhance your Jav…ptional API enhancement")
      preparedStmt4.execute
      preparedStmt4.close()

      val insertKnolx1: String =
        """
          |insert into knolx(id, email_id, delivered_on, title)
          |values (?,?,?,?)
""".stripMargin

      val preparedStmt8: PreparedStatement = connection.prepareStatement(insertKnolx1)
      preparedStmt8.setInt(1, 1)
      preparedStmt8.setString(2, "mukesh.kumar@knoldus.com")
      preparedStmt8.setTimestamp(3, date)
      preparedStmt8.setString(4, "Reactive Microservices")
      preparedStmt8.execute
      preparedStmt8.close()

      val insertKnolx2: String =
        """
          |insert into knolx(id, email_id, delivered_on, title)
          |values (?,?,?,?)
""".stripMargin

      val preparedStmt9: PreparedStatement = connection.prepareStatement(insertKnolx2)
      preparedStmt9.setInt(1, 4)
      preparedStmt9.setString(2, "mukesh.kumar@knoldus.com")
      preparedStmt9.setTimestamp(3, date)
      preparedStmt9.setString(4, "Delta Lake")
      preparedStmt9.execute
      preparedStmt9.close()

      val insertKnolder: String =
        """
          |insert into knolder(id, full_name, wordpress_id, email_id, active_status)
          |values (?,?,?,?,?)
""".stripMargin

      val preparedStmt7: PreparedStatement = connection.prepareStatement(insertKnolder)
      preparedStmt7.setInt(1, 1)
      preparedStmt7.setString(2, "Mukesh Gupta")
      preparedStmt7.setString(3, "mukesh01")
      preparedStmt7.setString(4, "mukesh.kumar@knoldus.com")
      preparedStmt7.setBoolean(5, true)
      preparedStmt7.execute
      preparedStmt7.close()

      val blogTitles = List(ContributionDetails("windows handling using selenium webdriver", date.toString),
        ContributionDetails("Java 9: Enhance your Jav…ptional API enhancement", date.toString))
      val blogDetails = Option(Contribution("Blogs", 2, 10, blogTitles))

      val knolxTitles = List(ContributionDetails("Reactive Microservices", date.toString),
        ContributionDetails("Delta Lake", date.toString))
      val knolxDetails = Option(Contribution("Knolx", 2, 20, knolxTitles))

      val contributions = List(blogDetails, knolxDetails)
      val knolderDetails = KnolderDetails("Mukesh Gupta", 30, contributions)

      fetchKnolderDetails.fetchKnolderAllTimeDetails(1).
        map(details => assert(details == knolderDetails))
    }
  }
}
