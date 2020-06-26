package com.knoldus.leader_board.business

import com.typesafe.scalalogging._
import org.apache.commons.io.IOUtils
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.HttpClientBuilder
import org.apache.http.client.utils.URIBuilder

class URLResponse extends LazyLogging {
  /**
   * Gets response from given URL and setting parameters.
   *
   * @param url  Takes string of URL to request from that URL.
   * @param afterDate Takes date for fetching blogs published after that date.
   * @return Response entity in form of string.
   */
  def getResponse(url: String, afterDate: String): String = {
    logger.info("Gettting response from Wordpress API")
    val builder = new URIBuilder(url)
    builder.setParameter("per_page", "100").setParameter("after", afterDate)
      .setParameter("_embed", "author")
    val request = new HttpGet(builder.build())
    val client = HttpClientBuilder.create().build()
    val response = client.execute(request)
    IOUtils.toString(response.getEntity.getContent)
  }
}
