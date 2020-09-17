package com.knoldus.leader_board.infrastructure

import java.sql.{Connection, PreparedStatement}

import com.knoldus.leader_board.{DatabaseConnection, GetReputation}
import com.typesafe.config.ConfigFactory
import org.mockito.MockitoSugar
import org.scalatest.matchers.should.Matchers
import org.scalatest.{BeforeAndAfterEach, DoNotDiscover}

@DoNotDiscover
class FetchAllTimeReputationImplSpec extends DBSpec with BeforeAndAfterEach with MockitoSugar with Matchers {
  implicit val connection: Connection = DatabaseConnection.connection(ConfigFactory.load())

  val fetchAllTimeReputation = new FetchAllTimeReputationImpl(ConfigFactory.load)

  override def afterEach(): Unit = {
    cleanUpDatabase(connection)
  }

  override protected def beforeEach(): Unit = {
    createTable(connection)
  }

  "FetchAllTimeReputationImpl" should {

    "return all time reputation of knolders" in {
      val insertAllTimeReputationData: String =
        """
          |insert into all_time_reputation(id, knolder_id, score, rank)
          |values (?,?,?,?)
""".stripMargin

      val preparedStmtOne: PreparedStatement = connection.prepareStatement(insertAllTimeReputationData)
      preparedStmtOne.setInt(1, 1)
      preparedStmtOne.setInt(2, 1)
      preparedStmtOne.setInt(3, 10)
      preparedStmtOne.setInt(4, 1)
      preparedStmtOne.execute
      preparedStmtOne.close()


      val preparedStmtTwo: PreparedStatement = connection.prepareStatement(insertAllTimeReputationData)
      preparedStmtTwo.setInt(1, 2)
      preparedStmtTwo.setInt(2, 2)
      preparedStmtTwo.setInt(3, 5)
      preparedStmtTwo.setInt(4, 2)
      preparedStmtTwo.execute
      preparedStmtTwo.close()


      val preparedStmtThree: PreparedStatement = connection.prepareStatement(insertAllTimeReputationData)
      preparedStmtThree.setInt(1, 3)
      preparedStmtThree.setInt(2, 3)
      preparedStmtThree.setInt(3, 5)
      preparedStmtThree.setInt(4, 2)
      preparedStmtThree.execute
      preparedStmtThree.close()


      val insertKnolder: String =
        """
          |insert into knolder(id, full_name, wordpress_id, email_id, active_status)
          |values (?,?,?,?,?)
""".stripMargin

      val preparedStmtFour: PreparedStatement = connection.prepareStatement(insertKnolder)
      preparedStmtFour.setInt(1, 1)
      preparedStmtFour.setString(2, "Mukesh Gupta")
      preparedStmtFour.setString(3, "mukesh01")
      preparedStmtFour.setString(4, "mukesh.kumar@knoldus.com")
      preparedStmtFour.setBoolean(5, true)
      preparedStmtFour.execute
      preparedStmtFour.close()


      val preparedStmtFive: PreparedStatement = connection.prepareStatement(insertKnolder)
      preparedStmtFive.setInt(1, 2)
      preparedStmtFive.setString(2, "Abhishek Baranwal")
      preparedStmtFive.setString(3, "abhishek02")
      preparedStmtFive.setString(4, "abhishek.baranwal@knoldus.com")
      preparedStmtFive.setBoolean(5, true)
      preparedStmtFive.execute
      preparedStmtFive.close()


      val preparedStmtSix: PreparedStatement = connection.prepareStatement(insertKnolder)
      preparedStmtSix.setInt(1, 3)
      preparedStmtSix.setString(2, "Komal Rajpal")
      preparedStmtSix.setString(3, "komal03")
      preparedStmtSix.setString(4, "komal.rajpal@knoldus.com")
      preparedStmtSix.setBoolean(5, false)
      preparedStmtSix.execute
      preparedStmtSix.close()

      val allTimeReputationOfKnolders = List(GetReputation(1, "Mukesh Gupta", 10, 1), GetReputation(2, "Abhishek Baranwal", 5, 2)
        , GetReputation(3, "Komal Rajpal", 5, 2))

      assert(fetchAllTimeReputation.fetchAllTimeReputation == allTimeReputationOfKnolders)
    }
  }

}
