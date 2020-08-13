package com.knoldus.leader_board.business

import java.sql.Timestamp
import java.text.SimpleDateFormat

import com.knoldus.leader_board.OSContributionTemplate
import com.knoldus.leader_board.utils.SpreadSheetApi
import com.typesafe.config.Config
import com.typesafe.scalalogging.LazyLogging

import scala.collection.JavaConverters._
import scala.util.{Failure, Success, Try}

class OSContributionDataImpl(response: SpreadSheetApi, config: Config) extends OSContributionData with LazyLogging {
  val formatOne = new SimpleDateFormat("M/dd/yyyy")
  val formatTwo = new SimpleDateFormat("dd-MMMM-yyyy")
  val formatThree = new SimpleDateFormat("""dd\MM\yyyy""")

  /**
   * getting data in os contribution case class from list of value range of  spread sheet data.
   *
   * @return list of os contribution case class.
   */
  override def getOSContributionData: List[OSContributionTemplate] = {
    Try {
      logger.info("modelling data from list of value range to os contribution case class")
      val values = response.getResponse(config.getString("OSContributionSpreadSheetId"),
        config.getString("OSContributionSpreadSheetRange")).getValues.asScala.toList
      val oSContributionData = values.map(data => data.asScala.toList.map(_.toString))
      val oSContributionList = oSContributionData.map {
        case List(emailId, name, date, templateTopic, oSContributionLink, _*) => val contributedOn = Try {
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
          OSContributionTemplate(oSContributionLink, emailId, name, contributedOn, templateTopic)
      }
      oSContributionList.filter(OSContribution => OSContribution.contributedOn.isDefined)
    } match {
      case Success(value) => value
      case Failure(exception) => logger.info(exception.getClass.getCanonicalName)
        List.empty
    }
  }
}
