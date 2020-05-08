package com.knoldus.leader_board.application

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives
import ch.megard.akka.http.cors.scaladsl.CorsDirectives
import ch.megard.akka.http.cors.scaladsl.settings.CorsSettings
import com.knoldus.leader_board.business.OverallReputation
import com.knoldus.leader_board.infrastructure.ReadAllTimeReputation
import com.typesafe.config.Config
import com.typesafe.scalalogging._
import net.liftweb.json.Extraction.decompose
import net.liftweb.json.{DefaultFormats, compactRender}

import scala.concurrent.{ExecutionContextExecutor, Future}

class AllTimeDataOnAPIImpl(overallReputation: OverallReputation, readAllTimeReputation: ReadAllTimeReputation
                           , config: Config)(implicit system: ActorSystem, executionContext: ExecutionContextExecutor)
  extends AllTimeDataOnAPI with Directives with CorsDirectives with LazyLogging {
  implicit val formats: DefaultFormats.type = net.liftweb.json.DefaultFormats

  /**
   * Displays all time reputation of each knolder on API.
   *
   * @return Http request binded with server port.
   */
  override def displayAllTimeDataOnAPI: Future[Http.ServerBinding] = {
    logger.info("Displaying reputation of each knolder on the API.")
    val route = cors(settings = CorsSettings.defaultSettings) {
      path("reputation") {
        get {
          complete(HttpEntity(ContentTypes.`application/json`,
            compactRender(decompose(readAllTimeReputation.fetchAllTimeReputationData))))
        }
      }
    }
    Http().bindAndHandle(route, config.getString("interface"), config.getInt("port"))
  }.recoverWith {
    case ex: Exception => Future.failed(new Exception("Service failed", ex))
  }
}
