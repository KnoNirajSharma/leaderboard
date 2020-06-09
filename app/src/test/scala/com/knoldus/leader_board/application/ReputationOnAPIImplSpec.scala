package com.knoldus.leader_board.application

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.testkit.ScalatestRouteTest
import com.knoldus.leader_board.infrastructure.{FetchKnolderDetails, FetchReputation}
import com.knoldus.leader_board.{Contribution, ContributionDetails, KnolderDetails, Reputation}
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
  val blogTitles = List(ContributionDetails("windows handling using selenium webdriver", "2020-04-13 13:10:40"),
    ContributionDetails("Java 9: Enhance your Jav…ptional API enhancement", "2020-04-13 13:10:40"))
  val blogDetails: Option[Contribution] = Option(Contribution("Blog", 2, 10, blogTitles))
  val contributions = List(blogDetails)
  val knolderDetails: Option[KnolderDetails] = Option(KnolderDetails("Mukesh Gupta", 10, contributions))
  when(mockFetchReputation.fetchReputation).thenReturn(reputations)
  when(mockFetchKnolderDetails.fetchKnolderAllTimeDetails(1)).thenReturn(knolderDetails)
  "The service" should {
    "display reputation of knolders to routed path" in {
      Get("/reputation") ~> reputationOnAPI.reputationRoute ~> check {
        responseAs[String] shouldEqual compactRender(decompose(reputations))
      }
    }
    "display monthly details of knolders to routed path" in {
      when(mockFetchKnolderDetails.fetchKnolderMonthlyDetails(1, 4, 2020)).thenReturn(knolderDetails)

      Get("/reputation/1?month=April&year=2020") ~> reputationOnAPI.monthlyDetailsRoute ~> check {
        responseAs[String] shouldEqual compactRender(decompose(knolderDetails))
      }

    }
    "display monthly details of knolders to routed path with incorrect parameter" in {
      when(mockFetchKnolderDetails.fetchKnolderMonthlyDetails(1, 4, 2)).thenReturn(knolderDetails)

      Get("/reputation/1?month=ap&year=2") ~> reputationOnAPI.monthlyDetailsRoute ~> check {
        status shouldEqual StatusCodes.InternalServerError
      }
    }
    "display all time details of knolders to routed path" in {
      Get("/reputation/1") ~> reputationOnAPI.allTimeDetailsRoute ~> check {
        responseAs[String] shouldEqual compactRender(decompose(knolderDetails))
      }
    }
  }
}
