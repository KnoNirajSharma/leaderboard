package com.knoldus.leader_board.business

import java.sql.{Connection, Timestamp}
import java.time.Instant

import com.knoldus.leader_board.infrastructure.{FetchTechHub, FetchTechHubImpl}
import com.knoldus.leader_board.{DatabaseConnection, TechHub}
import com.typesafe.config.ConfigFactory
import org.mockito.ArgumentMatchersSugar.any
import org.mockito.MockitoSugar
import org.scalatest.BeforeAndAfterEach
import org.scalatest.wordspec.AnyWordSpecLike

class TechHubDataImplSpec extends AnyWordSpecLike with MockitoSugar with BeforeAndAfterEach {
  implicit val connection: Connection = DatabaseConnection.connection(ConfigFactory.load())
  val mockFetchTechhub: FetchTechHub = mock[FetchTechHubImpl]
  val mockURLResponse: URLResponse = mock[URLResponse]
  val techHub: TechHubData= new TechHubDataImpl(mockFetchTechhub, mockURLResponse, ConfigFactory.load())

  "techHub" should {

    val techHubData: String =
      """{
        |	"data": {
        |		"leaderShipBoardTemplates": [{
        |			"_id": "ab3c6981-9964-46e2-adcd-64154120c1dc",
        |			"author": "mukesh",
        |			"author_mail": "mukesh.kumar@knoldus.com",
        |			"downloads": 0,
        |			"techHubUrl": "https://techhub.knoldus.com/dashboard/projects/java/5f148594c48df018f3c8b40d",
        |			"title": "Reactive Microservices",
        |			"uploadedDate": "1970-01-19T11:49:09.000+05:30[Asia/Kolkata]"
        |		}, {
        |			"_id": "4cb67c8f-941b-4860-ba4e-a7e7f497768d",
        |			"author": "mukesh",
        |			"author_mail": "mukesh.kumar@knoldus.com",
        |			"downloads": 0,
        |			"techHubUrl": "https://techhub.knoldus.com/dashboard/projects/java/5f1487458ad6f41edfc2a981",
        |			"title": "Delta Lake",
        |			"uploadedDate": "1970-01-19T15:11:46.000+05:30[Asia/Kolkata]"
        |		}]
        |	},
        |	"resource": "GET_DETAILS_LEADERSHIP_BOARD",
        |	"status": "success",
        |	"token": ""
        |}""".stripMargin

    "return latest knolx from API" in {
      when(mockFetchTechhub.fetchMaxTechHubUploadedDate)
        .thenReturn(Option(Timestamp.from(Instant.parse("2019-01-19T11:49:09Z"))))

      when(mockURLResponse.getTechHubResponse(any,any, any))
        .thenReturn(techHubData)

      val listOfTechHub = List(
        TechHub(
          Option("ab3c6981-9964-46e2-adcd-64154120c1dc"),
          Option("mukesh.kumar@knoldus.com"),
          Option(Timestamp.valueOf("1970-01-19 11:49:09.0")),
          Option("Reactive Microservices")),
        TechHub(
          Option("4cb67c8f-941b-4860-ba4e-a7e7f497768d"),
          Option("mukesh.kumar@knoldus.com"),
          Option(Timestamp.valueOf("1970-01-19 15:11:46.0")),
          Option("Delta Lake"))
      )

      assert(techHub.getLatestTechHubFromAPI== listOfTechHub)
    }

    "return empty list when an invalid response receive from api" in {
      when(mockFetchTechhub.fetchMaxTechHubUploadedDate)
        .thenReturn(Option(Timestamp.from(Instant.parse("2019-01-19T11:49:09Z"))))

      when(mockURLResponse.getTechHubResponse(any,any, any))
        .thenReturn("""[]""")


      assert(techHub.getLatestTechHubFromAPI == List())
    }

    "return list of latest knolx" in {
      val listOfTechHub= List(
        TechHub(
          Option("ab3c6981-9964-46e2-adcd-64154120c1dc"),
          Option("mukesh.kumar@knoldus.com"),
          Option(Timestamp.valueOf("1970-01-19 11:49:09.0")),
          Option("Reactive Microservices")),
        TechHub(
          Option("4cb67c8f-941b-4860-ba4e-a7e7f497768d"),
          Option("mukesh.kumar@knoldus.com"),
          Option(Timestamp.valueOf("1970-01-19 15:11:46.0")),
          Option("Delta Lake"))
      )

      assert(techHub.getListOfLatestTechHub(techHubData) ==listOfTechHub)
    }
  }
}
