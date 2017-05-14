name := "maybe"

scalaVersion := "2.12.1"

scalacOptions in Global ++= Seq(
  "-Xfatal-warnings",
  "-unchecked",
  "-feature",
  "-deprecation",
  "-language:higherKinds",
  "-language:implicitConversions",
  "-language:experimental.macros"
)

libraryDependencies ++= Seq(
  "org.typelevel" %% "cats" % "0.9.0",

  // Test deps
  "org.scalacheck" %% "scalacheck" % "1.13.4" % "test",
  "com.github.alexarchambault" %% "scalacheck-shapeless_1.13" % "1.1.3" % "test",
  "org.scalatest" %% "scalatest" % "3.0.0" % "test"
)
