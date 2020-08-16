package com.knoldus.leader_board

import java.sql.Timestamp

final case class Knolx(knolxId: Option[String], emailId: Option[String], deliveredOn: Option[Timestamp], title: Option[String])
