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
  "org.apache.hadoop" % "hadoop-yarn-api" % "2.2.0",
  "org.apache.hadoop" % "hadoop-mapreduce-client-core" % "2.2.0",
  "org.apache.hadoop" % "hadoop-mapreduce-client-jobclient" % "2.2.0",
  "org.apache.hadoop" % "hadoop-mapreduce-client-jobclient" % "2.6.0",
  "org.apache.hadoop" % "hadoop-mapreduce-client-shuffle" % "2.6.0",
  "org.apache.hadoop" % "hadoop-mapreduce-client-common" % "2.6.0",
  "org.apache.hadoop" % "hadoop-mapreduce-client-app" % "2.6.0",
  "org.mongodb.mongo-hadoop" % "mongo-hadoop-core" % "1.4.2"
)

resolvers += "Spark Packages Repo" at "http://dl.bintray.com/spark-packages/maven"

// resolvers += Resolver.mavenLocal
