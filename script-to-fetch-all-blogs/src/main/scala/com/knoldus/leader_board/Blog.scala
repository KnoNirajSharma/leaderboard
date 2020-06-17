package com.knoldus.leader_board

import java.sql.Timestamp

final case class Blog(blogId: Option[Int], wordpressId: Option[String], publishedOn: Timestamp, title: Option[String])
