package com.knoldus.leader_board

import com.knoldus.leader_board.business.URLResponse
import org.apache.http.client.methods.HttpGet
import org.scalatest.wordspec.AnyWordSpecLike

class URLResponseSpec extends AnyWordSpecLike {
  val response = new URLResponse

  "URl response" should {
    "return an empty json response when url throws an exception" in {
      val url         = "bloog.knoldus.com"
      val httpRequest = new HttpGet(url)

      assert(response.getResponse(httpRequest) == """[]""")
    }
  }
}
