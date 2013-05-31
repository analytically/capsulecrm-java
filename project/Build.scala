import sbt._
import Keys._

object Build extends sbt.Build {
  lazy val buildVersion = "1.0.2"
  lazy val playVersion = "2.1.1"

  lazy val root = Project(id = "capsulecrm-java", base = file("."), settings = Project.defaultSettings).settings(
    version := buildVersion,
    organization := "uk.co.coen",
    organizationName := "Coen Recruitment",
    organizationHomepage := Some(new URL("http://www.coen.co.uk")),
    description := "Unofficial Capsule CRM API Java Client",
    startYear := Some(2011),
    resolvers += Resolver.typesafeRepo("releases"),
    scalaVersion := "2.10.1",
    parallelExecution in Test := false,
    testOptions += Tests.Argument(TestFrameworks.JUnit, "-q", "-v"),

    libraryDependencies += "play" %% "play-java" % playVersion,
    libraryDependencies += "com.thoughtworks.xstream" % "xstream" % "1.4.4",
      // testing
    libraryDependencies += "play" %% "play-test" % playVersion % "test",
    libraryDependencies += "org.specs2" %% "specs2" % "1.14" % "test",
    libraryDependencies += "org.jsoup" % "jsoup" % "1.7.2" % "test",
    libraryDependencies += "com.novocode" % "junit-interface" % "0.10-M4" % "test",

    crossPaths := false,
    autoScalaLibrary := false,
    publishMavenStyle := true,
    publishTo <<= version {
      v: String =>
        val nexus = "https://oss.sonatype.org/"
        if (v.trim.endsWith("SNAPSHOT"))
          Some("snapshots" at nexus + "content/repositories/snapshots")
        else
          Some("releases" at nexus + "service/local/staging/deploy/maven2")
    },
    pomExtra := (
      <url>http://coenrecruitment.github.io/capsulecrm-java/</url>
      <licenses>
        <license>
          <name>Apache 2</name>
          <url>http://www.apache.org/licenses/LICENSE-2.0.txt</url>
          <distribution>repo</distribution>
        </license>
      </licenses>
      <scm>
        <url>git@github.com:coenrecruitment/capsulecrm-java.git</url>
        <connection>scm:git:git@github.com:coenrecruitment/capsulecrm-java.git</connection>
      </scm>
      <developers>
        <developer>
          <id>analytically</id>
          <name>Mathias Bogaert</name>
          <url>http://coen.co.uk</url>
        </developer>
      </developers>
      )
  )
}