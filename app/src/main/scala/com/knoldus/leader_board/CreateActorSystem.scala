package com.knoldus.leader_board

import akka.actor.ActorSystem
import akka.stream.SystemMaterializer

import scala.concurrent.ExecutionContextExecutor

trait CreateActorSystem {
  implicit val system: ActorSystem = ActorSystem()
  implicit val materializer: SystemMaterializer = SystemMaterializer(system)
  implicit val executionContext: ExecutionContextExecutor = system.dispatcher
}
