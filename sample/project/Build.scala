import sbt._
import Keys._
import PlayProject._

object Build extends sbt.Build {

    val appName         = "sample"
    val appVersion      = "1.0-SNAPSHOT"

    val appDependencies = Seq(
      "com.thoughtworks.xstream" % "xstream" % "1.4.3",
      "com.capsulecrm" % "play-plugins-capsulecrm" % "2.0"
    )

    val main = PlayProject(appName, appVersion, appDependencies, mainLang = JAVA).settings(
      // Add your own project settings here
    )

}
