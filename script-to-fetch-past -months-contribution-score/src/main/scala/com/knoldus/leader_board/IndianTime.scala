package com.knoldus.leader_board

import java.time.{ZoneId, ZonedDateTime}
import java.util.TimeZone

object IndianTime {
  def indianTimezone: TimeZone = TimeZone.getTimeZone("Asia/Calcutta")
  def currentTime: ZonedDateTime = ZonedDateTime.now(ZoneId.of("Asia/Calcutta"))
}
