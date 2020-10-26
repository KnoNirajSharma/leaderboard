package com.knoldus.leader_board.infrastructure

import java.sql.{Connection, PreparedStatement}

import com.knoldus.leader_board.{DatabaseConnection, MonthYearWithTopFiveLeaders, MonthlyTopFiveLeaders}
import com.typesafe.config.ConfigFactory
import org.mockito.MockitoSugar
import org.scalatest.matchers.should.Matchers
import org.scalatest.{BeforeAndAfterEach, DoNotDiscover}

@DoNotDiscover
class FetchMonthlyTopFiveLeadersImplSpec extends DBSpec with BeforeAndAfterEach with MockitoSugar with Matchers {
  implicit val connection: Connection = DatabaseConnection.connection(ConfigFactory.load())
  val fetchMonthlyTopFiveLeadersObj = new FetchMonthlyTopFiveLeadersImpl(ConfigFactory.load())


  override def afterEach(): Unit = {
    cleanUpDatabase(connection)
  }

  override def beforeEach(): Unit = {
    createTable(connection)
  }

  "FetchMonthlyTopFiveLeadersImpl" should {

    "return top five leaders of every month" in {


      val insertLeader: String =
        """
          |insert into halloffame(id, knolder_id, knolder_name,monthly_score,monthly_rank,all_time_score,all_time_rank,month,year)
          |
          |values (?,?,?,?,?,?,?,?,?)
""".stripMargin

      val preparedStmtOne: PreparedStatement = connection.prepareStatement(insertLeader)
      preparedStmtOne.setInt(1, 1)
      preparedStmtOne.setInt(2, 1)
      preparedStmtOne.setString(3, "Mukesh Gupta")
      preparedStmtOne.setInt(4, 30)
      preparedStmtOne.setInt(5, 2)
      preparedStmtOne.setInt(6, 100)
      preparedStmtOne.setInt(7, 1)
      preparedStmtOne.setString(8, "SEPTEMBER")
      preparedStmtOne.setInt(9, 2020)
      preparedStmtOne.execute
      preparedStmtOne.close()

      val preparedStmtTwo: PreparedStatement = connection.prepareStatement(insertLeader)
      preparedStmtTwo.setInt(1, 2)
      preparedStmtTwo.setInt(2, 2)
      preparedStmtTwo.setString(3, "Akash Gupta")
      preparedStmtTwo.setInt(4, 50)
      preparedStmtTwo.setInt(5, 1)
      preparedStmtTwo.setInt(6, 80)
      preparedStmtTwo.setInt(7, 2)
      preparedStmtTwo.setString(8, "SEPTEMBER")
      preparedStmtTwo.setInt(9, 2020)
      preparedStmtTwo.execute
      preparedStmtTwo.close()

      val preparedStmtThree: PreparedStatement = connection.prepareStatement(insertLeader)
      preparedStmtThree.setInt(1, 3)
      preparedStmtThree.setInt(2, 3)
      preparedStmtThree.setString(3, "Amit Gupta")
      preparedStmtThree.setInt(4, 20)
      preparedStmtThree.setInt(5, 3)
      preparedStmtThree.setInt(6, 60)
      preparedStmtThree.setInt(7, 3)
      preparedStmtThree.setString(8, "SEPTEMBER")
      preparedStmtThree.setInt(9, 2020)
      preparedStmtThree.execute
      preparedStmtThree.close()

      val preparedStmtFour: PreparedStatement = connection.prepareStatement(insertLeader)
      preparedStmtFour.setInt(1, 4)
      preparedStmtFour.setInt(2, 4)
      preparedStmtFour.setString(3, "Amit Gupta")
      preparedStmtFour.setInt(4, 15)
      preparedStmtFour.setInt(5, 4)
      preparedStmtFour.setInt(6, 40)
      preparedStmtFour.setInt(7, 4)
      preparedStmtFour.setString(8, "SEPTEMBER")
      preparedStmtFour.setInt(9, 2020)
      preparedStmtFour.execute
      preparedStmtFour.close()

      val preparedStmtFive: PreparedStatement = connection.prepareStatement(insertLeader)
      preparedStmtFive.setInt(1, 5)
      preparedStmtFive.setInt(2, 5)
      preparedStmtFive.setString(3, "Amit Gupta")
      preparedStmtFive.setInt(4, 10)
      preparedStmtFive.setInt(5, 5)
      preparedStmtFive.setInt(6, 30)
      preparedStmtFive.setInt(7, 5)
      preparedStmtFive.setString(8, "SEPTEMBER")
      preparedStmtFive.setInt(9, 2020)
      preparedStmtFive.execute
      preparedStmtFive.close()


      val listOfMonthlyLeaders = List(MonthYearWithTopFiveLeaders("SEPTEMBER" ,2020,List(MonthlyTopFiveLeaders
      ("SEPTEMBER" ,2020,2,"Akash Gupta",50,1,80,2),
        MonthlyTopFiveLeaders("SEPTEMBER" ,2020,1,"Mukesh Gupta",30,2,100,1),MonthlyTopFiveLeaders
        ("SEPTEMBER" ,2020,3,"Amit Gupta",20,3,60,3),MonthlyTopFiveLeaders
        ("SEPTEMBER" ,2020,4,"Amit Gupta",15,4,40,4),MonthlyTopFiveLeaders
        ("SEPTEMBER" ,2020,5,"Amit Gupta",10,5,30,5))))


      assert(fetchMonthlyTopFiveLeadersObj.fetchMonthlyTopFiveLeaders == listOfMonthlyLeaders)
    }
  }
}
