package com.knoldus.leader_board.business

import com.typesafe.scalalogging._
import org.apache.commons.io.IOUtils
import org.apache.http.client.methods.{CloseableHttpResponse, HttpGet}
import org.apache.http.impl.client.HttpClientBuilder

import scala.util.{Failure, Success, Try}

class URLResponse extends LazyLogging {

  def getEntityResponse(url: String): String = {
    val result = Try {
      getResponse(url)
    }
    result match {
      case Failure(exception) => logger.info(s" getting exception from api with message ${exception.getClass.getCanonicalName} ")
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

  def getTotalNumberOfPagesResponse(url: String): String = {
    val result = Try {
      getResponse(url)
    }
    result match {
      case Failure(exception) => logger.info(s" getting exception from api with message ${exception.getClass.getCanonicalName} ")
        """[]"""
      case Success(response) => if (response.getStatusLine.getStatusCode == 200) {
        logger.info(s"getting valid response from api with status code ${response.getStatusLine.getStatusCode}")
        response.getFirstHeader("X-WP-TotalPages").getValue
      } else {
        logger.error(s"getting invalid response from api with status code ${response.getStatusLine.getStatusCode}")
        "0"
      }
    }
  }

  /**
   * Gets response from given URL.
   *
   * @param url Takes string of URL to request from that URL.
   * @return Respaonse entity in form of string.
   */
  def getResponse(url: String): CloseableHttpResponse = {
    val request = new HttpGet(url)
    val client = HttpClientBuilder.create().build()
    val response = client.execute(request)
    response
  }
}
