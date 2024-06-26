ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.3.1"

lazy val root = (project in file("."))
  .settings(
    name := "Proyecto_Final_Final_CicloIII",

    libraryDependencies ++= Seq(
      "com.github.tototoshi" %% "scala-csv" % "1.3.10",
      "io.github.pityka" %% "nspl-awt" % "0.10.0",
      "io.github.pityka" %% "nspl-core" % "0.10.0",
      "io.github.pityka" %% "nspl-saddle" % "0.10.0",
      "org.scalanlp" %% "breeze-viz" % "2.1.0",
      "org.tpolecat" %% "doobie-core" % "1.0.0-RC4",
      "org.tpolecat" %% "doobie-hikari" % "1.0.0-RC4", // estas si son desarrollada en Scala, por eso tienen dos %%
      "com.mysql" % "mysql-connector-j" % "8.2.0"
    )
  )
