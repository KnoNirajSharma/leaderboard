package com.knoldus.leader_board.infrastructure

import java.sql.Connection
import java.time.Month

import com.knoldus.leader_board._
import com.knoldus.leader_board.business.{KnolderEachContrbutionScore, PastMonthsContributionImpl, PastMonthsContribution}
import com.typesafe.config.ConfigFactory
import org.mockito.ArgumentMatchersSugar.any
import org.mockito.MockitoSugar
import org.scalatest.{BeforeAndAfterEach, DoNotDiscover}


@DoNotDiscover
class WriteMonthlyContributionImplSpec extends DBSpec with BeforeAndAfterEach with MockitoSugar{
  implicit val connection: Connection = DatabaseConnection.connection(ConfigFactory.load())
  val mockPastMonthsContribution:PastMonthsContribution = mock[PastMonthsContributionImpl]
  val writeMonthlyContribution: WriteMonthlyContribution = new WriteMonthlyContributionImpl(ConfigFactory.load(), mockPastMonthsContribution)

  override def afterEach(): Unit = {
    cleanUpDatabase(connection)
  }

  override protected def beforeEach(): Unit = {
    createTable(connection)
  }

  "WriteMonthlyCOntributionImpl" should {
    val monthValue = IndianTime.currentTime.getMonthValue
    val year = IndianTime.currentTime.getYear
    val reputations = List(KnolderEachContrbutionScore(1,"mukesh",10,20,15,15,30,100,100,50,Month.of(monthValue).toString,year))

    "return number of rows affected when insertion in monthly contribution table" in {
      when(mockPastMonthsContribution.getPastMonthsContributionScores(any))
        .thenReturn(reputations)

      val result = writeMonthlyContribution.insertKnolderMonthlyContribution
      result.sum shouldBe 1
    }
  }
}
