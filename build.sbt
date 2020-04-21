lazy val Scala213 = "2.13.1"
lazy val Scala212 = "2.12.11"
lazy val Scala211 = "2.11.12"

lazy val Play28 = "2.8.1"
lazy val Play27 = "2.7.4"
lazy val Play26 = "2.6.25"

lazy val libVersion = "1"

lazy val `play-form-28` = playForm(Play28, libVersion)
lazy val `play-form-27` = playForm(Play27, libVersion)
lazy val `play-form-26` = playForm(Play26, libVersion)

def playForm(playVersion: String, bugVersion: String) = {
  val majorMinorVersion = playVersion.split('.').take(2)
  val libVersion = (majorMinorVersion :+ bugVersion).mkString(".")
  val id = s"play-form-${majorMinorVersion.mkString}"

  Project(id, file(id))
    .settings(publishSettings)
    .settings(
      organization := "com.github.plippe",
      name := "play-form",
      version := libVersion,
      Compile / unmanagedSourceDirectories += baseDirectory.value / "../play-form/src/main/scala",
      Test / unmanagedSourceDirectories += baseDirectory.value / "../play-form/src/test/scala",
      crossScalaVersions := playCrossScalaVersions(playVersion),
      libraryDependencies ++= Seq(
        "com.typesafe.play" %% "play" % playVersion,
        "org.scalatest" %% "scalatest" % "3.1.1" % Test,
        "org.scalatestplus.play" %% "scalatestplus-play" % "4.0.3" % Test
      ),
      wartremoverErrors ++= Warts.all
    )
}

def playCrossScalaVersions(playVersion: String): Seq[String] =
  playVersion match {
    case Play28 => List(Scala213, Scala212)
    case Play27 => List(Scala213, Scala212, Scala211)
    case Play26 => List(Scala212, Scala211)
    case _      => List.empty
  }

def publishSettings = Seq(
  licenses := Seq("Unlicense" -> url("http://unlicense.org")),
  homepage := Some(url("https://github.com/example/project")),
  scmInfo := Some(
    ScmInfo(
      url("https://github.com/plippe/play-form"),
      "scm:git@github.com:plippe/play-form.git"
    )
  ),
  developers := List(
    sbt.librarymanagement.Developer(
      "plippe",
      "Philippe Vinchon",
      "p.vinchon@gmail.com",
      url("https://github.com/plippe")
    )
  ),
  pomIncludeRepository := { _ => false },
  publishTo := sonatypePublishToBundle.value,
  publishMavenStyle := true
)

skip in publish := true
