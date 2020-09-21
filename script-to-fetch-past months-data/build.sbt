
name := "script-to-fetch-past months-data"

version := "0.1"

scalaVersion := "2.12.6"

scapegoatVersion in ThisBuild := "1.3.8"

coverageExcludedPackages := ".*DriverApp*"

coverageMinimum := 80

coverageFailOnMinimum := true


val jdbcAndLiftJsonVersion = "3.4.1"

libraryDependencies ++= Seq(

  "org.postgresql" % "postgresql" % "42.2.11",
  "org.scalikejdbc" %% "scalikejdbc" % jdbcAndLiftJsonVersion,
  "ch.qos.logback" % "logback-classic" % "1.2.3",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.7.2",
  "com.h2database" % "h2" % "1.4.196",
  "org.scalatest" %% "scalatest" % "3.1.0" % Test,
  "org.mockito" %% "mockito-scala" % "1.11.4" % Test,
  "com.typesafe" % "config" % "1.3.4"
)
