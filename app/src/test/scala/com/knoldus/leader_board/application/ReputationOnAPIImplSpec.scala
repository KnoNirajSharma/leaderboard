package com.knoldus.leader_board.application

import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.testkit.ScalatestRouteTest
import com.knoldus.leader_board.Reputation
import com.knoldus.leader_board.infrastructure.{FetchKnolderDetails, FetchReputation}
import com.typesafe.config.ConfigFactory
import net.liftweb.json.Extraction.decompose
import net.liftweb.json.{DefaultFormats, compactRender}
import org.mockito.MockitoSugar
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpecLike

class ReputationOnAPIImplSpec extends AnyWordSpecLike with MockitoSugar with Matchers with ScalatestRouteTest {
  implicit val formats: DefaultFormats.type = net.liftweb.json.DefaultFormats
  val mockFetchReputation: FetchReputation = mock[FetchReputation]
  val mockFetchKnolderDetails: FetchKnolderDetails = mock[FetchKnolderDetails]
  val reputationOnAPI: ReputationOnAPI = new ReputationOnAPIImpl(mockFetchKnolderDetails, mockFetchReputation,
    ConfigFactory.load())

  val reputations = List(Reputation(1, "Mukesh Gupta", 10, 1, "15-20-20", 10, 1),
    Reputation(2, "Abhishek Baranwal", 5, 2, "10-10-15", 5, 2),
    Reputation(3, "Komal Rajpal", 5, 2, "5-10-5", 5, 2))

  when(mockFetchReputation.fetchReputation).thenReturn(reputations)

  "The service" should {

    "display reputation of knolders to routed path" in {
      val route = {
        path("reputation") {
          get {
            complete(HttpEntity(ContentTypes.`application/json`, compactRender(decompose(reputations))))
          }
        }
      }
      Get("/reputation") ~> route ~> check {
        reputationOnAPI.displayReputationOnAPI.map { displayedReputation =>
          responseAs[String] shouldEqual displayedReputation
        }
      }
    }
  }
}
