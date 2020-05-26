package com.knoldus.leader_board.infrastructure

import java.sql.{Connection, PreparedStatement}

import com.knoldus.leader_board.DatabaseConnection
import com.typesafe.config.ConfigFactory
import org.scalatest.{BeforeAndAfterEach, DoNotDiscover}

@DoNotDiscover
class ReadAllTimeReputationImplSpec extends DBSpec with BeforeAndAfterEach {
  implicit val connection: Connection = DatabaseConnection.connection(ConfigFactory.load())
  val readAllTimeReputation: ReadAllTimeReputation = new ReadAllTimeReputationImpl(ConfigFactory.load())

  override def afterEach(): Unit = {
    cleanUpDatabase(connection)
  }

  override def beforeEach(): Unit = {
    createTable(connection)
  }

  "read all time reputation" should {

    "return knolder id from all time reputation table" in {
      val insertAllTimeReputationData: String =
        """
          |insert into all_time_reputation(id, knolder_id, full_name, score, rank)
          |values (?,?,?,?,?)
""".stripMargin

      val preparedStmt: PreparedStatement = connection.prepareStatement(insertAllTimeReputationData)
      preparedStmt.setInt(1, 1)
      preparedStmt.setInt(2, 1)
      preparedStmt.setString(3, "Mukesh Gupta")
      preparedStmt.setInt(4, 10)
      preparedStmt.setInt(5, 1)
      preparedStmt.execute
      preparedStmt.close()

      val result = readAllTimeReputation.fetchKnolderIdFromAllTimeReputation(1)
      result.map { id =>
        id shouldBe 1
      }
    }
  }
}
