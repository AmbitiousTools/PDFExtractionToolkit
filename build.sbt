name := "PDFExtractionToolkit"

version := "1.0"

scalaVersion := "2.11.6"

sbtVersion := "0.13.7"

resolvers ++= Seq(
  "artifactory.ambitious.tools" at "http://artifactory.ambitious.tools/artifactory/libs-snapshot-local",
  "Sonatype repository" at "https://oss.sonatype.org/content/repositories/snapshots/"
)

unmanagedBase := baseDirectory.value / "lib_unmanaged"

libraryDependencies ++= Seq(
  "org.scalatest" % "scalatest_2.11" % "2.2.1" % "test",

  "tabula-extractor" % "tabula-extractor" % "0.7.4-SNAPSHOT"
)
