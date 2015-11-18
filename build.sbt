name := "PDFExtractionToolkit"

version := "1.0"

scalaVersion := "2.11.6"

sbtVersion := "0.13.7"

resolvers ++= Seq(
  "artifactory.ambitious.tools" at "http://artifactory.ambitious.tools/artifactory/libs-snapshot-local",
  "Sonatype repository" at "https://oss.sonatype.org/content/repositories/snapshots/"
)

unmanagedBase := baseDirectory.value / "lib_unmanaged"

val akkaV = "2.3.9"
val sprayV = "1.3.3"

libraryDependencies ++= Seq(
  "org.scalatest" % "scalatest_2.11" % "2.2.1" % "test",
  "org.scalamock" %% "scalamock-scalatest-support" % "3.2.2" % "test",

  "tabula-extractor" % "tabula-extractor" % "0.7.4-SNAPSHOT",

  "com.github.tototoshi" %% "scala-csv" % "1.2.1",

  "io.spray"            %%  "spray-can"     % sprayV,
  "io.spray"            %%  "spray-routing" % sprayV,
  "io.spray"            %%  "spray-testkit" % sprayV  % "test",
  "com.typesafe.akka"   %%  "akka-actor"    % akkaV,
  "com.typesafe.akka"   %%  "akka-testkit"  % akkaV   % "test",
  "com.typesafe.slick"  %%  "slick"         % "3.1.0",
  "org.xerial"          %   "sqlite-jdbc"   % "3.8.11.2",
  "org.specs2"          %%  "specs2-core"   % "2.3.11" % "test",

  "commons-io" % "commons-io" % "2.4"
)

Revolver.settings: Seq[sbt.Def.Setting[_]]