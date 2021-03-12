package com.knoldus.leader_board.infrastructure

import java.sql.{Connection, PreparedStatement}

import com.knoldus.leader_board.{DatabaseConnection, IndianTime}
import com.typesafe.config.ConfigFactory
import org.mockito.MockitoSugar
import org.scalatest.matchers.should.Matchers
import org.scalatest.{BeforeAndAfterEach, DoNotDiscover}

@DoNotDiscover
class ContributionScoreMultiplierAndSpikeMonthImplSpec extends DBSpec with BeforeAndAfterEach with MockitoSugar with Matchers {
  implicit val connection: Connection = DatabaseConnection.connection(ConfigFactory.load())

  val fetchSpikeMonthAnsScoreMultiplier = new ContributionScoreMultiplierAndSpikeMonthImpl(ConfigFactory.load)

  override def afterEach(): Unit = {
    cleanUpDatabase(connection)
  }

  override protected def beforeEach(): Unit = {
    createTable(connection)
  }

  "ContributionScoreMultiplierAndSpikeMonthImpl" should {

    "return contribution score multiplier and spike month" in {
      val month = IndianTime.currentTime.getMonth.toString
      val year = IndianTime.currentTime.getYear
      val insertDynamicScoring: String =
        """
          |insert into dynamicscoring(blog_score_multiplier,knolx_score_multiplier,webinar_score_multiplier,techhub_score_multiplier
          |,oscontribution_score_multiplier,conference_score_multiplier,researchpaper_score_multiplier,meetup_score_multiplier,book_score_multiplier,month,year)
          |values (?,?,?,?,?,?,?,?,?,?,?)
""".stripMargin

      val preparedStmtOne: PreparedStatement = connection.prepareStatement(insertDynamicScoring)
      preparedStmtOne.setInt(1, 2)
      preparedStmtOne.setInt(2, 2)
      preparedStmtOne.setInt(3, 2)
      preparedStmtOne.setInt(4, 2)
      preparedStmtOne.setInt(5, 2)
      preparedStmtOne.setInt(6, 2)
      preparedStmtOne.setInt(7, 2)
      preparedStmtOne.setInt(8, 2)

      preparedStmtOne.setInt(9, 2)
      preparedStmtOne.setString(10, month)
      preparedStmtOne.setInt(11, year)
      preparedStmtOne.execute
      preparedStmtOne.close()


      val scoreMultiplier = Option(ScoreMultiplier(2, 2, 2, 2, 2, 2, 2, 2, 2, month, year))

      assert(fetchSpikeMonthAnsScoreMultiplier.fetchContributionScoreMultiplierAndSpikeMonthImpl(month,year) == scoreMultiplier)
    }
  }
}
