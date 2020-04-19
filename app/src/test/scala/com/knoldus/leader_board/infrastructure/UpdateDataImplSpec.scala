package com.knoldus.leader_board.infrastructure

import java.sql.{Connection, PreparedStatement}

import com.knoldus.leader_board.business.OverallRankImpl
import com.knoldus.leader_board.{AuthorScore, DatabaseConnection, GetRank}
import com.typesafe.config.ConfigFactory
import org.mockito.MockitoSugar
import org.scalatest.{BeforeAndAfterEach, DoNotDiscover}

@DoNotDiscover
class UpdateDataImplSpec extends DBSpec with BeforeAndAfterEach with MockitoSugar {
  val databaseConnection = new DatabaseConnection(ConfigFactory.load())
  implicit val connection: Connection = databaseConnection.connection
  val mockOverallRank: OverallRankImpl = mock[OverallRankImpl]
  val updateData: UpdateData = new UpdateDataImpl(mockOverallRank)

  override def afterEach(): Unit = {
    cleanUpDatabase(connection)
  }

  override protected def beforeEach(): Unit = {
    cleanUpDatabase(connection)
    createTable(connection)
  }

  "UpdateData" should {

    "return number of rows affected in updation of ranks" in {
      val insertAllTimeData1: String =
        """
          |insert into all_time(id, knolder_id, overall_score, overall_rank)
          |values (?,?,?,?)
""".stripMargin
      val preparedStmt5: PreparedStatement = connection.prepareStatement(insertAllTimeData1)
      preparedStmt5.setInt(1, 1)
      preparedStmt5.setInt(2, 1)
      preparedStmt5.setInt(3, 10)
      preparedStmt5.setInt(4, 0)
      preparedStmt5.execute
      preparedStmt5.close()

      val insertAllTimeData2: String =
        """
          |insert into all_time(id, knolder_id, overall_score, overall_rank)
          |values (?,?,?,?)
""".stripMargin
      val preparedStmt6: PreparedStatement = connection.prepareStatement(insertAllTimeData2)
      preparedStmt6.setInt(1, 2)
      preparedStmt6.setInt(2, 2)
      preparedStmt6.setInt(3, 5)
      preparedStmt6.setInt(4, 0)
      preparedStmt6.execute
      preparedStmt6.close()

      val insertAllTimeData3: String =
        """
          |insert into all_time(id, knolder_id, overall_score, overall_rank)
          |values (?,?,?,?)
""".stripMargin
      val preparedStmt7: PreparedStatement = connection.prepareStatement(insertAllTimeData3)
      preparedStmt7.setInt(1, 3)
      preparedStmt7.setInt(2, 3)
      preparedStmt7.setInt(3, 5)
      preparedStmt7.setInt(4, 0)
      preparedStmt7.execute
      preparedStmt7.close()

      when(mockOverallRank.calculateRank)
        .thenReturn(Vector(GetRank(1, 1), GetRank(2, 2), GetRank(3, 2)))
      val result = updateData.updateRank()
      result.sum shouldBe 3
    }
  }

  "return number of rows of affected in updation of scores" in {
    val insertAllTimeData1: String =
      """
        |insert into all_time(id, knolder_id, overall_score, overall_rank)
        |values (?,?,?,?)
""".stripMargin
    val preparedStmt5: PreparedStatement = connection.prepareStatement(insertAllTimeData1)
    preparedStmt5.setInt(1, 1)
    preparedStmt5.setInt(2, 1)
    preparedStmt5.setInt(3, 10)
    preparedStmt5.setInt(4, 0)
    preparedStmt5.execute
    preparedStmt5.close()

    val insertAllTimeData2: String =
      """
        |insert into all_time(id, knolder_id, overall_score, overall_rank)
        |values (?,?,?,?)
""".stripMargin
    val preparedStmt6: PreparedStatement = connection.prepareStatement(insertAllTimeData2)
    preparedStmt6.setInt(1, 2)
    preparedStmt6.setInt(2, 2)
    preparedStmt6.setInt(3, 5)
    preparedStmt6.setInt(4, 0)
    preparedStmt6.execute
    preparedStmt6.close()

    val insertAllTimeData3: String =
      """
        |insert into all_time(id, knolder_id, overall_score, overall_rank)
        |values (?,?,?,?)
""".stripMargin
    val preparedStmt7: PreparedStatement = connection.prepareStatement(insertAllTimeData3)
    preparedStmt7.setInt(1, 3)
    preparedStmt7.setInt(2, 3)
    preparedStmt7.setInt(3, 5)
    preparedStmt7.setInt(4, 0)
    preparedStmt7.execute
    preparedStmt7.close()

    val result = updateData.updateAllTimeData(AuthorScore(Option("mukesh01"), Option("Mukesh Kumar"), 15, 0),
      Option(1))
    result shouldBe 1
  }
}
