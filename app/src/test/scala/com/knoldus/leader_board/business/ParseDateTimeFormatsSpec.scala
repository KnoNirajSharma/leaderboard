package com.knoldus.leader_board.business

import java.sql.Timestamp
import java.text.SimpleDateFormat

import org.mockito.MockitoSugar
import org.scalatest.BeforeAndAfterEach
import org.scalatest.wordspec.AnyWordSpecLike

class ParseDateTimeFormatsSpec extends AnyWordSpecLike with MockitoSugar with BeforeAndAfterEach {
  val dateTimeFormats = new ParseDateTimeFormats

  "Parse Date Time Format" should {

    "return time stamp after parsing with pattern of M/dd/yyyy" in {
      val formatOne = new SimpleDateFormat("M/dd/yyyy")
      val formatDateOne = formatOne.parse("2/12/2020")
      val dateOne = new Timestamp(formatDateOne.getTime)
      assert(dateTimeFormats.parseDateTimeFormat("2/12/2020") == Option(dateOne))
    }
    """return time stamp after parsing with pattern of dd\MM\yyyy""" in {
      val formatOne = new SimpleDateFormat("""dd\MM\yyyy""")
      val formatDateOne = formatOne.parse("""2\12\2020""")
      val dateOne = new Timestamp(formatDateOne.getTime)
      assert(dateTimeFormats.parseDateTimeFormat("""2\12\2020""") == Option(dateOne))
    }
    "return time stamp after parsing with pattern of dd-MMMM-yyyy" in {
      val formatOne = new SimpleDateFormat("dd-MMMM-yyyy")
      val formatDateOne = formatOne.parse("""2-MAY-2020""")
      val dateOne = new Timestamp(formatDateOne.getTime)
      assert(dateTimeFormats.parseDateTimeFormat("2-MAY-2020") == Option(dateOne))
    }
    "return none after parsing with invalid  date format" in {

      assert(dateTimeFormats.parseDateTimeFormat("2MAY2020") == None)
    }
  }
}
