package com.knoldus.leader_board.infrastructure

import java.sql.Connection

import com.knoldus.leader_board._
import com.knoldus.leader_board.business.{MonthlyLeaders, MonthlyLeadersImpl, PastMonthsLeaders, PastMonthsLeadersImpl}
import com.typesafe.config.ConfigFactory
import org.mockito.ArgumentMatchersSugar.any
import org.mockito.MockitoSugar
import org.scalatest.{BeforeAndAfterEach, DoNotDiscover}


@DoNotDiscover
class StoreTopFiveLeadersImplSpec extends DBSpec with BeforeAndAfterEach with MockitoSugar{
  implicit val connection: Connection = DatabaseConnection.connection(ConfigFactory.load())
  val mockMonthlyLeaders:PastMonthsLeaders = mock[PastMonthsLeadersImpl]
  val storeTopFiveLeaders: StoreTopFiveLeaders = new StoreTopFiveLeadersImpl(ConfigFactory.load(), mockMonthlyLeaders)

  override def afterEach(): Unit = {
    cleanUpDatabase(connection)
  }

  override protected def beforeEach(): Unit = {
    createTable(connection)
  }

  "StoreTopFiveLeadersImpl" should {
    val reputations = List(MonthlyAllTimeReputation(1, "Mukesh Gupta", 30, 1, 20, 1 ,"AUGUST",2020),
      MonthlyAllTimeReputation(2, "Abhishek Baranwal", 25, 2, 10, 2,"AUGUST",2020), MonthlyAllTimeReputation(3, "Mukesh Gupta", 20, 3, 10, 2,"AUGUST",2020),
      MonthlyAllTimeReputation(4, "Mukesh Gupta", 10, 4, 10, 2,"AUGUST",2020), MonthlyAllTimeReputation(5, "Mukesh Gupta", 5, 5, 5, 3,"AUGUST",2020))

    "return number of rows affected when insertion in hall of fame table" in {
      when(mockMonthlyLeaders.getPastMonthsLeaders(any))
        .thenReturn(reputations)

      val result = storeTopFiveLeaders.insertTopFiveLeaders
      result.sum shouldBe 5
    }
  }
}
