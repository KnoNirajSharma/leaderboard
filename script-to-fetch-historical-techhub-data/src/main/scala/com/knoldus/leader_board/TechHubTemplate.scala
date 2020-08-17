package com.knoldus.leader_board

import java.sql.Timestamp

final case class TechHubTemplate(techHubId: Option[String], emailId: Option[String], uploadedOn: Option[Timestamp], title: Option[String])
