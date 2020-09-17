package com.knoldus.leader_board.business

import java.io.IOException
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util

import com.google.api.services.sheets.v4.model.ValueRange
import com.knoldus.leader_board.Webinar
import com.knoldus.leader_board.utils.SpreadSheetApiImpl
import com.typesafe.config.ConfigFactory
import org.mockito.ArgumentMatchersSugar.any
import org.mockito.MockitoSugar
import org.scalatest.BeforeAndAfterEach
import org.scalatest.wordspec.AnyWordSpecLike


class WebinarSpreadSheetDataImplSpec extends AnyWordSpecLike with MockitoSugar with BeforeAndAfterEach {
  val mockWebinarResponse = mock[SpreadSheetApiImpl]
  val mockDateTimeFormat = mock[ParseDateTimeFormats]
  val webinarObj: WebinarSpreadSheetData = new WebinarSpreadSheetDataImpl(mockDateTimeFormat, mockWebinarResponse, ConfigFactory.load())
  val formatOne = new SimpleDateFormat("dd/M/yyyy")

  "WebinarSpreadSheetDataImpl" should {

    val valueRange: ValueRange = new ValueRange
    val webinarValueRange = valueRange.setValues(
      util.Arrays.asList(
        util.Arrays.asList("1", "12/2/2020", "amit", "java lambdas", "abc@knoldus.com"),
        util.Arrays.asList("2", "13/2/2020", "akash", "k", "xyz@knoldus.com")))
    "return webinar details " in {
      when(mockWebinarResponse.getResponse(any, any))
        .thenReturn(webinarValueRange)
      val formatDateOne = formatOne.parse("12/2/2020")
      val dateOne = new Timestamp(formatDateOne.getTime)
      when(mockDateTimeFormat.parseDateTimeFormat(any))
        .thenReturn(Option(dateOne))

      val webinarList = List(Webinar("1", Option(dateOne), "amit", "java lambdas", "abc@knoldus.com"),
        Webinar("2", Option(dateOne), "akash", "k", "xyz@knoldus.com"))
      assert(webinarObj.getWebinarData == webinarList)
    }

    "return empty list if response method throws IO exception details " in {
      when(mockWebinarResponse.getResponse(any, any))
        .thenThrow(new IOException)


      assert(webinarObj.getWebinarData == List())

    }
  }
}
