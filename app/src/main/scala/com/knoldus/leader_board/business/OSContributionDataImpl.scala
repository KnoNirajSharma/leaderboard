package com.knoldus.leader_board.business

import java.text.SimpleDateFormat

import com.knoldus.leader_board.OSContributionTemplate
import com.knoldus.leader_board.utils.SpreadSheetApi
import com.typesafe.config.Config
import com.typesafe.scalalogging.LazyLogging

import scala.collection.JavaConverters._
import scala.util.{Failure, Success, Try}

class OSContributionDataImpl(dateTimeFormats: ParseDateTimeFormats, response: SpreadSheetApi, config: Config) extends OSContributionData with LazyLogging {

  /**
   * getting data in os contribution case class from list of value range of  spread sheet data.
   *
   * @return list of os contribution case class.
   */
  override def getOSContributionData: List[OSContributionTemplate] = {
    Try {
      val osContributionSheetID = config.getString("OSContributionSpreadSheetId")
      val osContributionSheetRange = config.getString("OSContributionSpreadSheetRange")
      logger.info(s"fetching data from OS Contribution sheet with ID $osContributionSheetID")
      val values = response.getResponse(osContributionSheetID, osContributionSheetRange
      ).getValues.asScala.toList
      val oSContributionData = values.map(data => data.asScala.toList.map(_.toString))
      val oSContributionList = oSContributionData.map {
        case List(emailId, name, date, templateTopic, oSContributionLink, _*) => val contributedOn = dateTimeFormats.parseDateTimeFormat(date)
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
