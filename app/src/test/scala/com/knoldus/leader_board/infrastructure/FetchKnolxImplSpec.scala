package com.knoldus.leader_board.infrastructure

import java.sql.{Connection, PreparedStatement, Timestamp}
import java.time.Instant

import com.knoldus.leader_board.DatabaseConnection
import com.typesafe.config.ConfigFactory
import org.scalatest.{BeforeAndAfterEach, DoNotDiscover}

@DoNotDiscover
class FetchKnolxImplSpec extends DBSpec with BeforeAndAfterEach {
  implicit val connection: Connection = DatabaseConnection.connection(ConfigFactory.load())
  val fetchData: FetchKnolx = new FetchKnolxImpl(ConfigFactory.load())

  override def afterEach(): Unit = {
    cleanUpDatabase(connection)
  }

  override protected def beforeEach(): Unit = {
    createTable(connection)
  }

  "Fetch Knolx" should {

    "return maximum delivered date of knolx" in {
      val insertKnolx1: String =
        """
          |insert into knolx(id, email_id, delivered_on, title)
          |values (?,?,?,?)
""".stripMargin

      val preparedStmt8: PreparedStatement = connection.prepareStatement(insertKnolx1)
      preparedStmt8.setInt(1, 1)
      preparedStmt8.setString(2, "mukesh.kumar@knoldus.com")
      preparedStmt8.setTimestamp(3, Timestamp.from(Instant.parse("2020-04-13T14:56:40Z")))
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
      preparedStmt9.setTimestamp(3, Timestamp.from(Instant.parse("2020-04-13T13:10:40Z")))
      preparedStmt9.setString(4, "Delta Lake")
      preparedStmt9.execute
      preparedStmt9.close()

      val result = fetchData.fetchMaxKnolxDeliveredDate
      result.map { date =>
        date shouldBe Timestamp.from(Instant.parse("2020-04-13T14:56:40Z"))
      }
    }
  }
}
