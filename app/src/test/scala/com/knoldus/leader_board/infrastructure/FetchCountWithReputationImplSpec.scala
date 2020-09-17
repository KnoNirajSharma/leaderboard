package com.knoldus.leader_board.infrastructure

import java.sql.{Connection, PreparedStatement}

import com.knoldus.leader_board.{DatabaseConnection, Reputation, ReputationWithCount}
import com.typesafe.config.ConfigFactory
import org.mockito.MockitoSugar
import org.scalatest.matchers.should.Matchers
import org.scalatest.{BeforeAndAfterEach, DoNotDiscover}

@DoNotDiscover
class FetchCountWithReputationImplSpec extends DBSpec with BeforeAndAfterEach with MockitoSugar with Matchers {
  implicit val connection: Connection = DatabaseConnection.connection(ConfigFactory.load())
  val mockFetchReputation: FetchReputation = mock[FetchReputation]
  val fetchReputationWithCount = new FetchCountWithReputationImpl(ConfigFactory.load(), mockFetchReputation)


  override def afterEach(): Unit = {
    cleanUpDatabase(connection)
  }

  override def beforeEach(): Unit = {
    createTable(connection)
  }

  "fetch reputation with count" should {

    "return reputation of each knolder with all time and monthly count" in {


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

      val monthlyCountAndReputation = Option(ReputationWithCount(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, reputations))

      assert(fetchReputationWithCount.allTimeAndMonthlyContributionCountWithReputation == monthlyCountAndReputation)
    }
  }
}
