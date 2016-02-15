name := "twitter-flink"

version := "1.0"

scalaVersion := "2.10.4"

libraryDependencies ++= Seq(
  "org.apache.flink" %% "flink-scala"                   % "1.0.0",
  "org.apache.flink" %% "flink-streaming-scala"         % "1.0.0",
  "org.apache.flink" %% "flink-clients"                 % "1.0.0",
  "org.apache.flink" %% "flink-connector-twitter"       % "1.0.0",
  "org.apache.flink" %% "flink-connector-elasticsearch" % "1.0.0",
  "joda-time"        %  "joda-time"                     % "2.9.2"
)

fork in run := true