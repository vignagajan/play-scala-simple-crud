name := """group-eleven"""
organization := "com.inventine"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.6"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test

// JDBC
libraryDependencies ++= Seq(
  jdbc
)

libraryDependencies ++= Seq(
  "mysql" % "mysql-connector-java" % "5.1.41"
)

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.inventine.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.inventine.binders._"
