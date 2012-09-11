import sbt._
import Keys._

object Build extends sbt.Build {
  lazy val buildVersion =  "1.0"
  lazy val playVersion = "2.1-SNAPSHOT"

  lazy val typesafeSnapshot = "Typesafe Snapshots Repository" at "http://repo.typesafe.com/typesafe/snapshots/"
  lazy val typesafe = "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"
  lazy val repo = if (playVersion.endsWith("SNAPSHOT")) typesafeSnapshot else typesafe

  lazy val root = Project(id = "play-plugins-capsulecrm", base = file("."), settings = Project.defaultSettings).settings(
    version := buildVersion,
    organization := "com.zestia",
    resolvers += repo,
    libraryDependencies += "play" %% "play" % playVersion,
    libraryDependencies += "com.thoughtworks.xstream" % "xstream" % "1.4.3",

    // testing
    libraryDependencies += "play" %% "play-test" % playVersion,
    libraryDependencies += "org.specs2" %% "specs2" % "1.12.1" % "test",
    libraryDependencies += "org.jsoup" % "jsoup" % "1.6.3" % "test",
    libraryDependencies += "junit" % "junit" % "4.8" % "test"
  )
}