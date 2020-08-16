package com.knoldus.leader_board.business

import java.sql.Timestamp
import java.text.SimpleDateFormat

import com.knoldus.leader_board.Knolx
import com.typesafe.config.Config
import com.typesafe.scalalogging.LazyLogging
import net.liftweb.json.{DefaultFormats, parse}

class KnolxImpl(URLResponse: URLResponse, config: Config) extends LazyLogging with Knolxs {
  implicit val formats: DefaultFormats.type = DefaultFormats

  /**
   * Calls method to get all knolx present on knolx API.
   *
   * @return List of knolx.
   */
  override def getAllKnolxDetails: List[Knolx] = {
    logger.info("all knolx data awill be extracted from knolx API.")
    parseAllKnolxJsonData(URLResponse.getResponse(config.getString("urlForKnolx")))
  }

  /**
   * Parse knolx data from knolx API.
   *
   * @param unparsedKnolx JSON string of knolx data fetched from knolx API.
   * @return List of knolx.
   */
  override def parseAllKnolxJsonData(unparsedKnolx: String): List[Knolx] = {
    logger.info("Parsing JSON string of knolx information.")
    val parsedKnolx = parse(unparsedKnolx)
    val knolxs = parsedKnolx.children map { knolx =>
      val emailId = (knolx \ "email").extract[Option[String]]
      (knolx \ "knolxDetails").children.map { knolxDetails =>
        val dateOfSession = (knolxDetails \ "dateOfSession").extract[Option[String]]
        val delivered_on = dateOfSession.map { date =>
          val formatter = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy")
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
