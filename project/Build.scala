import sbt._
import Keys._

object Build extends sbt.Build {
  lazy val buildVersion = "1.1.0"

  lazy val root = Project(id = "capsulecrm-java", base = file("."), settings = Project.defaultSettings)
    .settings(net.virtualvoid.sbt.graph.Plugin.graphSettings: _*)
    .settings(
    shellPrompt := ShellPrompt.buildShellPrompt,
    version := buildVersion,
    organization := "uk.co.coen",
    organizationName := "Coen Recruitment",
    organizationHomepage := Some(new URL("http://www.coen.co.uk")),
    description := "Unofficial Capsule CRM API Java Client",
    startYear := Some(2011),
    resolvers += Resolver.typesafeRepo("releases"),
    scalaVersion := "2.10.3",
    parallelExecution in Test := false,
    testOptions += Tests.Argument(TestFrameworks.JUnit, "-q", "-v"),

    libraryDependencies += "com.google.guava" % "guava" % "15.0",
    libraryDependencies += "joda-time" % "joda-time" % "2.3",
    libraryDependencies += "org.slf4j" % "slf4j-api" % "1.7.5",
    libraryDependencies += "com.typesafe" % "config" % "1.0.2",

    libraryDependencies += "com.ning" % "async-http-client" % "1.7.22",
    libraryDependencies += "com.thoughtworks.xstream" % "xstream" % "1.4.6",

    // testing
    libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.0.13" % "test",
    libraryDependencies += "org.jsoup" % "jsoup" % "1.7.3" % "test",
    libraryDependencies += "org.easytesting" % "fest-assert" % "1.4",
    libraryDependencies += "com.novocode" % "junit-interface" % "0.10" % "test",

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
      <issueManagement>
        <system>GitHub Issues</system>
        <url>https://github.com/coenrecruitment/capsulecrm-java/issues</url>
      </issueManagement>
      <ciManagement>
        <system>Travis CI</system>
        <url>https://travis-ci.org/coenrecruitment/capsulecrm-java</url>
      </ciManagement>
      <licenses>
        <license>
          <name>Apache 2</name>
          <url>http://www.apache.org/licenses/LICENSE-2.0.txt</url>
          <distribution>repo</distribution>
        </license>
      </licenses>
      <scm>
        <url>https://github.com/coenrecruitment/capsulecrm-java</url>
        <connection>scm:git:git@github.com:coenrecruitment/capsulecrm-java.git</connection>
        <developerConnection>scm:git:git@github.com:coenrecruitment/capsulecrm-java.git</developerConnection>
      </scm>
      <developers>
        <developer>
          <id>analytically</id>
          <name>Mathias Bogaert</name>
          <url>http://twitter.com/analytically</url>
          <organization>Coen Recruitment</organization>
          <organizationUrl>http://www.coen.co.uk</organizationUrl>
        </developer>
      </developers>
      )
  )
}

// Shell prompt which show the current project and git branch
object ShellPrompt {
  object devnull extends ProcessLogger {
    def info(s: => String) {}
    def error(s: => String) {}
    def buffer[T](f: => T): T = f
  }

  val buildShellPrompt = {
    val LGREEN = "\033[1;32m"
    val LBLUE = "\033[01;34m"

    (state: State) => {
      val currProject = Project.extract(state).currentProject.id
      if (System.getProperty("sbt.nologformat", "false") != "true") {
        def currBranch = (
          ("git status -sb" lines_! devnull headOption)
            getOrElse "-" stripPrefix "## "
          )

        "%s%s%s:%s%s%s » ".format(LBLUE, currProject, scala.Console.WHITE, LGREEN, currBranch, scala.Console.WHITE)
      }
      else {
        "%s%s%s » ".format(LBLUE, currProject, scala.Console.WHITE)
      }
    }
  }
}
