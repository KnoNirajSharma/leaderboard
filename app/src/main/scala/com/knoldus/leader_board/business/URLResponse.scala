package com.knoldus.leader_board.business

import java.io.IOException

import com.typesafe.scalalogging._
import org.apache.commons.io.IOUtils
import org.apache.http.client.methods.HttpGet
import org.apache.http.client.utils.URIBuilder
import org.apache.http.impl.client.HttpClientBuilder

class URLResponse extends LazyLogging {

  /**
   * Gets response from given URL and setting parameters.
   *
   * @param url       Takes URL on which request is sent.
   * @param afterDate Takes date for fetching blogs published after that date.
   * @return Response entity in form of string.
   */
  def getBlogResponse(url: String, afterDate: String): String = {
    logger.info("Gettting response from Wordpress API")
    val builder = new URIBuilder(url)
    builder.setParameter("per_page", "100").setParameter("after", afterDate)
      .setParameter("_embed", "author")
    val request = new HttpGet(builder.build())
    getResponse(request)
  }

  /**
   * Gets response from given URL and setting parameters.
   *
   * @param url       Takes URL on which request is sent.
   * @param startDate Takes date for fetching knolx delivered after that date.
   * @param endDate   Takes date for fetching knolx delivered before that date.
   * @return Response entity in form of string.
   */
  def getKnolxResponse(url: String, startDate: String, endDate: String): String = {
    logger.info(s"Gettting response from knolx API ")
    val builder = new URIBuilder(url)
    builder.setParameter("startDate", startDate).setParameter("endDate", endDate)
    val request = new HttpGet(builder.build())
    getResponse(request)
  }

  def getResponse(request: HttpGet): String = {
    try {
      val client = HttpClientBuilder.create().build()
      val response = client.execute(request)
      if(response.getStatusLine.getStatusCode==200){
        IOUtils.toString(response.getEntity.getContent)
      }else{
        logger.info("getting invalid response from api")
        """[]"""
      }
    } catch {
      case _: IOException => logger.info("getting any IO exception from api")
        """[]"""
    }
  }
}

