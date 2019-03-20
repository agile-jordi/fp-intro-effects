ThisBuild / scalaVersion := "2.12.7"
ThisBuild / organization := "com.agilogy"

lazy val fpEffectsIntro = (project in file("."))
  .settings(
    name := "fp-effects-intro",
    scalaVersion := "2.12.7",
    scalacOptions ++= List("-Ypartial-unification", "-Yrangepos", "-Ywarn-unused-import"),
    addCompilerPlugin(scalafixSemanticdb),
    libraryDependencies += "org.typelevel" %% "cats-core" % "1.6.0",
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.5" % "test"
  )

addCommandAlias("fix", "all compile:scalafix test:scalafix")