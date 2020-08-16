package com.knoldus.leader_board.business

import com.typesafe.scalalogging._
import org.apache.commons.io.IOUtils
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.HttpClientBuilder

import scala.util.{Failure, Success, Try}

class URLResponse extends LazyLogging {
  /**
   * Gets response from given URL.
   *
   * @param url Takes string of URL to request from that URL.
   * @return Response entity in form of string.
   */
  def getResponse(url: String): String = {
    Try {
      val request = new HttpGet(url)
      val client = HttpClientBuilder.create().build()
     client.execute(request)
    }match {
      case Failure(exception) =>
        logger.info(s" getting exception from api with message ${exception.getClass.getCanonicalName} ")
        """[]"""
      case Success(response) => if (response.getStatusLine.getStatusCode == 200) {
        logger.info(s"getting valid response from api with status code ${response.getStatusLine.getStatusCode}")
        IOUtils.toString(response.getEntity.getContent)
      } else {
        logger.error(s"getting invalid response from api with status code ${response.getStatusLine.getStatusCode}")
        """[]"""
      }
    }
  }
}
