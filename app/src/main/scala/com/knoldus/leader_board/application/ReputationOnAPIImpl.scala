package com.knoldus.leader_board.application

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives
import ch.megard.akka.http.cors.scaladsl.CorsDirectives
import ch.megard.akka.http.cors.scaladsl.settings.CorsSettings
import com.knoldus.leader_board.infrastructure.{ReadAllTimeReputation, ReadMonthlyReputation}
import com.typesafe.config.Config
import com.typesafe.scalalogging._
import net.liftweb.json.Extraction.decompose
import net.liftweb.json.{DefaultFormats, compactRender}

import scala.concurrent.{ExecutionContextExecutor, Future}

class ReputationOnAPIImpl(readAllTimeReputation: ReadAllTimeReputation, readMonthlyReputation: ReadMonthlyReputation,
                          config: Config)(implicit system: ActorSystem, executionContext: ExecutionContextExecutor)
  extends ReputationOnAPI with Directives with CorsDirectives with LazyLogging {
  implicit val formats: DefaultFormats.type = net.liftweb.json.DefaultFormats

  /**
   * Displays all time reputation and monthly reputation of each knolder on API.
   *
   * @return Http request binded with server port.
   */
  override def displayReputationOnAPI: Future[Http.ServerBinding] = {
    logger.info("Displaying all time reputation of each knolder on the API.")
    val routeForAllTimeData = cors(settings = CorsSettings.defaultSettings) {
      path("all-time-reputation") {
        get {
          complete(HttpEntity(ContentTypes.`application/json`,
            compactRender(decompose(readAllTimeReputation.fetchAllTimeReputationData))))
        }
      }
    }

    logger.info("Displaying monthly reputation of each knolder on the API.")
    val routeForMonthlyData = cors(settings = CorsSettings.defaultSettings) {
      path("monthly-reputation") {
        get {
          complete(HttpEntity(ContentTypes.`application/json`,
            compactRender(decompose(readMonthlyReputation.fetchMonthlyReputationData))))
        }
      }
    }

    val mainRoute = routeForAllTimeData ~ routeForMonthlyData
    Http().bindAndHandle(mainRoute, config.getString("interface"), config.getInt("port"))
  }.recoverWith {
    case ex: Exception => Future.failed(new Exception("Service failed", ex))
  }
}
