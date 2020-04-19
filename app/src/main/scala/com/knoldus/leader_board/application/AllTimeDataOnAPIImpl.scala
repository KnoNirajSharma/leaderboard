package com.knoldus.leader_board.application

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives
import com.knoldus.leader_board.GetAuthorScore
import com.knoldus.leader_board.infrastructure.FetchData
import com.typesafe.config.Config
import net.liftweb.json.Extraction.decompose
import net.liftweb.json.{DefaultFormats, compactRender}

import scala.concurrent.{ExecutionContextExecutor, Future}

class AllTimeDataOnAPIImpl(fetchData: FetchData, config: Config)(implicit system: ActorSystem,
                                                                 executionContext: ExecutionContextExecutor)
  extends AllTimeDataOnAPI with Directives {
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
