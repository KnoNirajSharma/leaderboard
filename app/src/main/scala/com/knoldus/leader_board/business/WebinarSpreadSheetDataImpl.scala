package com.knoldus.leader_board.business

import java.io.IOException
import java.sql.Timestamp
import java.text.SimpleDateFormat

import com.knoldus.leader_board.Webinar
import com.knoldus.leader_board.utils.SpreadSheetApi
import com.typesafe.scalalogging.LazyLogging

import scala.collection.JavaConverters._
import scala.util.Try

class WebinarSpreadSheetDataImpl(response: SpreadSheetApi) extends WebinarSpreadSheetData with LazyLogging {
  val formatOne = new SimpleDateFormat("dd/M/yyyy")
  val formatTwo = new SimpleDateFormat("dd-MMMM-yyyy")
  val formatThree = new SimpleDateFormat("""dd\MM\yyyy""")

  /**
   * getting data in webinar case class from list of value range of  spread sheet data.
   *
   * @return list of Webinar  case class.
   */
  override def getWebinarData: List[Webinar] = {
    try {
      logger.info("modelling data from list of value range to webinar case class")
      val values = response.getResponse.getValues.asScala.toList
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
    } catch {
      case ex: IOException => logger.info(ex.getClass.getCanonicalName)
        List.empty
    }
  }
}
