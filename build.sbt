name := "twitter-flink"
version := "1.0"

scalaVersion := "2.11.7"
val flinkVersion = "1.0.0"

libraryDependencies ++= Seq(
  "org.apache.flink"          %% "flink-scala"                   % flinkVersion,
  "org.apache.flink"          %% "flink-streaming-scala"         % flinkVersion,
  "org.apache.flink"          %% "flink-clients"                 % flinkVersion,
  "org.apache.flink"          %% "flink-connector-twitter"       % flinkVersion,
  "org.apache.flink"          %% "flink-connector-elasticsearch" % flinkVersion,
  "joda-time"                 %  "joda-time"                     % "2.9.2",
  "org.apache.httpcomponents" %  "httpclient"                    % "4.5.1" //bug in Apache HttpClient 4.2
)

fork in run := true