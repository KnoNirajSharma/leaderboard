package com.knoldus.leader_board.application

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives
import akka.stream.SystemMaterializer
import com.knoldus.leader_board.GetAuthorScore
import com.typesafe.config.{Config, ConfigFactory}
import net.liftweb.json.Extraction.decompose
import net.liftweb.json.{DefaultFormats, compactRender}

import scala.concurrent.{ExecutionContextExecutor, Future}

class AllTimeDataOnAPI extends Directives {
  val config: Config = ConfigFactory.load()
  implicit val formats: DefaultFormats.type = net.liftweb.json.DefaultFormats
  implicit val system: ActorSystem = ActorSystem()
  implicit val materializer: SystemMaterializer = SystemMaterializer(system)
  implicit val executionContext: ExecutionContextExecutor = system.dispatcher

  /**
   * Displays all time data of each knolder on API.
   *
   * @param scoreForBlogsPerAuthor List of GetAuthorScore case class objects, which specifies full name,
   *                               overall score and overall rank of each knolder.
   * @return Http request binded with server port.
   */
  def pushAllTimeDataOnAPI(scoreForBlogsPerAuthor: List[GetAuthorScore]): Future[Http.ServerBinding] = {
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
