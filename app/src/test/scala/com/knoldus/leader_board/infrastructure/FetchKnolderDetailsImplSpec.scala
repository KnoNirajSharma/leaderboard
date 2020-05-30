package com.knoldus.leader_board.infrastructure

import java.sql.{Connection, PreparedStatement, Timestamp}

import com.knoldus.leader_board.{Constant, ContributionTitles, DatabaseConnection, KnolderDetails}
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
    val currentMonth = Timestamp.valueOf(Constant.CURRENT_TIME
      .withDayOfMonth(1).toLocalDate.atStartOfDay())
    val currentMonthAndYear = s"${Constant.CURRENT_TIME.toLocalDate.getMonth} " +
      s"${Constant.CURRENT_TIME.toLocalDate.getYear}"

    "return details of each knolder" in {
      val insertAllTimeReputationData1: String =
        """
          |insert into all_time_reputation(id, knolder_id, full_name, score, rank)
          |values (?,?,?,?,?)
""".stripMargin

      val preparedStmt1: PreparedStatement = connection.prepareStatement(insertAllTimeReputationData1)
      preparedStmt1.setInt(1, 1)
      preparedStmt1.setInt(2, 1)
      preparedStmt1.setString(3, "Mukesh Gupta")
      preparedStmt1.setInt(4, 10)
      preparedStmt1.setInt(5, 1)
      preparedStmt1.execute
      preparedStmt1.close()

      val insertMonthlyReputationData1: String =
        """
          |insert into monthly_reputation(id, knolder_id, full_name, score, rank)
          |values (?,?,?,?,?)
""".stripMargin

      val preparedStmt4: PreparedStatement = connection.prepareStatement(insertMonthlyReputationData1)
      preparedStmt4.setInt(1, 1)
      preparedStmt4.setInt(2, 1)
      preparedStmt4.setString(3, "Mukesh Gupta")
      preparedStmt4.setInt(4, 10)
      preparedStmt4.setInt(5, 1)
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
      preparedStmt7.setTimestamp(3, currentMonth)
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
      preparedStmt10.setTimestamp(3, currentMonth)
      preparedStmt10.setString(4, "Java 9: Enhance your Jav…ptional API enhancement")
      preparedStmt10.execute
      preparedStmt10.close()

      val insertKnolder1: String =
        """
          |insert into knolder(id, full_name, wordpress_id, email_id)
          |values (?,?,?,?)
""".stripMargin

      val preparedStmt11: PreparedStatement = connection.prepareStatement(insertKnolder1)
      preparedStmt11.setInt(1, 1)
      preparedStmt11.setString(2, "Mukesh Gupta")
      preparedStmt11.setString(3, "mukesh01")
      preparedStmt11.setString(4, "mukesh.kumar@knoldus.com")
      preparedStmt11.execute
      preparedStmt11.close()

      val titles = List(ContributionTitles("windows handling using selenium webdriver", currentMonth.toString),
        ContributionTitles("Java 9: Enhance your Jav…ptional API enhancement", currentMonth.toString))

      val knolderDetails = KnolderDetails("Mukesh Gupta", currentMonthAndYear, 10, 10, 10, titles)

      fetchKnolderDetails.fetchKnolderDetails(1).map(details => assert(details == knolderDetails))
    }
  }
}
