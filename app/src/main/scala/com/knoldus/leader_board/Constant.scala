package com.knoldus.leader_board

import java.time.{ZoneId, ZonedDateTime}
import java.util.TimeZone

object Constant {
  val INDIAN_TIMEZONE = TimeZone.getTimeZone("Asia/Calcutta")
  val CURRENT_TIME = ZonedDateTime.now(ZoneId.of("Asia/Calcutta"))
}
