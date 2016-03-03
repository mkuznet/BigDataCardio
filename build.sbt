name := "testScalaProject"

version := "1.0"

mainClass := Some("artezio.mkuznetsova.MainObject")

scalaVersion := "2.11.7"
//scalaVersion := "2.10.4"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % "1.5.2",
  "org.mongodb.scala" %% "mongo-scala-driver" % "1.1.0",
  "com.stratio.datasource" %% "spark-mongodb" % "0.10.0",
  "org.apache.spark" %% "spark-mllib" % "1.5.2",
  "org.mongodb.mongo-hadoop" % "mongo-hadoop-core" % "1.4.2",
  "org.scala-lang" % "scala-compiler" % "2.11.7",
  "org.apache.commons" % "commons-lang3" % "3.3.2",
  "jline" % "jline" % "2.12.1",
  "org.scala-lang.modules" %% "scala-xml" % "1.0.4",
  "org.scala-lang.modules" %% "scala-parser-combinators" % "1.0.4",
  "org.slf4j" % "slf4j-api" % "1.7.10"
)

//resolvers += "Spark Packages Repo" at "http://dl.bintray.com/spark-packages/maven"

// resolvers += Resolver.mavenLocal
