name := "twitter-flink"

version := "1.0.0"

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  "com.rethinkdb" % "rethinkdb-driver" % "2.2-beta-1",

  "org.apache.flink" %% "flink-scala" % "0.10.1",
  "org.apache.flink" %% "flink-streaming-scala" % "0.10.1",
  "org.apache.flink" %% "flink-clients" % "0.10.1",
  "org.apache.flink" %% "flink-connector-twitter" % "0.10.1",
  "org.apache.httpcomponents" % "httpclient" % "4.5.1"
)

fork in run := true