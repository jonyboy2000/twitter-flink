package sinks

import org.apache.flink.streaming.api.functions.sink.RichSinkFunction
import org.apache.flink.streaming.connectors.elasticsearch.{IndexRequestBuilder, ElasticsearchSink}
import org.elasticsearch.common.transport.TransportAddress
import org.joda.time.DateTime

/**
  * Created by thomas on 18/3/16.
  */
class Sink(userConfig: Map[String, String], indexRequestBuilder: IndexRequestBuilder[(DateTime, String,Int)] )
  extends ElasticsearchSink(userConfig: Map[String, String], indexRequestBuilder: IndexRequestBuilder[(DateTime, String,Int)]){

}
