name := "knoldus_leaderboard"

version := "0.1"

scalaVersion := "2.12.6"

scapegoatVersion in ThisBuild := "1.3.8"

enablePlugins(JavaAppPackaging)
enablePlugins(DockerPlugin)
enablePlugins(AshScriptPlugin)

mainClass in Compile := Some("com.knoldus.leader_board.DriverApp")

dockerBaseImage := "openjdk:8-jre-alpine"

coverageExcludedPackages := ".*DriverApp*"

coverageMinimum := 80

coverageFailOnMinimum := true

val akkaHttpVersion="10.1.11"
val akkaVersion="2.6.5"
val jdbcAndLiftJsonVersion="3.4.1"

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
  "com.enragedginger" %% "akka-quartz-scheduler" % "1.8.3-akka-2.6.x"
)
