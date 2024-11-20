import sbt._
import Keys._
import sbtassembly.AssemblyPlugin.autoImport._

ThisBuild / scalaVersion := Dependencies.Versions.scala
ThisBuild / organization := "io.metabolic"

lazy val core = (project in file("."))
  .settings(
    name := "core",
    version := "SNAPSHOT",
    scalaVersion := Dependencies.Versions.scala,

    libraryDependencies ++=
        Dependencies.sparkDependencies ++
        Dependencies.awsDependencies ++
        Dependencies.loggingDependencies ++
        Dependencies.commonDependencies ++
        Dependencies.testDependencies,

    dependencyOverrides ++= Dependencies.dependencyOverridesSettings,

    pomIncludeRepository := { _ => false },

    // Assembly settings
    assembly / assemblyMergeStrategy := {
      case PathList("META-INF", xs @ _*) => MergeStrategy.discard
      case _                             => MergeStrategy.first
    },

    // Test settings
    Test / coverageEnabled      := true,
    Test / parallelExecution    := false,
    coverageHighlighting        := true,

    // Compiler options
    scalacOptions ++= Seq("-deprecation", "-unchecked", "-target:jvm-1.8"),

    // Java options
    javaOptions ++= Seq("-Xms512M", "-Xmx2048M", "-XX:+CMSClassUnloadingEnabled"),

    // Publishing settings
    publishMavenStyle := true,
    //publishTo         := sonatypePublishToBundle.value,
    licenses          += ("Apache-2.0", url("https://www.apache.org/licenses/LICENSE-2.0.html")),
    homepage          := Some(url("https://github.com/io.metabolic/core")),
    scmInfo           := Some(
      ScmInfo(
        url("https://github.com/io.metabolic/core"),
        "scm:git@github.com:io.metabolic/core.git"
      )
    ),
    developers        := List(
      Developer(id = "developer-id", name = "Developer Name", email = "email@example.com", url = url("https://developer.website"))
    )
  )

// Uncomment the following lines if you need AWS Glue dependencies
// resolvers += "aws-glue-etl-artifacts" at "https://aws-glue-etl-artifacts.s3.amazonaws.com/release/"
// libraryDependencies += "com.amazonaws" % "AWSGlueETL" % "1.0.0" % Provided