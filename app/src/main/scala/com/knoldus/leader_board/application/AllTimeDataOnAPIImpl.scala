package com.knoldus.leader_board.application

import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives
import com.knoldus.leader_board.infrastructure.FetchData
import com.knoldus.leader_board.{CreateActorSystem, GetAuthorScore}
import com.typesafe.config.Config
import net.liftweb.json.Extraction.decompose
import net.liftweb.json.{DefaultFormats, compactRender}

import scala.concurrent.Future

class AllTimeDataOnAPIImpl(fetchData: FetchData, config: Config) extends AllTimeDataOnAPI with Directives
  with CreateActorSystem {
  implicit val formats: DefaultFormats.type = net.liftweb.json.DefaultFormats

  /**
   * Displays all time data of each knolder on API.
   *
   * @return Http request binded with server port.
   */
  override def pushAllTimeDataOnAPI: Future[Http.ServerBinding] = {
    val scoreForBlogsPerAuthor: List[GetAuthorScore] = fetchData.fetchAllTimeData
    val reputation = compactRender(decompose(scoreForBlogsPerAuthor))
    val route =
      path("reputation") {
        get {
          complete(HttpEntity(ContentTypes.`application/json`, reputation))
        }
      }
    Http().bindAndHandle(route, config.getString("interface"), config.getInt("port"))
  }.recoverWith {
    case ex: Exception => Future.failed(new Exception("Service failed", ex))
  }
}
