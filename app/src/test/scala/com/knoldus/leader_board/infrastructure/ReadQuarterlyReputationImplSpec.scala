package com.knoldus.leader_board.infrastructure

import java.sql.{Connection, PreparedStatement}

import com.knoldus.leader_board.DatabaseConnection
import com.typesafe.config.ConfigFactory
import org.scalatest.{BeforeAndAfterEach, DoNotDiscover}

@DoNotDiscover
class ReadQuarterlyReputationImplSpec extends DBSpec with BeforeAndAfterEach {
  implicit val connection: Connection = DatabaseConnection.connection(ConfigFactory.load())
  val readQuarterlyReputation: ReadQuarterlyReputation = new ReadQuarterlyReputationImpl(ConfigFactory.load())

  override def afterEach(): Unit = {
    cleanUpDatabase(connection)
  }

  override def beforeEach(): Unit = {
    createTable(connection)
  }

  "read quarterly reputation" should {

    "return knolder id from quarterly reputation table" in {
      val insertQuarterlyReputationData: String =
        """
          |insert into quarterly_reputation(id, knolder_id, streak)
          |values (?,?,?)
""".stripMargin

      val preparedStmt4: PreparedStatement = connection.prepareStatement(insertQuarterlyReputationData)
      preparedStmt4.setInt(1, 1)
      preparedStmt4.setInt(2, 1)
      preparedStmt4.setString(3, "15-20-20")
      preparedStmt4.execute
      preparedStmt4.close()

      val result = readQuarterlyReputation.fetchKnolderIdFromQuarterlyReputation(1)
      result.map { id =>
        id shouldBe 1
      }
    }
  }
}
