package com.knoldus.leader_board.business

import com.typesafe.scalalogging._
import org.apache.commons.io.IOUtils
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.HttpClientBuilder

class URLResponse extends LazyLogging {
  /**
   * Gets response from given URL.
   *
   * @param url Takes string of URL to request from that URL.
   * @return Respaonse entity in form of string.
   */
  def getResponse(url: String): String = {
    logger.info(s"Gettting response from $url")
    val request = new HttpGet(url)
    val client = HttpClientBuilder.create().build()
    val response = client.execute(request)
    IOUtils.toString(response.getEntity.getContent)
  }
}
