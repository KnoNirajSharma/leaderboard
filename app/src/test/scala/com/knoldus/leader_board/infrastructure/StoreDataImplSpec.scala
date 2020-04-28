package com.knoldus.leader_board.infrastructure

import java.sql.Connection

import com.knoldus.leader_board._
import com.typesafe.config.ConfigFactory
import org.scalatest.{BeforeAndAfterEach, DoNotDiscover}

@DoNotDiscover
class StoreDataImplSpec extends DBSpec with BeforeAndAfterEach {
  val databaseConnection = new DatabaseConnection(ConfigFactory.load())
  implicit val connection: Connection = databaseConnection.connection
  val storeData: StoreData = new StoreDataImpl(databaseConnection)

  override def afterEach(): Unit = {
    cleanUpDatabase(connection)
  }

  override protected def beforeEach(): Unit = {
    cleanUpDatabase(connection)
    createTable(connection)
  }

  "StoreData" should {

    "return number of rows affected when insertion in all_time table" in {
      val blogCount = BlogCount(1, "mukesh01", "Mukesh Gupta", 2)

      val result = storeData.insertAllTimeData(blogCount)
      result shouldBe 1
    }
  }
}
