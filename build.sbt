name := "cardio_stress"
version := "1.0"
scalaVersion := "2.11.7"
mainClass := Some("artezio.cardio.StressZones")

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % "1.5.2"  % "provided",
  "org.mongodb.scala" %% "mongo-scala-driver" % "1.1.0",
  "com.stratio.datasource" %% "spark-mongodb" % "0.10.0",
  "org.apache.spark" %% "spark-mllib" % "1.5.2" % "provided",
  "org.mongodb.mongo-hadoop" % "mongo-hadoop-core" % "1.4.2",
  "org.scala-lang" % "scala-compiler" % "2.11.7" % "provided",
  "org.apache.commons" % "commons-lang3" % "3.3.2" % "provided",
  "jline" % "jline" % "2.12.1" % "provided",
  "org.scala-lang.modules" %% "scala-xml" % "1.0.4" % "provided",
  "org.scala-lang.modules" %% "scala-parser-combinators" % "1.0.4" % "provided",
  "org.slf4j" % "slf4j-api" % "1.7.10" % "provided"
)

// META-INF discarding
assemblyMergeStrategy in assembly := {
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case x => MergeStrategy.first
}
