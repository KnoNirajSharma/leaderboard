package com.knoldus.leader_board.application

import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.testkit.ScalatestRouteTest
import com.knoldus.leader_board.Reputation
import com.knoldus.leader_board.business.{OverallReputation, OverallReputationImpl}
import com.typesafe.config.ConfigFactory
import net.liftweb.json.Extraction.decompose
import net.liftweb.json.{DefaultFormats, compactRender}
import org.mockito.MockitoSugar
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpecLike

class AllTimeDataOnAPIImplSpec extends AnyWordSpecLike with MockitoSugar with Matchers with ScalatestRouteTest {
  implicit val formats: DefaultFormats.type = net.liftweb.json.DefaultFormats
  val mockOverallReputation: OverallReputation = mock[OverallReputationImpl]
  val allTimeDataOnAPI: AllTimeDataOnAPI = new AllTimeDataOnAPIImpl(mockOverallReputation, ConfigFactory.load())

  val reputationOfKnolders = List(Reputation("Vikas Hazrati", 820, 1),
    Reputation("Himanshu Gupta", 290, 2),
    Reputation("Deepak Mehra", 140, 3))
  when(mockOverallReputation.calculateReputation).thenReturn(reputationOfKnolders)
  val reputation: String = compactRender(decompose(reputationOfKnolders))

  "The service" should {

    "display reputation of knolders to routed path" in {
      val route = {
        path("reputation") {
          get {
            complete(HttpEntity(ContentTypes.`application/json`, reputation))
          }
        }
      }
      Get("/reputation") ~> route ~> check {
        allTimeDataOnAPI.pushAllTimeDataOnAPI.map { pushedReputation =>
          responseAs[String] shouldEqual pushedReputation
        }
      }
    }
  }
}
