package com.knoldus.leader_board.business

import java.io.IOException
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util

import com.google.api.services.sheets.v4.model.ValueRange
import com.knoldus.leader_board.OtherContributionDetails
import com.knoldus.leader_board.utils.SpreadSheetApiImpl
import com.typesafe.config.ConfigFactory
import org.mockito.ArgumentMatchersSugar.any
import org.mockito.MockitoSugar
import org.scalatest.BeforeAndAfterEach
import org.scalatest.wordspec.AnyWordSpecLike


class OtherContributionDataImplSpec extends AnyWordSpecLike with MockitoSugar with BeforeAndAfterEach {
  val mockOtherContributionResponse = mock[SpreadSheetApiImpl]
  val mockDateTimeFormat = mock[ParseDateTimeFormats]
  val otherContributionObj: OtherContributionData = new OtherContributionDataImpl(mockDateTimeFormat, mockOtherContributionResponse, ConfigFactory.load())
  val formatOne = new SimpleDateFormat("M/dd/yyyy")


  "OtherContributionDataImpl" should {

    val valueRange: ValueRange = new ValueRange
    val osContributionValueRange = valueRange.setValues(
      util.Arrays.asList(
        util.Arrays.asList("abc@knoldus.com", "amit","a@gmail.com ", "Open Source","java lambdas","6/2/2020", "why java lambdas", "abc@github.com"),
        util.Arrays.asList("xyz@knoldus.com", "akash","b@gmail.com","Conference","rust", """05\02\2020""", " why rust", "xyz@github.com")))
    "return other contribution details " in {
      val formatDateOne = formatOne.parse("6/2/2020")
      val dateOne = new Timestamp(formatDateOne.getTime)
      when(mockOtherContributionResponse.getResponse(any, any))
        .thenReturn(osContributionValueRange)
      when(mockDateTimeFormat.parseDateTimeFormat(any))
        .thenReturn(Option(dateOne))
      val webinarList = List(OtherContributionDetails("abc@github.com", "a@gmail.com", "amit", Option(dateOne), "java lambdas", "Open Source"),
        OtherContributionDetails("xyz@github.com", "b@gmail.com", "akash", Option(dateOne), "rust", "Conference"))
      assert(otherContributionObj.getOtherContributionData == webinarList)
    }

    "return empty list if response method throws IO exception details " in {
      when(mockOtherContributionResponse.getResponse(any, any))
        .thenThrow(new IOException)

      assert(otherContributionObj.getOtherContributionData == List())

    }
  }
}
