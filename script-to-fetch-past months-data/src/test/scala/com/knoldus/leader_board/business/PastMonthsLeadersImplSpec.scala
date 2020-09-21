package com.knoldus.leader_board.business

import java.time.Month

import com.knoldus.leader_board.{IndianTime, MonthlyAllTimeReputation}
import org.mockito.MockitoSugar
import org.scalatest.BeforeAndAfterEach
import org.scalatest.wordspec.AnyWordSpecLike

class PastMonthsLeadersImplSpec extends AnyWordSpecLike with MockitoSugar with BeforeAndAfterEach {
  val mockMonthlyLeaders: MonthlyLeaders = mock[MonthlyLeadersImpl]
  val pastMonthsLeaders = new PastMonthsLeadersImpl(mockMonthlyLeaders)

  "PastMonthsLeadersImpl" should {


    "return empty list of monthly leaders if month is less than january 2020" in {

      assert(pastMonthsLeaders.getPastMonthsLeaders(0) == List())
    }
    "return empty list of monthly leaders if month is greater than january 2020 and less than september 2020" in {
      val month = IndianTime.currentTime.minusMonths(1).getMonthValue

      val year = IndianTime.currentTime.minusMonths(1).getYear
      when(mockMonthlyLeaders.getMonthlyAndAllTimeReputation(month, year))
        .thenReturn(List(MonthlyAllTimeReputation(1,"mukesh",300,1,50,1,Month.of(month).toString,2020)))
      assert(pastMonthsLeaders.getPastMonthsLeaders(1) ==List(MonthlyAllTimeReputation(1,"mukesh",300,1,50,1,Month.of(month).toString,2020)))

    }
  }
}
