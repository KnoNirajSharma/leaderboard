package com.knoldus.leader_board.infrastructure

import java.sql.{Connection, PreparedStatement, Timestamp}

import com.knoldus.leader_board._
import com.typesafe.config.ConfigFactory
import org.scalatest.{BeforeAndAfterEach, DoNotDiscover}

@DoNotDiscover
class StoreWebinarImplSpec extends DBSpec with BeforeAndAfterEach {
  implicit val connection: Connection = DatabaseConnection.connection(ConfigFactory.load())
  val storeData: StoreWebinar = new StoreWebinarImpl(ConfigFactory.load())

  override def afterEach(): Unit = {
    cleanUpDatabase(connection)
  }

  override protected def beforeEach(): Unit = {
    createTable(connection)
  }

  "Store Webinar" should {

    "return number of rows affected when insertion in webinar table" in {
      val insertKnolder1: String =
        """
          |insert into knolder(id, full_name, wordpress_id, email_id, active_status)
          |values (?,?,?,?,?)
""".stripMargin

      val preparedStmt1: PreparedStatement = connection.prepareStatement(insertKnolder1)
      preparedStmt1.setInt(1, 1)
      preparedStmt1.setString(2, "Mukesh Kumar")
      preparedStmt1.setString(3, "mukesh01")
      preparedStmt1.setString(4, "mukesh.kumar@knoldus.com")
      preparedStmt1.setBoolean(5, true)
      preparedStmt1.execute
      preparedStmt1.close()

      val insertKnolder2: String =
        """
          |insert into knolder(id, full_name, wordpress_id, email_id, active_status)
          |values (?,?,?,?,?)
""".stripMargin

      val preparedStmt2: PreparedStatement = connection.prepareStatement(insertKnolder2)
      preparedStmt2.setInt(1, 2)
      preparedStmt2.setString(2, "Abhishek Baranwal")
      preparedStmt2.setString(3, "abhishek02")
      preparedStmt2.setString(4, "abhishek.baranwal@knoldus.com")
      preparedStmt2.setBoolean(5, true)
      preparedStmt2.execute
      preparedStmt2.close()

      val webinar = List(
        Webinar("1",Option(Timestamp.valueOf("1970-01-19 11:49:09.0")),"mukesh","Reactive Microservices","mukesh.kumar@knoldus.com"),
        Webinar("2",Option(Timestamp.valueOf("1970-01-19 15:11:46.0")),"Abhishek","Delta Lake","abhishek.baranwal@knoldus.com"))

      val result = storeData.insertWebinar(webinar)
      result.sum shouldBe 2
    }
  }
}
