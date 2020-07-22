package com.knoldus.leader_board.business

import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util

import com.google.api.services.sheets.v4.model.ValueRange
import com.knoldus.leader_board.Webinar
import org.mockito.MockitoSugar
import org.scalatest.BeforeAndAfterEach
import org.scalatest.wordspec.AnyWordSpecLike

class WebinarSpreadSheetDataImplSpec  extends AnyWordSpecLike with MockitoSugar with BeforeAndAfterEach {
  val mockWebinarResponse=mock[SpreadSheetApi]
  val webinarObj:WebinarSpreadSheetData=new WebinarSpreadSheetDataImpl(mockWebinarResponse)
  val formatOne = new SimpleDateFormat("dd/MM/yyyy")

  "webinar" should{

    val valueRange:ValueRange=new ValueRange
    val webinarValueRange=valueRange.setValues(
      util.Arrays.asList(
        util.Arrays.asList("1", "12/2/2020", "amit","java lambdas","abc@knoldus.com"),
        util.Arrays.asList("2", "13/2/2020", "akash","k","xyz@knoldus.com")));
    "return webinar details " in {
      when(mockWebinarResponse.getResponse)
        .thenReturn(webinarValueRange)
      val formatDateOne = formatOne.parse("12/2/2020")
      val dateOne=new Timestamp(formatDateOne.getTime)
      val formatDateTwo = formatOne.parse("13/2/2020")
      val dateTwo=new Timestamp(formatDateTwo.getTime)

      val webinarList=List(Webinar("1",dateOne, "amit","java lambdas","abc@knoldus.com"),
        Webinar("2",dateTwo, "akash","k","xyz@knoldus.com"))
      assert(webinarObj.getWebinarData == webinarList)

    }

  }

}
