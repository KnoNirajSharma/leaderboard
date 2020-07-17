package com.knoldus.leader_board.business

import java.sql.{Connection, Timestamp}
import java.time.Instant

import com.knoldus.leader_board.infrastructure.{FetchKnolx, FetchKnolxImpl}
import com.knoldus.leader_board.{DatabaseConnection, Knolx}
import com.typesafe.config.ConfigFactory
import org.mockito.MockitoSugar
import org.mockito.ArgumentMatchersSugar.any
import org.scalatest.BeforeAndAfterEach
import org.scalatest.wordspec.AnyWordSpecLike

class KnolxImplSpec extends AnyWordSpecLike with MockitoSugar with BeforeAndAfterEach {
  implicit val connection: Connection = DatabaseConnection.connection(ConfigFactory.load())
  val mockFetchKnolx: FetchKnolx = mock[FetchKnolxImpl]
  val mockURLResponse: URLResponse = mock[URLResponse]
  val knolx: Knolxs = new KnolxImpl(mockFetchKnolx, mockURLResponse, ConfigFactory.load())

  "Knolx" should {

    val knolxData: String =
      """[{
        |	"email": "mukesh.kumar@knoldus.com",
        |	"knolxCount": 4,
        |	"knolxDetails": [{
        |		"dateOfSession": "Mon Jan 19 11:49:09 IST 1970",
        |		"id": "ab3c6981-9964-46e2-adcd-64154120c1dc",
        |		"title": "Reactive Microservices"
        |	}, {
        |		"dateOfSession": "Mon Jan 19 15:11:46 IST 1970",
        |		"id": "4cb67c8f-941b-4860-ba4e-a7e7f497768d",
        |		"title": "Delta Lake"
        |	}],
        |	"userId": "8694bdb6-3bf0-4c86-a443-6e631b10562b"
        |}]""".stripMargin

    "return latest knolx from API" in {
      when(mockFetchKnolx.fetchMaxKnolxDeliveredDate)
        .thenReturn(Option(Timestamp.from(Instant.parse("2019-01-19T11:49:09Z"))))

      when(mockURLResponse.getKnolxResponse(any,any, any))
        .thenReturn(knolxData)

      val listOfKnolx = List(
        Knolx(
          Option("ab3c6981-9964-46e2-adcd-64154120c1dc"),
          Option("mukesh.kumar@knoldus.com"),
          Option(Timestamp.valueOf("1970-01-19 11:49:09.0")),
          Option("Reactive Microservices")),
        Knolx(
          Option("4cb67c8f-941b-4860-ba4e-a7e7f497768d"),
          Option("mukesh.kumar@knoldus.com"),
          Option(Timestamp.valueOf("1970-01-19 15:11:46.0")),
          Option("Delta Lake"))
      )

      assert(knolx.getLatestKnolxFromAPI == listOfKnolx)
    }

    "return list of latest knolx" in {
      val listOfKnolx = List(
        Knolx(
          Option("ab3c6981-9964-46e2-adcd-64154120c1dc"),
          Option("mukesh.kumar@knoldus.com"),
          Option(Timestamp.valueOf("1970-01-19 11:49:09.0")),
          Option("Reactive Microservices")),
        Knolx(
          Option("4cb67c8f-941b-4860-ba4e-a7e7f497768d"),
          Option("mukesh.kumar@knoldus.com"),
          Option(Timestamp.valueOf("1970-01-19 15:11:46.0")),
          Option("Delta Lake"))
      )

      assert(knolx.getListOfLatestKnolx(knolxData) == listOfKnolx)
    }
  }
}
