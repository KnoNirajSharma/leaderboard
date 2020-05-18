package com.knoldus.leader_board.application

import akka.http.scaladsl.Http

import scala.concurrent.Future

trait ReputationOnAPI {
  def displayReputationOnAPI: Future[Http.ServerBinding]
}
