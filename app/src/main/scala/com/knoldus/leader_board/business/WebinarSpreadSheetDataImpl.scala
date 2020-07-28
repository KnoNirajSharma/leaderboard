package com.knoldus.leader_board.business

import java.sql.Timestamp
import java.text.SimpleDateFormat

import com.knoldus.leader_board.Webinar
import com.knoldus.leader_board.utils.SpreadSheetApi
import com.typesafe.config.Config
import com.typesafe.scalalogging.LazyLogging

import scala.collection.JavaConverters._
import scala.util.{Failure, Success, Try}

class WebinarSpreadSheetDataImpl(response: SpreadSheetApi, config: Config) extends WebinarSpreadSheetData with LazyLogging {
  val formatOne = new SimpleDateFormat("dd/M/yyyy")
  val formatTwo = new SimpleDateFormat("dd-MMMM-yyyy")
  val formatThree = new SimpleDateFormat("""dd\MM\yyyy""")

  /**
   * getting data in webinar case class from list of value range of  spread sheet data.
   *
   * @return list of Webinar  case class.
   */
  override def getWebinarData: List[Webinar] = {
    Try {
      logger.info("modelling data from list of value range to webinar case class")
      val values = response.getResponse(config.getString("webinarSpreadSheetId"), config.getString("webinarSpreadSheetRange")).getValues.asScala.toList
      val webinarData = values.map(data => data.asScala.toList.map(_.toString))
      val webinarList = webinarData.map {
        case List(id, date, name, topic, emailId, _*) => val deliveredOn = Try {
          val formatDate = formatOne.parse(date)
          Option(new Timestamp(formatDate.getTime))
        }.getOrElse {
          Try {
            val formatDate = formatTwo.parse(date)
            Option(new Timestamp(formatDate.getTime))
          }.getOrElse {
            Try {
              val formatDate = formatThree.parse(date)
              Option(new Timestamp(formatDate.getTime))
            }.getOrElse(None)
          }
        }
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
