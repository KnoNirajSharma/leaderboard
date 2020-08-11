package com.knoldus.leader_board.business

import java.sql.Timestamp
import java.text.SimpleDateFormat

import com.knoldus.leader_board.infrastructure.FetchTechHub
import com.knoldus.leader_board.{IndianTime, TechHubTemplate}
import com.typesafe.config.Config
import com.typesafe.scalalogging.LazyLogging
import net.liftweb.json.{DefaultFormats, parse}

import scala.util.Try

class TechHubDataImpl(fetchTechHub: FetchTechHub, URLResponse: URLResponse, config: Config) extends LazyLogging with TechHubData {
  implicit val formats: DefaultFormats.type = DefaultFormats

  /**
   * Calls method to get latest techhub present on techhub API.
   *
   * @return List of techhub.
   */
  override def getLatestTechHubTemplates: List[TechHubTemplate] = {
    logger.info("Latest techhub will be extracted from techhub API.")
    val maxDate = fetchTechHub.getLastUpdatedDateForTechHub.getOrElse(Timestamp.valueOf("0001-01-01 00:00:00"))
    val startDate = maxDate.toLocalDateTime.toLocalDate.toString
    val endDate = IndianTime.currentTime.toLocalDateTime.toLocalDate.toString
    parseTechHubJson(URLResponse.getTechHubResponse(config.getString("urlForLatestTechHub"), startDate, endDate))
  }

  /**
   * Parse techhub data from techhub API.
   *
   * @param unparsedTechHub JSON string of techhub data fetched from techhub API.
   * @return List of techhub.
   */
  override def parseTechHubJson(unparsedTechHub: String): List[TechHubTemplate] = {
    logger.info("Parsing JSON string of techhub information.")
    val parsedTecHub = parse(unparsedTechHub)
    val techHubs = (parsedTecHub \ "data" \ "leaderShipBoardTemplates").children map { techHub =>
      val emailId = (techHub \ "author_mail").extract[Option[String]]

      val dateOfTechHub = (techHub \ "uploadedDate").extract[Option[String]]
      val uploaded_on = Try {
        dateOfTechHub.map { date =>
          val formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS")
          new Timestamp(formatter.parse(date).getTime)
        }
      }.getOrElse(None)
      val techHubId = (techHub \ "_id").extract[Option[String]]
      val title = (techHub \ "title").extract[Option[String]]

      TechHubTemplate(techHubId, emailId, uploaded_on, title)

    }
    techHubs.filter(techHub => techHub.techHubId.isDefined && techHub.title.isDefined && techHub.uploadedOn.isDefined && techHub.emailId.isDefined)
  }
}
