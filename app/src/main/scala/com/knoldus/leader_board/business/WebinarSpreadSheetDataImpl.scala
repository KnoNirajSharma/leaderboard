package com.knoldus.leader_board.business

import java.sql.Timestamp
import java.text.{ParseException, SimpleDateFormat}

import com.knoldus.leader_board.Webinar
import com.typesafe.scalalogging.LazyLogging

import scala.collection.JavaConverters._

class WebinarSpreadSheetDataImpl(response: SpreadSheetApi) extends WebinarSpreadSheetData with LazyLogging {
  val formatOne = new SimpleDateFormat("dd/M/yyyy")
  val formatTwo = new SimpleDateFormat("dd-MMMM-yyyy")
  val formatThree = new SimpleDateFormat("""dd\MM\yyyy""")

  /**
   * getting data in webinar case class from list of value range of  spread sheet data.
   * @return list of Webinar  case class.
   */
  override def getWebinarData: List[Webinar] = {
    logger.info("modelling data from list of value range to webinar case class")
    val values = response.getResponse.getValues.asScala.toList
    val webinarData = values.map(data => data.asScala.toList.map(_.toString))
    webinarData.map{
      case List(id, date, name, topic, emailId, _*) => val deliveredOn = try {
          val formatDate = formatOne.parse(date)
          new Timestamp(formatDate.getTime)
        }
        catch {
          case _: ParseException => try {
            val formatDate = formatTwo.parse(date)
            new Timestamp(formatDate.getTime)
          } catch {
            case _: ParseException =>
              val formatDate = formatThree.parse(date)
              new Timestamp(formatDate.getTime)
          }
        }
          Webinar(id, deliveredOn, name, topic, emailId)
      }
    }
}
