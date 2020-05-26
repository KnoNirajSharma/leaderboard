package com.knoldus.leader_board.application

import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.testkit.ScalatestRouteTest
import com.knoldus.leader_board.GetReputation
import com.knoldus.leader_board.infrastructure.{ReadAllTimeReputation, ReadMonthlyReputation}
import com.typesafe.config.ConfigFactory
import net.liftweb.json.Extraction.decompose
import net.liftweb.json.{DefaultFormats, compactRender}
import org.mockito.MockitoSugar
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpecLike

class ReputationOnAPIImplSpec extends AnyWordSpecLike with MockitoSugar with Matchers with ScalatestRouteTest {
  implicit val formats: DefaultFormats.type = net.liftweb.json.DefaultFormats
  val mockReadAllTimeReputation: ReadAllTimeReputation = mock[ReadAllTimeReputation]
  val mockReadMonthlyReputation: ReadMonthlyReputation = mock[ReadMonthlyReputation]
  val reputationOnAPI: ReputationOnAPI = new ReputationOnAPIImpl(mockReadAllTimeReputation, mockReadMonthlyReputation,
    ConfigFactory.load())

  val reputationOfKnolders = List(GetReputation("Vikas Hazrati", 820, 1),
    GetReputation("Himanshu Gupta", 290, 2),
    GetReputation("Deepak Mehra", 140, 3))

  when(mockReadAllTimeReputation.fetchAllTimeReputationData).thenReturn(reputationOfKnolders)
  when(mockReadAllTimeReputation.fetchAllTimeReputationData).thenReturn(reputationOfKnolders)

  "The service" should {

    "display all time reputation of knolders to routed path" in {
      val routeForAllTimeData = {
        path("reputation") {
          get {
            complete(HttpEntity(ContentTypes.`application/json`, compactRender(decompose(reputationOfKnolders))))
          }
        }
      }
      Get("/reputation") ~> routeForAllTimeData ~> check {
        reputationOnAPI.displayReputationOnAPI.map { displayedReputation =>
          responseAs[String] shouldEqual displayedReputation
        }
      }
    }

    "display monthly reputation of knolders to routed path" in {
      val routeForMonthlyData = {
        path("reputation"/"monthly") {
          get {
            complete(HttpEntity(ContentTypes.`application/json`, compactRender(decompose(reputationOfKnolders))))
          }
        }
      }
      Get("/reputation/monthly") ~> routeForMonthlyData ~> check {
        reputationOnAPI.displayReputationOnAPI.map { displayedReputation =>
          responseAs[String] shouldEqual displayedReputation
        }
      }
    }
  }
}
