package com.knoldus.leader_board.business

import java.io.IOException
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util

import com.google.api.services.sheets.v4.model.ValueRange
import com.knoldus.leader_board.OSContributionTemplate
import com.knoldus.leader_board.utils.SpreadSheetApi
import com.typesafe.config.ConfigFactory
import org.mockito.ArgumentMatchersSugar.any
import org.mockito.MockitoSugar
import org.scalatest.BeforeAndAfterEach
import org.scalatest.wordspec.AnyWordSpecLike


class OSContributionDataImplSpec extends AnyWordSpecLike with MockitoSugar with BeforeAndAfterEach {
  val mockOSContributionResponse = mock[SpreadSheetApi]
  val osContributionObj: OSContributionData = new OSContributionDataImpl(mockOSContributionResponse, ConfigFactory.load())
  val formatOne = new SimpleDateFormat("M/dd/yyyy")
  val formatTwo = new SimpleDateFormat("""dd\MM\yyyy""")


  "OSContributionDataImpl" should {

    val valueRange: ValueRange = new ValueRange
    val webinarValueRange = valueRange.setValues(
      util.Arrays.asList(
        util.Arrays.asList("abc@knoldus.com", "amit", "6/2/2020", "java lambdas", "abc@github.com"),
        util.Arrays.asList("xyz@knoldus.com", "akash", """05\02\2020""", "rust", "xyz@github.com"),
        util.Arrays.asList("abc@knoldus.com", "amit", "1222020", "java lambdas", "pqr@github.com")));
    "return os contribution details " in {
      when(mockOSContributionResponse.getResponse(any, any))
        .thenReturn(webinarValueRange)
      val formatDateOne = formatOne.parse("6/2/2020")
      val dateOne = new Timestamp(formatDateOne.getTime)
      val formatDateTwo = formatTwo.parse("""05\02\2020""")
      val dateTwo = new Timestamp(formatDateTwo.getTime)

      val webinarList = List(OSContributionTemplate("abc@github.com", "abc@knoldus.com", "amit", Option(dateOne), "java lambdas"),
        OSContributionTemplate("xyz@github.com", "xyz@knoldus.com", "akash", Option(dateTwo), "rust"))
      assert(osContributionObj.getOSContributionData == webinarList)
    }

    "return empty list if response method throw IO exception details " in {
      when(mockOSContributionResponse.getResponse(any, any))
        .thenThrow(new IOException)


      assert(osContributionObj.getOSContributionData == List())

    }


  }

}
