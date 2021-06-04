package com.knoldus.leader_board.business

import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.Locale

import com.knoldus.leader_board.infrastructure.FetchKnolx
import com.knoldus.leader_board.{IndianTime, Knolx}
import com.typesafe.config.Config
import com.typesafe.scalalogging.LazyLogging
import net.liftweb.json.{DefaultFormats, parse}

class KnolxImpl(fetchKnolx: FetchKnolx, URLResponse: URLResponse, config: Config) extends LazyLogging with Knolxs {
  implicit val formats: DefaultFormats.type = DefaultFormats

  /**
   * Calls method to get latest knolx present on knolx API.
   *
   * @return List of knolx.
   */
  override def getLatestKnolxFromAPI: List[Knolx] = {
    logger.info("Latest knolx will be extracted from knolx API.")
    val maxDate = fetchKnolx.fetchMaxKnolxDeliveredDate.getOrElse(Timestamp.valueOf("0001-01-01 00:00:00"))
    val startDate = maxDate.getTime.toString
    val endDate = Timestamp.valueOf(IndianTime.currentTime.toLocalDateTime).getTime.toString
    getListOfLatestKnolx(URLResponse.getKnolxResponse(config.getString("urlForLatestKnolx"), startDate, endDate))
  }

  /**
   * Parse knolx data from knolx API.
   *
   * @param unparsedKnolx JSON string of knolx data fetched from knolx API.
   * @return List of knolx.
   */
  override def getListOfLatestKnolx(unparsedKnolx: String): List[Knolx] = {
    logger.info("Parsing JSON string of knolx information.")
    val parsedKnolx = parse(unparsedKnolx)
    val knolxs = parsedKnolx.children map { knolx =>
      val emailId = (knolx \ "email").extract[Option[String]]
      (knolx \ "knolxDetails").children.map { knolxDetails =>
        val dateOfSession = (knolxDetails \ "dateOfSession").extract[Option[String]]
        val delivered_on = dateOfSession.map { date =>
          val formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH)
          new Timestamp(formatter.parse(date).getTime)
        }
        val knolxId = (knolxDetails \ "id").extract[Option[String]]
        val title = (knolxDetails \ "title").extract[Option[String]]

        Knolx(knolxId, emailId, delivered_on, title)
      }
    }
    knolxs.flatten.filter(knolx => knolx.knolxId.isDefined && knolx.title.isDefined)
  }
}
