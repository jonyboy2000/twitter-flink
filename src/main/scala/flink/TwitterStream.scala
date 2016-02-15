package flink


import flink.parsers.JsonLanguageParsers
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.connectors.twitter.TwitterSource

object TwitterStream extends App {

  val streamEnvironment = StreamExecutionEnvironment.getExecutionEnvironment

//  val elasticsearchHost = "127.0.0.1"
//  val elasticsearchPort = 9300
  val streamSource = streamEnvironment.addSource(new TwitterSource("config/twitter/twitter-auth.properties"))

  val tweets = streamSource
    .flatMap(new JsonLanguageParsers).name("Filtering languages")
    .filter(_.nonEmpty)
    .map((_, 1))
    .keyBy(0)
    .sum(1)
    .map((1,_))
//    .addSink(new LanguagesESSink(elasticsearchHost, elasticsearchPort))

  tweets.print
  streamEnvironment.execute("Twitter Stream")
}

class LanguagesESSink(host: String, port: Int)
  extends ElasticsearchUpsertSink[(String, Int)](
    host,
    port,
    "elasticsearch_thomas",
    "twitter",
    "popular-languages") {

  override def insertJson(r: (String, Int)): Map[String, AnyRef] = {
    Map(
      "language" -> r._1.asInstanceOf[AnyRef],
      "cnt" -> r._2.asInstanceOf[AnyRef]
    )
  }

  override def updateJson(r: (String, Int)): Map[String, AnyRef] = {
    Map[String, AnyRef](
      "cnt" -> r._2.asInstanceOf[AnyRef]
    )
  }

  override def indexKey(r: (String, Int)): String = {
    r._1.toString
  }
}

