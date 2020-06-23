package com.knoldus.leader_board.business

import java.sql.Timestamp

import com.knoldus.leader_board.Knolx
import com.typesafe.config.Config
import com.typesafe.scalalogging.LazyLogging
import net.liftweb.json.{DefaultFormats, parse}

class KnolxImpl(URLResponse: URLResponse, config: Config) extends LazyLogging with Knolxs {
  implicit val formats: DefaultFormats.type = DefaultFormats

  /**
   * Calls method to get latest knolx present on knolx API.
   *
   * @return List of knolx.
   */
  override def getLatestKnolxFromAPI: List[Knolx] = {
    logger.info("Latest knolx will be extracted from knolx API.")
    getListOfLatestKnolx(URLResponse.getResponse(config.getString("urlForLatestKnolx")))
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
    parsedKnolx.children map { knolxDetails =>
      val knolxId = (knolxDetails \ "id").extract[Option[Int]]
      val emailId = (knolxDetails \ "email_id").extract[Option[String]]
      val date = Timestamp.valueOf((knolxDetails \ "date").extract[String])
      val title = (knolxDetails \ "title").extract[Option[String]]
      logger.info("Modelling knolx information from JSON format to case class object.")
      Knolx(knolxId, emailId, date, title)
    }
  }
}
