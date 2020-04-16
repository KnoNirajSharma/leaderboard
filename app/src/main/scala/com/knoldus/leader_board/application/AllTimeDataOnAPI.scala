package com.knoldus.leader_board.application

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.SystemMaterializer

import scala.concurrent.{ExecutionContextExecutor, Future}

trait AllTimeDataOnAPI {
  def pushAllTimeDataOnAPI(implicit system: ActorSystem, materializer: SystemMaterializer,
                           executionContext: ExecutionContextExecutor): Future[Http.ServerBinding]
}
