package com.knoldus.leader_board.infrastructure

import java.sql.{Connection, PreparedStatement}

import com.knoldus.leader_board.{DatabaseConnection, GetStreak, KnolderStreak}
import com.typesafe.config.ConfigFactory
import org.scalatest.{BeforeAndAfterEach, DoNotDiscover}

@DoNotDiscover
class WriteQuarterlyReputationImplSpec extends DBSpec with BeforeAndAfterEach {
  implicit val connection: Connection = DatabaseConnection.connection(ConfigFactory.load())
  val writeQuarterlyReputation: WriteQuarterlyReputation = new WriteQuarterlyReputationImpl(ConfigFactory.load())

  override def afterEach(): Unit = {
    cleanUpDatabase(connection)
  }

  override def beforeEach(): Unit = {
    createTable(connection)
  }

  "write quarterly reputation" should {

    "return number of rows affected when insertion in quarterly reputation table" in {
      val reputationOfKnolders = List(KnolderStreak(None, GetStreak(1, "Mukesh Gupta", "15-20-20")),
        KnolderStreak(None, GetStreak(2, "anjali", "10-10-15")))

      val result = writeQuarterlyReputation.insertQuarterlyReputationData(reputationOfKnolders)
      result.sum shouldBe 2
    }

    "return number of rows affected when insertion in quarterly reputation table when one entry will not get " +
      "inserted" in {
      val reputationOfKnolders = List(KnolderStreak(None, GetStreak(1, "Mukesh Gupta", "15-20-20")),
        KnolderStreak(Some(2), GetStreak(2, "anjali", "10-10-15")))

      val result = writeQuarterlyReputation.insertQuarterlyReputationData(reputationOfKnolders)
      result.sum shouldBe 1
    }

    "return number of rows affected when updation in quarterly reputation table" in {
      val insertQuarterlyReputationData1: String =
        """
          |insert into quarterly_reputation(id, knolder_id, full_name, streak)
          |values (?,?,?,?)
""".stripMargin

      val preparedStmt1: PreparedStatement = connection.prepareStatement(insertQuarterlyReputationData1)
      preparedStmt1.setInt(1, 1)
      preparedStmt1.setInt(2, 1)
      preparedStmt1.setString(3, "Mukesh Gupta")
      preparedStmt1.setString(4, "15-20-20")
      preparedStmt1.execute
      preparedStmt1.close()

      val insertQuarterlyReputationData2: String =
        """
          |insert into quarterly_reputation(id, knolder_id, full_name, streak)
          |values (?,?,?,?)
""".stripMargin

      val preparedStmt2: PreparedStatement = connection.prepareStatement(insertQuarterlyReputationData2)
      preparedStmt2.setInt(1, 2)
      preparedStmt2.setInt(2, 2)
      preparedStmt2.setString(3, "anjali")
      preparedStmt2.setString(4, "10-10-15")
      preparedStmt2.execute
      preparedStmt2.close()

      val reputationOfKnolders = List(KnolderStreak(Some(1), GetStreak(1, "Mukesh Gupta", "15-20-20")),
        KnolderStreak(Some(2), GetStreak(2, "anjali", "10-10-15")))

      val result = writeQuarterlyReputation.updateQuarterlyReputationData(reputationOfKnolders)
      result.sum shouldBe 2
    }

    "return number of rows affected when updation in quarterly reputation table when one entry will not get updated" in {
      val insertQuarterlyReputationData1: String =
        """
          |insert into quarterly_reputation(id, knolder_id, full_name, streak)
          |values (?,?,?,?)
""".stripMargin

      val preparedStmt1: PreparedStatement = connection.prepareStatement(insertQuarterlyReputationData1)
      preparedStmt1.setInt(1, 1)
      preparedStmt1.setInt(2, 1)
      preparedStmt1.setString(3, "Mukesh Gupta")
      preparedStmt1.setString(4, "15-20-20")
      preparedStmt1.execute
      preparedStmt1.close()

      val insertQuarterlyReputationData2: String =
        """
          |insert into quarterly_reputation(id, knolder_id, full_name, streak)
          |values (?,?,?,?)
""".stripMargin

      val preparedStmt2: PreparedStatement = connection.prepareStatement(insertQuarterlyReputationData2)
      preparedStmt2.setInt(1, 2)
      preparedStmt2.setInt(2, 2)
      preparedStmt2.setString(3, "anjali")
      preparedStmt2.setString(4, "10-10-15")
      preparedStmt2.execute
      preparedStmt2.close()

      val reputationOfKnolders = List(KnolderStreak(Some(1), GetStreak(1, "Mukesh Gupta", "15-20-20")),
        KnolderStreak(None, GetStreak(2, "anjali", "10-10-15")))

      val result = writeQuarterlyReputation.updateQuarterlyReputationData(reputationOfKnolders)
      result.sum shouldBe 1
    }
  }
}
