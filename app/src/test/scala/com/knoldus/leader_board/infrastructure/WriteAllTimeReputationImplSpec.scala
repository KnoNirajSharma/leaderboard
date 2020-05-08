package com.knoldus.leader_board.infrastructure

import java.sql.{Connection, PreparedStatement}

import com.knoldus.leader_board.{DatabaseConnection, KnolderReputation, Reputation}
import com.typesafe.config.ConfigFactory
import org.scalatest.{BeforeAndAfterEach, DoNotDiscover}

@DoNotDiscover
class WriteAllTimeReputationImplSpec extends DBSpec with BeforeAndAfterEach {
  implicit val connection: Connection = DatabaseConnection.connection(ConfigFactory.load())
  val writeAllTimeReputation: WriteAllTimeReputation = new WriteAllTimeReputationImpl(ConfigFactory.load())

  override def afterEach(): Unit = {
    cleanUpDatabase(connection)
  }

  override protected def beforeEach(): Unit = {
    cleanUpDatabase(connection)
    createTable(connection)
  }

  "write all time reputation" should {

    "return number of rows affected when insertion in all_time_reputation table" in {
      val reputationOfKnolders = List(KnolderReputation(None, Reputation(1, "Mukesh Gupta", 10, 1)),
        KnolderReputation(None, Reputation(2, "Abhishek Baranwal", 5, 2)),
        KnolderReputation(None, Reputation(3, "Komal Rajpal", 5, 2)))

      val result = writeAllTimeReputation.insertAllTimeReputationData(reputationOfKnolders)
      result.sum shouldBe 3
    }

    "return number of rows affected when insertion in all_time_reputation table when one entry will not get " +
      "inserted" in {
      val reputationOfKnolders = List(KnolderReputation(None, Reputation(1, "Mukesh Gupta", 10, 1)),
        KnolderReputation(None, Reputation(2, "Abhishek Baranwal", 5, 2)),
        KnolderReputation(Option(3), Reputation(3, "Komal Rajpal", 5, 2)))

      val result = writeAllTimeReputation.insertAllTimeReputationData(reputationOfKnolders)
      result.sum shouldBe 2
    }

    "return number of rows affected when updation in all_time_reputation table" in {
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

      val reputationOfKnolders = List(KnolderReputation(Option(1), Reputation(1, "Mukesh Gupta", 10, 1)),
        KnolderReputation(Option(2), Reputation(2, "Abhishek Baranwal", 5, 2)),
        KnolderReputation(Option(3), Reputation(3, "Komal Rajpal", 5, 2)))

      val result = writeAllTimeReputation.updateAllTimeReputationData(reputationOfKnolders)
      result.sum shouldBe 3
    }

    "return number of rows affected when updation in all_time table when one entry will not get updated" in {
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

      val reputationOfKnolders = List(KnolderReputation(Option(1), Reputation(1, "Mukesh Gupta", 10, 1)),
        KnolderReputation(Option(2), Reputation(2, "Abhishek Baranwal", 5, 2)),
        KnolderReputation(None, Reputation(3, "Komal Rajpal", 5, 2)))

      val result = writeAllTimeReputation.updateAllTimeReputationData(reputationOfKnolders)
      result.sum shouldBe 2
    }
  }
}
