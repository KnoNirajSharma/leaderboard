import sbtsonar.SonarPlugin.autoImport.sonarProperties

name := "knoldus_leaderboard"

version := "0.1"

scalaVersion := "2.12.6"

scapegoatVersion in ThisBuild := "1.3.8"

cancelable in Global := false

enablePlugins(JavaAppPackaging)
enablePlugins(DockerPlugin)
enablePlugins(AshScriptPlugin)

mainClass in Compile := Some("com.knoldus.leader_board.DriverApp")

dockerBaseImage := "openjdk:8-jre-alpine"

coverageExcludedPackages := ".*DriverApp*"

coverageMinimum := 80

coverageFailOnMinimum := true

val akkaHttpVersion = "10.1.11"
val akkaVersion = "2.6.5"
val jdbcAndLiftJsonVersion = "3.4.1"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
  "com.typesafe.akka" %% "akka-stream" % akkaVersion,
  "net.liftweb" %% "lift-json" % jdbcAndLiftJsonVersion,
  "com.typesafe.akka" %% "akka-testkit" % akkaVersion % Test,
  "org.postgresql" % "postgresql" % "42.2.11",
  "org.scalikejdbc" %% "scalikejdbc" % jdbcAndLiftJsonVersion,
  "ch.qos.logback" % "logback-classic" % "1.2.3",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.7.2",
  "com.h2database" % "h2" % "1.4.196",
  "ch.megard" %% "akka-http-cors" % "0.4.3",
  "com.typesafe.akka" %% "akka-stream-testkit" % akkaVersion % Test,
  "com.typesafe.akka" %% "akka-http-testkit" % akkaHttpVersion % Test,
  "org.scalatest" %% "scalatest" % "3.1.0" % Test,
  "org.mockito" %% "mockito-scala" % "1.11.4" % Test,
  "com.enragedginger" %% "akka-quartz-scheduler" % "1.8.3-akka-2.6.x",
  "org.apache.commons" % "commons-io" % "1.3.2",
  "org.apache.httpcomponents" % "httpclient" % "4.5.11",
  "com.google.api-client" % "google-api-client" % "1.30.9",
  "com.google.oauth-client" % "google-oauth-client-jetty" % "1.30.5",
  "com.google.apis" % "google-api-services-sheets" % "v4-rev1-1.21.0"
)


sonarProperties := Map(
  "sonar.host.url" -> "http://localhost:9001",
  "sonar.projectName" -> "Knoldus Leaderboard",
  "sonar.projectKey" -> "knoldus-leader-board",
  "sonar.sources" -> "src/main/scala",
  "sonar.tests" -> "src/test/scala",
  "sonar.scala.scalastyle.reportPaths" -> "target/scalastyle-result.xml",
  "sonar.scala.coverage.reportPaths" -> "target/scala-2.12/scoverage-report/scoverage.xml",
  "sonar.scala.scapegoat.reportPaths" -> "target/scala-2.12/scapegoat-report/scapegoat-scalastyle.xml"
)
