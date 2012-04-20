import sbt._
import Keys._

object MinimalBuild extends Build {
  lazy val buildVersion =  "2.0"

  lazy val typesafeSnapshot = "Typesafe Snapshots Repository" at "http://repo.typesafe.com/typesafe/snapshots/"
  lazy val typesafe = "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"
  lazy val repo = if (buildVersion.endsWith("SNAPSHOT")) typesafeSnapshot else typesafe

  lazy val root = Project(id = "play-plugins-capsulecrm", base = file("."), settings = Project.defaultSettings).settings(
    version := buildVersion,
    organization := "com.capsulecrm",
    resolvers += repo,
    javacOptions += "-Xlint:unchecked",
    libraryDependencies += "play" %% "play" % buildVersion,
    libraryDependencies += "com.thoughtworks.xstream" % "xstream" % "1.4.2"
  )
}