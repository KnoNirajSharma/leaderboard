package com.knoldus.leader_board.infrastructure

import java.sql.{Connection, PreparedStatement}

import com.knoldus.leader_board.{DatabaseConnection, GetReputation}
import com.typesafe.config.ConfigFactory
import org.scalatest.{BeforeAndAfterEach, DoNotDiscover}

@DoNotDiscover
class ReadAllTimeReputationImplSpec extends DBSpec with BeforeAndAfterEach {
  implicit val connection: Connection = DatabaseConnection.connection(ConfigFactory.load())
  val readAllTimeReputation: ReadAllTimeReputation = new ReadAllTimeReputationImpl(ConfigFactory.load())

  override def afterEach(): Unit = {
    cleanUpDatabase(connection)
  }

  override protected def beforeEach(): Unit = {
    cleanUpDatabase(connection)
    createTable(connection)
  }

  "read all time reputation" should {

    "return all time reputation data of knolders" in {
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

      val insertAllTimeReputationData2: String =
        """
          |insert into all_time_reputation(id, knolder_id, full_name, score, rank)
          |values (?,?,?,?,?)
""".stripMargin

      val preparedStmt2: PreparedStatement = connection.prepareStatement(insertAllTimeReputationData2)
      preparedStmt2.setInt(1, 2)
      preparedStmt2.setInt(2, 2)
      preparedStmt2.setString(3, "Abhishek Baranwal")
      preparedStmt2.setInt(4, 5)
      preparedStmt2.setInt(5, 2)
      preparedStmt2.execute
      preparedStmt2.close()

      val insertAllTimeReputationData3: String =
        """
          |insert into all_time_reputation(id, knolder_id, full_name, score, rank)
          |values (?,?,?,?,?)
""".stripMargin

      val preparedStmt3: PreparedStatement = connection.prepareStatement(insertAllTimeReputationData3)
      preparedStmt3.setInt(1, 3)
      preparedStmt3.setInt(2, 3)
      preparedStmt3.setString(3, "Komal Rajpal")
      preparedStmt3.setInt(4, 5)
      preparedStmt3.setInt(5, 2)
      preparedStmt3.execute
      preparedStmt3.close()

      val allTimeReputationData = List(GetReputation("Mukesh Gupta", 10, 1),
        GetReputation("Abhishek Baranwal", 5, 2),
        GetReputation("Komal Rajpal", 5, 2))
      val result = readAllTimeReputation.fetchAllTimeReputationData
      result shouldBe allTimeReputationData
    }

    "return knolder id from all_time_reputation table" in {
      val insertAllTimeReputationData: String =
        """
          |insert into all_time_reputation(id, knolder_id, full_name, score, rank)
          |values (?,?,?,?,?)
""".stripMargin

      val preparedStmt4: PreparedStatement = connection.prepareStatement(insertAllTimeReputationData)
      preparedStmt4.setInt(1, 1)
      preparedStmt4.setInt(2, 1)
      preparedStmt4.setString(3, "Mukesh Gupta")
      preparedStmt4.setInt(4, 10)
      preparedStmt4.setInt(5, 1)
      preparedStmt4.execute
      preparedStmt4.close()

      val result = readAllTimeReputation.fetchKnolderIdFromAllTimeReputation(1)
      result.map { id =>
        id shouldBe 1
      }
    }
  }
}
