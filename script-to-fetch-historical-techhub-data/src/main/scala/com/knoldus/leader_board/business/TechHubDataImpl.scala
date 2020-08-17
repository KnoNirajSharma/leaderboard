package com.knoldus.leader_board.business

import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.time.{ZoneId, ZonedDateTime}

import com.knoldus.leader_board.TechHubTemplate
import com.typesafe.config.Config
import com.typesafe.scalalogging.LazyLogging
import net.liftweb.json.{DefaultFormats, parse}

import scala.util.Try

class TechHubDataImpl(URLResponse: URLResponse, config: Config) extends LazyLogging with TechHubData {
  implicit val formats: DefaultFormats.type = DefaultFormats

  /**
   * Calls method to get latest techhub present on techhub API.
   *
   * @return List of techhub.
   */
  override def getLatestTechHubTemplates: List[TechHubTemplate] = {
    logger.info("historical techhub will be extracted from techhub API.")
    val startDate = "0001-01-01"
    val endDate =ZonedDateTime.now(ZoneId.of("Asia/Calcutta")).toLocalDate.toString
    parseTechHubJson(URLResponse.getTechHubResponse(config.getString("urlForTechHub"), startDate, endDate))
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
