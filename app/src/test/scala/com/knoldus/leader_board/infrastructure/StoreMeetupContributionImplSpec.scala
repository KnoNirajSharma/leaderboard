package com.knoldus.leader_board.infrastructure

import java.sql.{Connection, PreparedStatement, Timestamp}

import com.knoldus.leader_board._
import com.typesafe.config.ConfigFactory
import org.scalatest.{BeforeAndAfterEach, DoNotDiscover}

@DoNotDiscover
class StoreMeetupContributionImplSpec extends DBSpec with BeforeAndAfterEach {
  implicit val connection: Connection = DatabaseConnection.connection(ConfigFactory.load())
  val storeData: StoreMeetupContribution = new StoreMeetupContributionImpl(ConfigFactory.load())

  override def afterEach(): Unit = {
    cleanUpDatabase(connection)
  }

  override protected def beforeEach(): Unit = {
    createTable(connection)
  }

  "StoreMeetupContributionImpl" should {

    "return number of rows affected when insertion in Meetup contribution table" in {
      val insertKnolderOne: String =
        """
          |insert into knolder(id, full_name, wordpress_id, email_id, active_status)
          |values (?,?,?,?,?)
""".stripMargin

      val preparedStmtOne: PreparedStatement = connection.prepareStatement(insertKnolderOne)
      preparedStmtOne.setInt(1, 1)
      preparedStmtOne.setString(2, "Mukesh Kumar")
      preparedStmtOne.setString(3, "mukesh01")
      preparedStmtOne.setString(4, "mukesh.kumar@knoldus.com")
      preparedStmtOne.setBoolean(5, true)
      preparedStmtOne.execute
      preparedStmtOne.close()

      val insertKnolderTwo: String =
        """
          |insert into knolder(id, full_name, wordpress_id, email_id, active_status)
          |values (?,?,?,?,?)
""".stripMargin

      val preparedStmtTwo: PreparedStatement = connection.prepareStatement(insertKnolderTwo)
      preparedStmtTwo.setInt(1, 2)
      preparedStmtTwo.setString(2, "Abhishek Baranwal")
      preparedStmtTwo.setString(3, "abhishek02")
      preparedStmtTwo.setString(4, "abhishek.baranwal@knoldus.com")
      preparedStmtTwo.setBoolean(5, true)
      preparedStmtTwo.execute
      preparedStmtTwo.close()

      val otherContribution = List(
        OtherContributionDetails("1", "mukesh.kumar@knoldus.com", "mukesh", Option(Timestamp.valueOf("1970-01-19 11:49:09.0")), "Reactive Microservices", "Open Source"),
        OtherContributionDetails("2", "abhishek.baranwal@knoldus.com", "Abhishek", Option(Timestamp.valueOf("1970-01-19 15:11:46.0")), "Delta Lake", "Meetup"))

      val result = storeData.insertMeetupContributionDetails(otherContribution)
      result.sum shouldBe 1
    }
  }
}
