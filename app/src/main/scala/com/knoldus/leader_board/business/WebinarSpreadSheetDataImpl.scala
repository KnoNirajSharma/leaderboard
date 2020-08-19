package com.knoldus.leader_board.business

import com.knoldus.leader_board.Webinar
import com.knoldus.leader_board.utils.SpreadSheetApi
import com.typesafe.config.Config
import com.typesafe.scalalogging.LazyLogging

import scala.collection.JavaConverters._
import scala.util.{Failure, Success, Try}

class WebinarSpreadSheetDataImpl(dateTimeFormats: ParseDateTimeFormats, response: SpreadSheetApi, config: Config) extends WebinarSpreadSheetData
  with LazyLogging {

  /**
   * getting data in webinar case class from list of value range of  spread sheet data.
   *
   * @return list of Webinar  case class.
   */
  override def getWebinarData: List[Webinar] = {
    Try {
      val webinarSheetId = config.getString("webinarSpreadSheetId")
      val webinarSheetRange = config.getString("webinarSpreadSheetRange")
      logger.info(s"fetching data from webinar  sheet with ID $webinarSheetId")
      val values = response.getResponse(webinarSheetId, webinarSheetRange).getValues.asScala.toList
      val webinarData = values.map(data => data.asScala.toList.map(_.toString))
      val webinarList = webinarData.map {
        case List(id, date, name, topic, emailId, _*) => val deliveredOn = dateTimeFormats.parseDateTimeFormat(date)
          Webinar(id, deliveredOn, name, topic, emailId)
      }
      webinarList.filter(webinar => webinar.deliveredOn.isDefined)
    } match {
      case Success(value) => value
      case Failure(exception) => logger.info(exception.getClass.getCanonicalName)
        List.empty
    }
  }
}
