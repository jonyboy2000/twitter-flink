package flink

import flink.parsers.JsonTweetParser
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.streaming.connectors.twitter.TwitterSource
import org.joda.time.DateTime
import sinks.ElasticsearchSinks

object TwitterStream extends App with ElasticsearchSinks {

  val streamEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
  val streamSource = streamEnvironment.addSource(new TwitterSource("config/twitter/twitter-auth.properties"))


  val tweets = streamSource
    .flatMap(new JsonTweetParser).name("Filtering tweets")
    .filter(_.text.nonEmpty)

  val tweets2 = tweets
    .map(tweet => (tweet.userLang, 1))
    .keyBy(0)
    .timeWindow(Time.seconds(5))
    .sum(1)
    .map(x=> (DateTime.now(),x._1,x._2))
    .addSink(esPopularLanguagesSink)

  streamEnvironment.execute("Twitter Stream")
}


