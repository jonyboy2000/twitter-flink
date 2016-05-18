package sinks

import java.net.InetAddress
import java.util

import com.typesafe.config.ConfigFactory
import org.apache.flink.api.common.functions.RuntimeContext
import org.apache.flink.streaming.connectors.elasticsearch.{IndexRequestBuilder, ElasticsearchSink}
import org.elasticsearch.action.index.IndexRequest
import org.elasticsearch.client.Requests
import org.elasticsearch.common.transport.{InetSocketTransportAddress, TransportAddress}
import org.joda.time.DateTime

trait ElasticsearchSinks {

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

}
