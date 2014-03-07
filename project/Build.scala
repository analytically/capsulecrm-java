import sbt._
import Keys._
import sbt.Tests.Setup

object Build extends sbt.Build {
  lazy val buildVersion = "1.2.3"

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
    concurrentRestrictions in Global += Tags.limit(Tags.Test, 1),
    testOptions += Tests.Argument(TestFrameworks.JUnit, "-q"),
    testOptions += Setup(cl =>
      cl.loadClass("org.slf4j.LoggerFactory").
        getMethod("getLogger", cl.loadClass("java.lang.String")).
        invoke(null, "ROOT")
    ),

    libraryDependencies += "com.typesafe" % "config" % "1.2.0",
    libraryDependencies += "com.google.guava" % "guava" % "16.0.1",
    libraryDependencies += "joda-time" % "joda-time" % "2.3",
    libraryDependencies += "com.ning" % "async-http-client" % "1.8.3",
    libraryDependencies += "com.thoughtworks.xstream" % "xstream" % "1.4.7",

    // testing
    libraryDependencies += "org.slf4j" % "slf4j-api" % "1.7.6" % "test",
    libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.1.1" % "test",
    libraryDependencies += "org.jsoup" % "jsoup" % "1.7.3" % "test",
    libraryDependencies += "org.easytesting" % "fest-assert" % "1.4" % "test",
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
    pomExtra :=
      <url>https://github.com/analytically/capsulecrm-java</url>
      <issueManagement>
        <system>GitHub Issues</system>
        <url>https://github.com/analytically/capsulecrm-java/issues</url>
      </issueManagement>
      <ciManagement>
        <system>Travis CI</system>
        <url>https://travis-ci.org/analytically/capsulecrm-java</url>
      </ciManagement>
      <licenses>
        <license>
          <name>Apache 2</name>
          <url>http://www.apache.org/licenses/LICENSE-2.0.txt</url>
          <distribution>repo</distribution>
        </license>
      </licenses>
      <scm>
        <url>https://github.com/analytically/capsulecrm-java</url>
        <connection>scm:git:git@github.com:analytically/capsulecrm-java.git</connection>
        <developerConnection>scm:git:git@github.com:analytically/capsulecrm-java.git</developerConnection>
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
          ("git symbolic-ref --short HEAD" lines_! devnull headOption)
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
