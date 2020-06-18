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
      val insertAllTimeReputationData1: String =
        """
          |insert into all_time_reputation(id, knolder_id, score, rank)
          |values (?,?,?,?)
""".stripMargin

      val preparedStmt1: PreparedStatement = connection.prepareStatement(insertAllTimeReputationData1)
      preparedStmt1.setInt(1, 1)
      preparedStmt1.setInt(2, 1)
      preparedStmt1.setInt(3, 10)
      preparedStmt1.setInt(4, 1)
      preparedStmt1.execute
      preparedStmt1.close()

      val insertMonthlyReputationData1: String =
        """
          |insert into monthly_reputation(id, knolder_id, score, rank)
          |values (?,?,?,?)
""".stripMargin

      val preparedStmt4: PreparedStatement = connection.prepareStatement(insertMonthlyReputationData1)
      preparedStmt4.setInt(1, 1)
      preparedStmt4.setInt(2, 1)
      preparedStmt4.setInt(3, 10)
      preparedStmt4.setInt(4, 1)
      preparedStmt4.execute
      preparedStmt4.close()

      val insertBlog1: String =
        """
          |insert into blog(id, wordpress_id, published_on, title)
          |values (?,?,?,?)
""".stripMargin

      val preparedStmt7: PreparedStatement = connection.prepareStatement(insertBlog1)
      preparedStmt7.setInt(1, 1001)
      preparedStmt7.setString(2, "mukesh01")
      preparedStmt7.setTimestamp(3, date)
      preparedStmt7.setString(4, "windows handling using selenium webdriver")
      preparedStmt7.execute
      preparedStmt7.close()

      val insertBlog4: String =
        """
          |insert into blog(id, wordpress_id, published_on, title)
          |values (?,?,?,?)
""".stripMargin

      val preparedStmt10: PreparedStatement = connection.prepareStatement(insertBlog4)
      preparedStmt10.setInt(1, 1004)
      preparedStmt10.setString(2, "mukesh01")
      preparedStmt10.setTimestamp(3, date)
      preparedStmt10.setString(4, "Java 9: Enhance your Jav…ptional API enhancement")
      preparedStmt10.execute
      preparedStmt10.close()

      val insertKnolder1: String =
        """
          |insert into knolder(id, full_name, wordpress_id, email_id, active_status)
          |values (?,?,?,?,?)
""".stripMargin

      val preparedStmt11: PreparedStatement = connection.prepareStatement(insertKnolder1)
      preparedStmt11.setInt(1, 1)
      preparedStmt11.setString(2, "Mukesh Gupta")
      preparedStmt11.setString(3, "mukesh01")
      preparedStmt11.setString(4, "mukesh.kumar@knoldus.com")
      preparedStmt11.setBoolean(5, true)
      preparedStmt11.execute
      preparedStmt11.close()

      val blogTitles = List(ContributionDetails("windows handling using selenium webdriver", date.toString),
        ContributionDetails("Java 9: Enhance your Jav…ptional API enhancement", date.toString))
      val blogDetails = Option(Contribution("Blogs", 2, 10, blogTitles))
      val contributions = List(blogDetails)
      val knolderDetails = KnolderDetails("Mukesh Gupta", 10, contributions)

      fetchKnolderDetails.fetchKnolderMonthlyDetails(1, 4, 2020).
        map(details => assert(details == knolderDetails))
    }

    "return all time details of specific knolder" in {
      val insertAllTimeReputationData1: String =
        """
          |insert into all_time_reputation(id, knolder_id, score, rank)
          |values (?,?,?,?)
""".stripMargin

      val preparedStmt1: PreparedStatement = connection.prepareStatement(insertAllTimeReputationData1)
      preparedStmt1.setInt(1, 1)
      preparedStmt1.setInt(2, 1)
      preparedStmt1.setInt(3, 10)
      preparedStmt1.setInt(4, 1)
      preparedStmt1.execute
      preparedStmt1.close()

      val insertMonthlyReputationData1: String =
        """
          |insert into monthly_reputation(id, knolder_id, score, rank)
          |values (?,?,?,?)
""".stripMargin

      val preparedStmt4: PreparedStatement = connection.prepareStatement(insertMonthlyReputationData1)
      preparedStmt4.setInt(1, 1)
      preparedStmt4.setInt(2, 1)
      preparedStmt4.setInt(3, 10)
      preparedStmt4.setInt(4, 1)
      preparedStmt4.execute
      preparedStmt4.close()

      val insertBlog1: String =
        """
          |insert into blog(id, wordpress_id, published_on, title)
          |values (?,?,?,?)
""".stripMargin

      val preparedStmt7: PreparedStatement = connection.prepareStatement(insertBlog1)
      preparedStmt7.setInt(1, 1001)
      preparedStmt7.setString(2, "mukesh01")
      preparedStmt7.setTimestamp(3, date)
      preparedStmt7.setString(4, "windows handling using selenium webdriver")
      preparedStmt7.execute
      preparedStmt7.close()

      val insertBlog4: String =
        """
          |insert into blog(id, wordpress_id, published_on, title)
          |values (?,?,?,?)
""".stripMargin

      val preparedStmt10: PreparedStatement = connection.prepareStatement(insertBlog4)
      preparedStmt10.setInt(1, 1004)
      preparedStmt10.setString(2, "mukesh01")
      preparedStmt10.setTimestamp(3, date)
      preparedStmt10.setString(4, "Java 9: Enhance your Jav…ptional API enhancement")
      preparedStmt10.execute
      preparedStmt10.close()

      val insertKnolder1: String =
        """
          |insert into knolder(id, full_name, wordpress_id, email_id, active_status)
          |values (?,?,?,?,?)
""".stripMargin

      val preparedStmt11: PreparedStatement = connection.prepareStatement(insertKnolder1)
      preparedStmt11.setInt(1, 1)
      preparedStmt11.setString(2, "Mukesh Gupta")
      preparedStmt11.setString(3, "mukesh01")
      preparedStmt11.setString(4, "mukesh.kumar@knoldus.com")
      preparedStmt11.setBoolean(5, true)
      preparedStmt11.execute
      preparedStmt11.close()

      val blogTitles = List(ContributionDetails("windows handling using selenium webdriver", date.toString),
        ContributionDetails("Java 9: Enhance your Jav…ptional API enhancement", date.toString))
      val blogDetails = Option(Contribution("Blogs", 2, 10, blogTitles))
      val contributions = List(blogDetails)
      val knolderDetails = KnolderDetails("Mukesh Gupta", 10, contributions)

      fetchKnolderDetails.fetchKnolderAllTimeDetails(1).
        map(details => assert(details == knolderDetails))
    }
  }
}
