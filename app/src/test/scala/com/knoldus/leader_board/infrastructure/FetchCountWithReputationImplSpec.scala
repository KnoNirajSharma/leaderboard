package com.knoldus.leader_board.infrastructure

import java.sql.{Connection, PreparedStatement}

import com.knoldus.leader_board.{DatabaseConnection, Reputation, ReputationCountAndReputation}
import com.typesafe.config.ConfigFactory
import org.mockito.MockitoSugar
import org.scalatest.matchers.should.Matchers
import org.scalatest.{BeforeAndAfterEach, DoNotDiscover}

@DoNotDiscover
class FetchCountWithReputationImplSpec extends DBSpec with BeforeAndAfterEach with MockitoSugar with Matchers {
  implicit val connection: Connection = DatabaseConnection.connection(ConfigFactory.load())
  val mockFetchReputation: FetchReputation = mock[FetchReputation]
  val fetchReputationWithCount=new FetchCountWithReputationImpl(ConfigFactory.load(),mockFetchReputation)


  override def afterEach(): Unit = {
    cleanUpDatabase(connection)
  }

  override def beforeEach(): Unit = {
    createTable(connection)
  }

  "fetch reputation with count" should {

    "return reputation of each knolder with all time and monthly count" in {
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

      val insertAllTimeReputationData2: String =
        """
          |insert into all_time_reputation(id, knolder_id, score, rank)
          |values (?,?,?,?)
""".stripMargin

      val preparedStmt2: PreparedStatement = connection.prepareStatement(insertAllTimeReputationData2)
      preparedStmt2.setInt(1, 2)
      preparedStmt2.setInt(2, 2)
      preparedStmt2.setInt(3, 5)
      preparedStmt2.setInt(4, 2)
      preparedStmt2.execute
      preparedStmt2.close()

      val insertAllTimeReputationData3: String =
        """
          |insert into all_time_reputation(id, knolder_id, score, rank)
          |values (?,?,?,?)
""".stripMargin

      val preparedStmt3: PreparedStatement = connection.prepareStatement(insertAllTimeReputationData3)
      preparedStmt3.setInt(1, 3)
      preparedStmt3.setInt(2, 3)
      preparedStmt3.setInt(3, 5)
      preparedStmt3.setInt(4, 2)
      preparedStmt3.execute
      preparedStmt3.close()

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

      val insertMonthlyReputationData2: String =
        """
          |insert into monthly_reputation(id, knolder_id, score, rank)
          |values (?,?,?,?)
""".stripMargin

      val preparedStmt5: PreparedStatement = connection.prepareStatement(insertMonthlyReputationData2)
      preparedStmt5.setInt(1, 2)
      preparedStmt5.setInt(2, 2)
      preparedStmt5.setInt(3, 5)
      preparedStmt5.setInt(4, 2)
      preparedStmt5.execute
      preparedStmt5.close()

      val insertMonthlyReputationData3: String =
        """
          |insert into monthly_reputation(id, knolder_id, score, rank)
          |values (?,?,?,?)
""".stripMargin

      val preparedStmt6: PreparedStatement = connection.prepareStatement(insertMonthlyReputationData3)
      preparedStmt6.setInt(1, 3)
      preparedStmt6.setInt(2, 3)
      preparedStmt6.setInt(3, 5)
      preparedStmt6.setInt(4, 2)
      preparedStmt6.execute
      preparedStmt6.close()

      val insertQuarterlyReputationData1: String =
        """
          |insert into quarterly_reputation(id, knolder_id, streak)
          |values (?,?,?)
""".stripMargin

      val preparedStmt7: PreparedStatement = connection.prepareStatement(insertQuarterlyReputationData1)
      preparedStmt7.setInt(1, 1)
      preparedStmt7.setInt(2, 1)
      preparedStmt7.setString(3, "15-20-20")
      preparedStmt7.execute
      preparedStmt7.close()

      val insertQuarterlyReputationData2: String =
        """
          |insert into quarterly_reputation(id, knolder_id, streak)
          |values (?,?,?)
""".stripMargin

      val preparedStmt8: PreparedStatement = connection.prepareStatement(insertQuarterlyReputationData2)
      preparedStmt8.setInt(1, 2)
      preparedStmt8.setInt(2, 2)
      preparedStmt8.setString(3, "10-10-15")
      preparedStmt8.execute
      preparedStmt8.close()

      val insertQuarterlyReputationData3: String =
        """
          |insert into quarterly_reputation(id, knolder_id, streak)
          |values (?,?,?)
""".stripMargin

      val preparedStmt9: PreparedStatement = connection.prepareStatement(insertQuarterlyReputationData3)
      preparedStmt9.setInt(1, 3)
      preparedStmt9.setInt(2, 3)
      preparedStmt9.setString(3, "5-10-5")
      preparedStmt9.execute
      preparedStmt9.close()

      val insertKnolder1: String =
        """
          |insert into knolder(id, full_name, wordpress_id, email_id, active_status)
          |values (?,?,?,?,?)
""".stripMargin

      val preparedStmt10: PreparedStatement = connection.prepareStatement(insertKnolder1)
      preparedStmt10.setInt(1, 1)
      preparedStmt10.setString(2, "Mukesh Gupta")
      preparedStmt10.setString(3, "mukesh01")
      preparedStmt10.setString(4, "mukesh.kumar@knoldus.com")
      preparedStmt10.setBoolean(5, true)
      preparedStmt10.execute
      preparedStmt10.close()

      val insertKnolder2: String =
        """
          |insert into knolder(id, full_name, wordpress_id, email_id, active_status)
          |values (?,?,?,?,?)
""".stripMargin

      val preparedStmt11: PreparedStatement = connection.prepareStatement(insertKnolder2)
      preparedStmt11.setInt(1, 2)
      preparedStmt11.setString(2, "Abhishek Baranwal")
      preparedStmt11.setString(3, "abhishek02")
      preparedStmt11.setString(4, "abhishek.baranwal@knoldus.com")
      preparedStmt11.setBoolean(5, true)
      preparedStmt11.execute
      preparedStmt11.close()

      val insertKnolder3: String =
        """
          |insert into knolder(id, full_name, wordpress_id, email_id, active_status)
          |values (?,?,?,?,?)
""".stripMargin

      val preparedStmt12: PreparedStatement = connection.prepareStatement(insertKnolder3)
      preparedStmt12.setInt(1, 3)
      preparedStmt12.setString(2, "Komal Rajpal")
      preparedStmt12.setString(3, "komal03")
      preparedStmt12.setString(4, "komal.rajpal@knoldus.com")
      preparedStmt12.setBoolean(5, false)
      preparedStmt12.execute
      preparedStmt12.close()

      val reputations = List(Reputation(1, "Mukesh Gupta", 10, 1, "15-20-20", 10, 1),
        Reputation(2, "Abhishek Baranwal", 5, 2, "10-10-15", 5, 2))
      when(mockFetchReputation.fetchReputation).thenReturn(reputations)

      val monthlyCountAndReputation = Option(ReputationCountAndReputation(0, 0, 0, 0, reputations))

      assert(fetchReputationWithCount.allTimeAndMonthlyContributionCountWithReputation == monthlyCountAndReputation)
    }
  }
}
