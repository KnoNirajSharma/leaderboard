package com.knoldus.leader_board.business

import com.knoldus.leader_board.OtherContributionDetails
import com.knoldus.leader_board.utils.SpreadSheetApi
import com.typesafe.config.Config
import com.typesafe.scalalogging.LazyLogging

import scala.collection.JavaConverters._
import scala.util.{Failure, Success, Try}

class OtherContributionDataImpl(dateTimeFormats: ParseDateTimeFormats, response: SpreadSheetApi, config: Config) extends
  OtherContributionData with LazyLogging {

  /**
   * getting data in other contribution case class from list of value range of  spread sheet data.
   *
   * @return list of other contribution case class.
   */
  override def getOtherContributionData: List[OtherContributionDetails] = {
    Try {
      val otherContributionSheetID = config.getString("otherContributionSpreadSheetId")
      val otherContributionSheetRange = config.getString("otherContributionSpreadSheetRange")
      logger.info(s"fetching data from other Contribution sheet with ID $otherContributionSheetID")
      val values = response.getResponse(otherContributionSheetID, otherContributionSheetRange
      ).getValues.asScala.toList
      val otherContributionData = values.map(data => data.asScala.toList.map(_.toString))
      val otherContributionList = otherContributionData.map {
        case List(_, name, emailId, typeOfContribution, title, date, _, urlLink, _*) =>
          val dateOfContribution = dateTimeFormats.parseDateTimeFormat(date)
          OtherContributionDetails(urlLink, emailId, name, dateOfContribution, title, typeOfContribution)
      }
      otherContributionList.filter(otherContribution => otherContribution.contributedOn.isDefined)
    } match {
      case Success(value) => value
      case Failure(exception) => logger.info(s"The cause of the exception is $exception")
        List.empty
    }
  }
}
