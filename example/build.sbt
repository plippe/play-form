lazy val root = (project in file("."))
  .enablePlugins(PlayScala)
  .settings(
    scalaVersion := "2.13.1",
    libraryDependencies ++= Seq(
      guice,
      "com.github.plippe" %% "play-form" % "2.8.0-SNAPSHOT",
    )
  )
