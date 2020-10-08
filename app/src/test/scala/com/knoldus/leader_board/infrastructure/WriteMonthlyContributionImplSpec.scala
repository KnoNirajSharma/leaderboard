package com.knoldus.leader_board.infrastructure

import java.sql.{Connection, PreparedStatement}

import com.knoldus.leader_board.DatabaseConnection
import com.knoldus.leader_board.business.{KnolderEachContrbutionScore, KnolderIdWithKnolderContributionScore}
import com.typesafe.config.ConfigFactory
import org.scalatest.{BeforeAndAfterEach, DoNotDiscover}

@DoNotDiscover
class WriteMonthlyContributionImplSpec extends DBSpec with BeforeAndAfterEach {
  implicit val connection: Connection = DatabaseConnection.connection(ConfigFactory.load())
  val writeMonthlyContribution: WriteMonthlyContribution = new WriteMonthlyContributionImpl(ConfigFactory.load())

  override def afterEach(): Unit = {
    cleanUpDatabase(connection)
  }

  override def beforeEach(): Unit = {
    createTable(connection)
  }

  "WriteMonthlyContributionImpl" should {

    "return number of rows affected when insertion in monthly contribution table" in {
      val contributionOfKnolders = List(KnolderIdWithKnolderContributionScore(None, KnolderEachContrbutionScore(1, "Mukesh Gupta", 10, 10,10,10
      ,10,10,10,10,"SEPTEMBER",2020)),
        KnolderIdWithKnolderContributionScore(None, KnolderEachContrbutionScore(2, "Abhishek Baranwal", 10, 10,10,10
          ,10,10,10,10,"SEPTEMBER",2020)),
        KnolderIdWithKnolderContributionScore(None, KnolderEachContrbutionScore(3, "Komal Rajpal", 10, 10,10,10
          ,10,10,10,10,"SEPTEMBER",2020)))

      val result = writeMonthlyContribution.insertKnolderMonthlyContribution(contributionOfKnolders)
      result.sum shouldBe 3
    }

    "return number of rows affected when insertion in monthly contribution table when two entry will not get " +
      "inserted" in {
      val contributionOfKnolders = List(KnolderIdWithKnolderContributionScore(Option(1), KnolderEachContrbutionScore(1, "Mukesh Gupta", 10, 10,10,10
        ,10,10,10,10,"SEPTEMBER",2020)),
        KnolderIdWithKnolderContributionScore(None, KnolderEachContrbutionScore(2, "Abhishek Baranwal", 10, 10,10,10
          ,10,10,10,10,"SEPTEMBER",2020)),
        KnolderIdWithKnolderContributionScore(None, KnolderEachContrbutionScore(3, "Komal Rajpal", 10, 10,10,10
          ,10,10,10,10,"SEPTEMBER",2020)))

      val result = writeMonthlyContribution.insertKnolderMonthlyContribution(contributionOfKnolders)
      result.sum shouldBe 2
    }

    "return number of rows affected when updation in monthly contribution table" in {
      val contributionOfKnolders = List(KnolderIdWithKnolderContributionScore(Option(1), KnolderEachContrbutionScore(1, "Mukesh Gupta", 10, 10,10,10
        ,10,10,10,10,"SEPTEMBER",2020)),
        KnolderIdWithKnolderContributionScore(None, KnolderEachContrbutionScore(2, "Abhishek Baranwal", 10, 10,10,10
          ,10,10,10,10,"SEPTEMBER",2020)),
        KnolderIdWithKnolderContributionScore(None, KnolderEachContrbutionScore(3, "Komal Rajpal", 10, 10,10,10
          ,10,10,10,10,"SEPTEMBER",2020)))

      val result = writeMonthlyContribution.updateKnolderMonthlyContribution(contributionOfKnolders)
      result.sum shouldBe 0

    }

    "return number of rows affected when one entry is updated in monthly contribution  table " in {
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
      preparedStmt.execute
      preparedStmt.close()

      val contributionOfKnolders = List(KnolderIdWithKnolderContributionScore(Option(1), KnolderEachContrbutionScore(1, "Mukesh Gupta", 10, 10,10,10
        ,10,10,10,10,"SEPTEMBER",2020)),
        KnolderIdWithKnolderContributionScore(Option(2), KnolderEachContrbutionScore(2, "Abhishek Baranwal", 10, 10,10,10
          ,10,10,10,10,"SEPTEMBER",2020)),
        KnolderIdWithKnolderContributionScore(Option(3), KnolderEachContrbutionScore(3, "Komal Rajpal", 10, 10,10,10
          ,10,10,10,10,"SEPTEMBER",2020)))

      val result = writeMonthlyContribution.updateKnolderMonthlyContribution(contributionOfKnolders)
      result.sum shouldBe 1
    }
  }
}
