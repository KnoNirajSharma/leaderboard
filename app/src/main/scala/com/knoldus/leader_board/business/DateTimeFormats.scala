package com.knoldus.leader_board.business

import java.text.SimpleDateFormat

object DateTimeFormats {
  val formatOne = new SimpleDateFormat("M/dd/yyyy")
  val formatTwo = new SimpleDateFormat("dd-MMMM-yyyy")
  val formatThree = new SimpleDateFormat("""dd\MM\yyyy""")
}
