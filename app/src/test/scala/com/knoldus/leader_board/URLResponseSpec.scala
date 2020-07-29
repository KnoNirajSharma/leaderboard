package com.knoldus.leader_board

import com.knoldus.leader_board.business.URLResponse
import org.apache.http.HttpRequest
import org.apache.http.client.methods.HttpGet
import org.mockito.MockitoSugar
import org.scalatest.BeforeAndAfterEach
import org.scalatest.wordspec.AnyWordSpecLike

class URLResponseSpec extends AnyWordSpecLike with MockitoSugar with BeforeAndAfterEach  {
  val response=new URLResponse

  "URl response" should {
    "return empty json when url thrwon any exception" in{

      val url="bloog.knoldus.com"
      val httpRequest=new HttpGet(url)
     assert( response.getResponse(httpRequest)== """[]""")
    }

  }

}
