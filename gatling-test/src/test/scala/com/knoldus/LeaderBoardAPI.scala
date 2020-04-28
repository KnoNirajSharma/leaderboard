package com.knoldus

import com.typesafe.config.ConfigFactory
import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder


class LeaderBoardAPI extends Simulation {

  /**
   * galting test for Knoldus LeaderBoard API .
   */

  val config = ConfigFactory.load("application.conf")

  val url = config.getString("url")


  val httpProtocol: HttpProtocolBuilder = http.baseUrl(url)
  val scene: ScenarioBuilder = scenario("KLB api test")
    .exec(http("request1")
      .get(url).check(status.is(200))
      .check(jsonPath("$..rank").findAll))
    .pause(1)

  setUp(scene.inject(atOnceUsers(500)).protocols(httpProtocol))

}

