package com.knoldus.leader_board.infrastructure

import java.sql.{Connection, PreparedStatement, Timestamp}
import java.time.Instant

import com.knoldus.leader_board.DatabaseConnection
import com.typesafe.config.ConfigFactory
import org.scalatest.{BeforeAndAfterEach, DoNotDiscover}

@DoNotDiscover
class FetchTechHubImplSpec extends DBSpec with BeforeAndAfterEach {
  implicit val connection: Connection = DatabaseConnection.connection(ConfigFactory.load())
  val fetchData: FetchTechHub = new FetchTechHubImpl(ConfigFactory.load())

  override def afterEach(): Unit = {
    cleanUpDatabase(connection)
  }

  override protected def beforeEach(): Unit = {
    createTable(connection)
  }

  "Fetch Techhub" should {

    "return maximum delivered date of techhub" in {
      val insertTechHub1: String =
        """
          |insert into techHub(id, email_id, uploaded_on, title)
          |values (?,?,?,?)
""".stripMargin

      val preparedStmt8: PreparedStatement = connection.prepareStatement(insertTechHub1)
      preparedStmt8.setInt(1, 1)
      preparedStmt8.setString(2, "mukesh.kumar@knoldus.com")
      preparedStmt8.setTimestamp(3, Timestamp.from(Instant.parse("2020-04-13T14:56:40Z")))
      preparedStmt8.setString(4, "Reactive Microservices")
      preparedStmt8.execute
      preparedStmt8.close()

      val insertTechHub2: String =
        """
          |insert into techhub(id, email_id, uploaded_on, title)
          |values (?,?,?,?)
""".stripMargin

      val preparedStmt9: PreparedStatement = connection.prepareStatement(insertTechHub2)
      preparedStmt9.setInt(1, 4)
      preparedStmt9.setString(2, "mukesh.kumar@knoldus.com")
      preparedStmt9.setTimestamp(3, Timestamp.from(Instant.parse("2020-04-13T13:10:40Z")))
      preparedStmt9.setString(4, "Delta Lake")
      preparedStmt9.execute
      preparedStmt9.close()

      val result = fetchData.getLastUpdatedDateForTechHub
      result.map { date =>
        date shouldBe Timestamp.from(Instant.parse("2020-04-13T14:56:40Z"))
      }
    }
  }
}
