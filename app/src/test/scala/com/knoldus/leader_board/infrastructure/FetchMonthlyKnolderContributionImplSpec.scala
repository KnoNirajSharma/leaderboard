package com.knoldus.leader_board.infrastructure

import java.sql.{Connection, PreparedStatement}

import com.knoldus.leader_board.DatabaseConnection
import com.typesafe.config.ConfigFactory
import org.scalatest.{BeforeAndAfterEach, DoNotDiscover}

@DoNotDiscover
class FetchMonthlyKnolderContributionImplSpec extends DBSpec with BeforeAndAfterEach {
  implicit val connection: Connection = DatabaseConnection.connection(ConfigFactory.load())
  val fetchMonthlyKnolderContribution: FetchMonthlyKnolderContribution = new FetchMonthlyKnolderContributionImpl(ConfigFactory.load())

  override def afterEach(): Unit = {
    cleanUpDatabase(connection)
  }

  override def beforeEach(): Unit = {
    createTable(connection)
  }

  "FetchMonthlyKnolderContributionImpl" should {

    "return knolder id if that knolder monthly contribution exist" in {
      val insertMonthlyContribution: String =
        """
          |insert into monthlycontribution(id, knolder_id,blog_score,knolx_score,webinar_score,techhub_score
          |,oscontribution_score,book_score,conference_score,researchpaper_score,month,year)
          |values (?,?,?,?,?,?,?,?,?,?,?,?)
""".stripMargin

      val preparedStmt: PreparedStatement = connection.prepareStatement(insertMonthlyContribution)
      preparedStmt.setInt(1, 1)
      preparedStmt.setInt(2, 1)
      preparedStmt.setInt(3, 5)
      preparedStmt.setInt(4, 5)
      preparedStmt.setInt(5,10)
      preparedStmt.setInt(6, 10)
      preparedStmt.setInt(7, 20)
      preparedStmt.setInt(8, 20)
      preparedStmt.setInt(9, 20)
      preparedStmt.setInt(10, 20)
      preparedStmt.setString(11, "SEPTEMBER")
      preparedStmt.setInt(12, 2020)


      val result = fetchMonthlyKnolderContribution.fetchKnolderIdIfKnolderMonthlyContributionExist(1,"SEPTEMBER",2020)
      result.map { id =>
        id shouldBe 1
      }
    }
  }
}
