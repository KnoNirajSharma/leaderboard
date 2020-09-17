package com.knoldus.leader_board.business

import java.sql.Timestamp

import com.typesafe.scalalogging.LazyLogging

import scala.util.Try

class ParseDateTimeFormats extends LazyLogging {

  def parseDateTimeFormat(date: String): Option[Timestamp] = {
    logger.info("parsing the date time formats into timestamp")
    Try {
      val formatDate = DateTimeFormats.formatOne.parse(date)
      Option(new Timestamp(formatDate.getTime))
    }.getOrElse {
      Try {
        val formatDate = DateTimeFormats.formatTwo.parse(date)
        Option(new Timestamp(formatDate.getTime))
      }.getOrElse {
        Try {
          val formatDate = DateTimeFormats.formatThree.parse(date)
          Option(new Timestamp(formatDate.getTime))
        }.getOrElse(None)
      }
    }
  }
}
