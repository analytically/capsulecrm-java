import sbt._
import Keys._

object Build extends sbt.Build {
  lazy val buildVersion = "1.0.1"
  lazy val playVersion = "2.1.1"

  lazy val root = Project(id = "play-capsulecrm", base = file("."), settings = Project.defaultSettings).settings(
    version := buildVersion,
    organization := "com.zestia",
    resolvers += "Typesafe Releases" at "http://repo.typesafe.com/typesafe/releases/",
    scalaVersion := "2.10.1",
    parallelExecution in Test := false,
    testOptions += Tests.Argument(TestFrameworks.JUnit, "-q", "-v"),
    libraryDependencies += "play" %% "play-java" % playVersion,
    libraryDependencies += "com.thoughtworks.xstream" % "xstream" % "1.4.4",

    // testing
    libraryDependencies += "play" %% "play-test" % playVersion % "test",
    libraryDependencies += "org.specs2" %% "specs2" % "1.14",
    libraryDependencies += "org.jsoup" % "jsoup" % "1.7.2" % "test",
    libraryDependencies += "com.novocode" % "junit-interface" % "0.10-M4" % "test"
  )
}