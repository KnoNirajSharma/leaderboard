name := "knoldus_leaderboard"

version := "0.1"

scalaVersion := "2.12.6"

libraryDependencies += "com.typesafe.akka" %% "akka-http"   % "10.1.11"

libraryDependencies += "com.typesafe.akka" %% "akka-stream" % "2.6.1"

libraryDependencies += "net.liftweb" %% "lift-json" % "3.4.1"

libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.5.13"

libraryDependencies += "org.postgresql" % "postgresql" % "42.2.11"

libraryDependencies += "org.scalikejdbc" %% "scalikejdbc" % "3.4.1"

libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.2.3"

enablePlugins(ScalikejdbcPlugin)

libraryDependencies += "org.scalatest" %% "scalatest" % "3.1.0" % Test

libraryDependencies += "org.mockito" %% "mockito-scala" % "1.11.4" % Test

libraryDependencies += "com.h2database" % "h2" % "1.4.196"
