import sbt._
import Keys._

object Build extends sbt.Build {
  lazy val buildVersion = "1.0"
  lazy val playVersion = "2.1-SNAPSHOT"

  lazy val root = Project(id = "play-capsulecrm", base = file("."), settings = Project.defaultSettings).settings(
    version := buildVersion,
    organization := "com.zestia",
    resolvers += "Typesafe Snapshots" at "http://repo.typesafe.com/typesafe/snapshots/",
    parallelExecution in Test := false,
    testOptions += Tests.Argument(TestFrameworks.JUnit, "-q", "-v"),
    libraryDependencies += "play" %% "play" % playVersion,
    libraryDependencies += "com.thoughtworks.xstream" % "xstream" % "1.4.3",

    // testing
    libraryDependencies += "play" %% "play-test" % playVersion % "test",
    libraryDependencies += "org.specs2" %% "specs2" % "1.12.1" % "test",
    libraryDependencies += "org.jsoup" % "jsoup" % "1.7.1" % "test",
    libraryDependencies += "com.novocode" % "junit-interface" % "0.10-M1" % "test"
  )
}