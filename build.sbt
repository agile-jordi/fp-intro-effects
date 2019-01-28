ThisBuild / scalaVersion := "2.12.7"
ThisBuild / organization := "com.agilogy"

lazy val fpEffectsIntro = (project in file("."))
  .settings(
    name := "fp-effects-intro"
  )
