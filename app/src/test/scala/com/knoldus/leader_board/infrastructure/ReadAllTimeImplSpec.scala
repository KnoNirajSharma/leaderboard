package com.knoldus.leader_board.infrastructure

import java.sql.{Connection, PreparedStatement}

import com.knoldus.leader_board.{DatabaseConnection, GetAllTime}
import com.typesafe.config.ConfigFactory
import org.scalatest.{BeforeAndAfterEach, DoNotDiscover}

@DoNotDiscover
class ReadAllTimeImplSpec extends DBSpec with BeforeAndAfterEach {
  implicit val connection: Connection = DatabaseConnection.connection(ConfigFactory.load())
  val readAllTime: ReadAllTime = new ReadAllTimeImpl(ConfigFactory.load())

  override def afterEach(): Unit = {
    cleanUpDatabase(connection)
  }

  override protected def beforeEach(): Unit = {
    cleanUpDatabase(connection)
    createTable(connection)
  }

  "ReadAllTime" should {

    "return all time data of knolders" in {
      val insertKnolder1: String =
        """
          |insert into knolder(id, full_name, wordpress_id, email_id)
          |values (?,?,?,?)
""".stripMargin

      val preparedStmt1: PreparedStatement = connection.prepareStatement(insertKnolder1)
      preparedStmt1.setInt(1, 1)
      preparedStmt1.setString(2, "Mukesh Kumar")
      preparedStmt1.setString(3, "mukesh01")
      preparedStmt1.setString(4, "mukesh.kumar@knoldus.com")
      preparedStmt1.execute
      preparedStmt1.close()

      val insertKnolder2: String =
        """
          |insert into knolder(id, full_name, wordpress_id, email_id)
          |values (?,?,?,?)
""".stripMargin

      val preparedStmt2: PreparedStatement = connection.prepareStatement(insertKnolder2)
      preparedStmt2.setInt(1, 2)
      preparedStmt2.setString(2, "Abhishek Baranwal")
      preparedStmt2.setString(3, "abhishek02")
      preparedStmt2.setString(4, "abhishek.baranwal@knoldus.com")
      preparedStmt2.execute
      preparedStmt2.close()

      val insertKnolder3: String =
        """
          |insert into knolder(id, full_name, wordpress_id, email_id)
          |values (?,?,?,?)
""".stripMargin

      val preparedStmt3: PreparedStatement = connection.prepareStatement(insertKnolder3)
      preparedStmt3.setInt(1, 3)
      preparedStmt3.setString(2, "Komal Rajpal")
      preparedStmt3.setString(3, "komal03")
      preparedStmt3.setString(4, "komal.rajpal@knoldus.com")
      preparedStmt3.execute
      preparedStmt3.close()

      val insertAllTimeData1: String =
        """
          |insert into all_time(id, knolder_id, number_of_blogs)
          |values (?,?,?)
""".stripMargin

      val preparedStmt4: PreparedStatement = connection.prepareStatement(insertAllTimeData1)
      preparedStmt4.setInt(1, 1)
      preparedStmt4.setInt(2, 1)
      preparedStmt4.setInt(3, 2)
      preparedStmt4.execute
      preparedStmt4.close()

      val insertAllTimeData2: String =
        """
          |insert into all_time(id, knolder_id, number_of_blogs)
          |values (?,?,?)
""".stripMargin

      val preparedStmt5: PreparedStatement = connection.prepareStatement(insertAllTimeData2)
      preparedStmt5.setInt(1, 2)
      preparedStmt5.setInt(2, 2)
      preparedStmt5.setInt(3, 1)
      preparedStmt5.execute
      preparedStmt5.close()

      val insertAllTimeData3: String =
        """
          |insert into all_time(id, knolder_id, number_of_blogs)
          |values (?,?,?)
""".stripMargin

      val preparedStmt6: PreparedStatement = connection.prepareStatement(insertAllTimeData3)
      preparedStmt6.setInt(1, 3)
      preparedStmt6.setInt(2, 3)
      preparedStmt6.setInt(3, 1)
      preparedStmt6.execute
      preparedStmt6.close()

      val allTimeData = List(GetAllTime("Mukesh Kumar", Option(2)),
        GetAllTime("Abhishek Baranwal", Option(1)),
        GetAllTime("Komal Rajpal", Option(1)))
      val result = readAllTime.fetchAllTimeData
      result shouldBe allTimeData
    }

    "return knolder id from all_time table" in {
      val insertAllTimeData: String =
        """
          |insert into all_time(id, knolder_id, number_of_blogs)
          |values (?,?,?)
""".stripMargin

      val preparedStmt: PreparedStatement = connection.prepareStatement(insertAllTimeData)
      preparedStmt.setInt(1, 1)
      preparedStmt.setInt(2, 1)
      preparedStmt.setInt(3, 2)
      preparedStmt.execute
      preparedStmt.close()

      val result = readAllTime.fetchKnolderIdFromAllTime(1)
      result.map { id =>
        id shouldBe 1
      }
    }
  }
}
