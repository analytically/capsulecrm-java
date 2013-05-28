import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

    val appName         = "sample"
    val appVersion      = "1.0-SNAPSHOT"

    val appDependencies = Seq(
      javaCore,
      "com.thoughtworks.xstream" % "xstream" % "1.4.4",
      "com.zestia" % "capsulecrm-java" % "1.0.1"
    )

    val main = play.Project(appName, appVersion, appDependencies).settings(
      // Add your own project settings here
    )
}