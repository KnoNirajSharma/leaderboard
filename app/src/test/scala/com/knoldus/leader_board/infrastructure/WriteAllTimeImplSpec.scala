package com.knoldus.leader_board.infrastructure

import java.sql.{Connection, PreparedStatement}

import com.knoldus.leader_board.{BlogCount, DatabaseConnection}
import com.typesafe.config.ConfigFactory
import org.scalatest.{BeforeAndAfterEach, DoNotDiscover}

@DoNotDiscover
class WriteAllTimeImplSpec extends DBSpec with BeforeAndAfterEach {
  val databaseConnection = new DatabaseConnection(ConfigFactory.load())
  implicit val connection: Connection = databaseConnection.connection
  val writeAllTime: WriteAllTime = new WriteAllTimeImpl(databaseConnection)

  override def afterEach(): Unit = {
    cleanUpDatabase(connection)
  }

  override protected def beforeEach(): Unit = {
    cleanUpDatabase(connection)
    createTable(connection)
  }

  "WriteAllTime" should {

    "return number of rows affected when insertion in all_time table" in {
      val blogCount = BlogCount(1, "mukesh01", "Mukesh Gupta", 2)

      val result = writeAllTime.insertAllTimeData(blogCount)
      result shouldBe 1
    }
  }

  "return number of rows affected when updation in all_time table" in {
    val insertAllTimeData1: String =
      """
        |insert into all_time(id, knolder_id, number_of_blogs)
        |values (?,?,?)
""".stripMargin

    val preparedStmt1: PreparedStatement = connection.prepareStatement(insertAllTimeData1)
    preparedStmt1.setInt(1, 1)
    preparedStmt1.setInt(2, 1)
    preparedStmt1.setInt(3, 2)
    preparedStmt1.execute
    preparedStmt1.close()

    val insertAllTimeData2: String =
      """
        |insert into all_time(id, knolder_id, number_of_blogs)
        |values (?,?,?)
""".stripMargin

    val preparedStmt2: PreparedStatement = connection.prepareStatement(insertAllTimeData2)
    preparedStmt2.setInt(1, 2)
    preparedStmt2.setInt(2, 2)
    preparedStmt2.setInt(3, 1)
    preparedStmt2.execute
    preparedStmt2.close()

    val insertAllTimeData3: String =
      """
        |insert into all_time(id, knolder_id, number_of_blogs)
        |values (?,?,?)
""".stripMargin

    val preparedStmt3: PreparedStatement = connection.prepareStatement(insertAllTimeData3)
    preparedStmt3.setInt(1, 3)
    preparedStmt3.setInt(2, 3)
    preparedStmt3.setInt(3, 1)
    preparedStmt3.execute
    preparedStmt3.close()

    val blogCount = BlogCount(1, "mukesh01", "Mukesh Gupta", 3)

    val result = writeAllTime.updateAllTimeData(blogCount)
    result shouldBe 1
  }
}
