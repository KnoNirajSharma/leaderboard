
name := "leaderboard-api"


version := "0.1"

scalaVersion := "2.12.10"
enablePlugins(GatlingPlugin)

libraryDependencies += "io.gatling.highcharts" % "gatling-charts-highcharts" % "3.0.0" % "test,it"
libraryDependencies += "io.gatling" % "gatling-test-framework" % "3.0.0" % "test,it"

libraryDependencies += "com.typesafe.sbt" % "sbt-interface" % "0.13.15"
