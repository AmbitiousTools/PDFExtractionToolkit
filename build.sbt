name := "PDFExtractionToolkit"

version := "1.0"

scalaVersion := "2.11.6"

sbtVersion := "0.13.7"

unmanagedBase := baseDirectory.value / "lib_unmanaged"

libraryDependencies += "org.scalatest" % "scalatest_2.11" % "2.2.1" % "test"
