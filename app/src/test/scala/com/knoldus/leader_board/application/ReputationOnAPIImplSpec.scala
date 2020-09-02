package com.knoldus.leader_board.application

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.testkit.ScalatestRouteTest
import com.knoldus.leader_board._
import com.knoldus.leader_board.business.TwelveMonthsContribution
import com.knoldus.leader_board.infrastructure.{FetchCountWithReputation, FetchKnolderContributionDetails}
import com.typesafe.config.ConfigFactory
import net.liftweb.json.Extraction.decompose
import net.liftweb.json.{DefaultFormats, compactRender}
import org.mockito.MockitoSugar
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpecLike

class ReputationOnAPIImplSpec extends AnyWordSpecLike with MockitoSugar with Matchers with ScalatestRouteTest {
  implicit val formats: DefaultFormats.type = net.liftweb.json.DefaultFormats
  val mockFetchReputation: FetchCountWithReputation = mock[FetchCountWithReputation]
  val mockFetchKnolderDetails: FetchKnolderContributionDetails = mock[FetchKnolderContributionDetails]
  val mockTwelveMonthsDetails = mock[TwelveMonthsContribution]
  val reputationOnAPI: ReputationOnAPI = new ReputationOnAPIImpl(mockTwelveMonthsDetails, mockFetchKnolderDetails, mockFetchReputation,
    ConfigFactory.load())
  val reputations = List(Reputation(1, "Mukesh Gupta", 10, 1, "15-20-20", 10, 1),
    Reputation(2, "Abhishek Baranwal", 5, 2, "10-10-15", 5, 2),
    Reputation(3, "Komal Rajpal", 5, 2, "5-10-5", 5, 2))
  val reputationWithCount = Option(ReputationWithCount(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, reputations))
  val blogTitles = List(ContributionDetails("windows handling using selenium webdriver", "2020-04-13 13:10:40"),
    ContributionDetails("Java 9: Enhance your Jav…ptional API enhancement", "2020-04-13 13:10:40"))
  val blogDetails: Option[Contribution] = Option(Contribution("Blog", 2, 10, blogTitles))
  val contributions = List(blogDetails)
  val knolderDetails: Option[KnolderDetails] = Option(KnolderDetails("Mukesh Gupta", 10, contributions))
  val twelveMonthDetails = Option(List(TwelveMonthsScore("JUNE", 2020, 30, 20, 40, 10, 15, 100)))
  when(mockFetchReputation.allTimeAndMonthlyContributionCountWithReputation).thenReturn(Option(ReputationWithCount(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, reputations)))
  "The service" should {
    "display reputation of knolders to routed path" in {
      Get("/reputation") ~> reputationOnAPI.reputationRoute ~> check {
        responseAs[String] shouldEqual compactRender(decompose(reputationWithCount))
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

    "display monthly details of non existing knolder to routed path" in {
      when(mockFetchKnolderDetails.fetchKnolderMonthlyDetails(0, 4, 2020)).thenReturn(None)
      Get("/reputation/0?month=April&year=2020") ~> reputationOnAPI.monthlyDetailsRoute ~> check {
        status shouldEqual StatusCodes.NotFound
      }
    }
    "display all time details of knolders to routed path" in {
      when(mockFetchKnolderDetails.fetchKnolderAllTimeDetails(1)).thenReturn(knolderDetails)
      Get("/reputation/1") ~> reputationOnAPI.allTimeDetailsRoute ~> check {
        responseAs[String] shouldEqual compactRender(decompose(knolderDetails))
      }
    }
    "display all time details of knolder of non existing knolder to routed path" in {
      when(mockFetchKnolderDetails.fetchKnolderAllTimeDetails(0)).thenReturn(None)
      Get("/reputation/0") ~> reputationOnAPI.allTimeDetailsRoute ~> check {
        status shouldEqual StatusCodes.NotFound
      }
    }
    "display all time details of knolder  with  garbage knolder id to routed path" in {
      Get("/reputation/a") ~> Route.seal(reputationOnAPI.allTimeDetailsRoute) ~> check {
        status shouldEqual StatusCodes.NotFound
      }
    }
    "display twelve months details details of knolder  with knolder id to routed path" in {
      when(mockTwelveMonthsDetails.lastTwelveMonthsScore(1, 1)).thenReturn(twelveMonthDetails)

      Get("/reputation/twelvemonths/1") ~> Route.seal(reputationOnAPI.twelveMonthsRoute) ~> check {
        responseAs[String] shouldEqual compactRender(decompose(twelveMonthDetails))
      }

    }
    "display twelve months details of knolder  of non existing knolder id id to routed path" in {
      when(mockTwelveMonthsDetails.lastTwelveMonthsScore(0, 1)).thenReturn(None)

      Get("/reputation/twelvemonths/0") ~> Route.seal(reputationOnAPI.twelveMonthsRoute) ~> check {
        status shouldEqual StatusCodes.NotFound
      }
    }
  }
}
