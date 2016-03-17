package flink

import java.net.InetAddress
import java.util

import com.typesafe.config.ConfigFactory
import flink.parsers.JsonTweetParser
import org.apache.flink.api.common.functions.RuntimeContext
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.streaming.connectors.elasticsearch.{ElasticsearchSink, IndexRequestBuilder}
import org.apache.flink.streaming.connectors.twitter.TwitterSource
import org.elasticsearch.action.index.IndexRequest
import org.elasticsearch.client.Requests
import org.elasticsearch.common.transport.{InetSocketTransportAddress, TransportAddress}
import org.joda.time.DateTime

object TwitterStream extends App {

  val appConfiguration = ConfigFactory.load()

  val esConfiguration = new util.HashMap[String, String]
  esConfiguration.put("bulk.flush.max.actions", "1")
  esConfiguration.put("cluster.name", appConfiguration.getString("ElasticSearchCluster"))

  val esTransports = new util.ArrayList[TransportAddress]
  esTransports.add(new InetSocketTransportAddress(InetAddress.getByName(appConfiguration.getString("ElasticSearchHost")), appConfiguration.getInt("ElasticSearchPort")))

  val esPopularLanguagesSink = new ElasticsearchSink(esConfiguration, esTransports, new IndexRequestBuilder[(DateTime, String,Int)] {
    override def createIndexRequest(element: (DateTime,String,Int), ctx: RuntimeContext): IndexRequest = {
      val json = new util.HashMap[String, AnyRef]
      json.put("timestamp", element._1.asInstanceOf[AnyRef])
      json.put("language", element._2.asInstanceOf[AnyRef])
      json.put("cnt", element._3.asInstanceOf[AnyRef])
      Requests.indexRequest.index("twitter").`type`("popular-languages").source(json)
    }
  })


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


