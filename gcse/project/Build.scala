import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

    val appName         = "capsulecrm-gcse"
    val appVersion      = "1.0-SNAPSHOT"

    val appDependencies = Seq(
      javaCore,
      "uk.co.coen" % "capsulecrm-java" % "1.3.0"
    )

    val main = play.Project(appName, appVersion, appDependencies).settings(
      // Add your own project settings here
    )
}
