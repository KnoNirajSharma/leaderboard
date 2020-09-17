package com.knoldus.leader_board.application

import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Route

import scala.concurrent.Future

trait ReputationOnAPI {
  def reputationRoute: Route

  def monthlyDetailsRoute: Route

  def allTimeDetailsRoute: Route

  def displayReputationOnAPI: Future[Http.ServerBinding]

  def twelveMonthsRoute: Route

  def hallOfFameRoute: Route

}
