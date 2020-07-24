package com.knoldus.leader_board.business

import org.apache.http.client.methods.HttpGet
import org.mockito.MockitoSugar
import org.scalatest.BeforeAndAfterEach
import org.scalatest.wordspec.AnyWordSpecLike

class URLResponseSpec extends AnyWordSpecLike with MockitoSugar with BeforeAndAfterEach  {
  val response=new URLResponse

  "URl response" should {
    "return empty json when url thrown any exception" in{

      val url="bloog.knoldus.com"
     assert( response.getEntityResponse(url)== """[]""")
    }

  }
  "return 0 header value when url thrownn any exception" in{

    val url="bloog.knoldus.com"
    assert( response.getTotalNumberOfPagesResponse(url)== """[]""")
  }
}
