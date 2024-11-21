import Dependencies._

ThisBuild / scalaVersion     := "2.13.12"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.example"
ThisBuild / organizationName := "example"

ThisBuild / javaOptions ++= Seq(
  "--add-opens", "java.base/sun.nio.ch=ALL-UNNAMED"
)

lazy val root = (project in file("."))
  .settings(
    name := "LearningSpark",
    libraryDependencies ++= Seq(
      "org.apache.spark" %% "spark-sql" % "3.5.3",
      munit % Test
    ),
    fork := true
  )
