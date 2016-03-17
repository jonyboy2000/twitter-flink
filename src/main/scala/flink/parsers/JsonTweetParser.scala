package flink.parsers

import domain.Tweet
import org.apache.flink.streaming.connectors.json.JSONParseFlatMap
import org.apache.flink.util.Collector

import scala.util.Try

class JsonTweetParser extends JSONParseFlatMap[String,Tweet] {

  override def flatMap(value: String, out: Collector[Tweet]): Unit = {
    out.collect(
      Tweet(
        Try(getString(value, "created_at")).getOrElse(""),
        Try(getString(value, "text")).getOrElse(""),
        Try(getString(value, "user.name")).getOrElse(""),
        Try(getString(value, "user.lang")).getOrElse(""),
        Try(getString(value, "place.name")).getOrElse(""),
        Try(getString(value, "place.country")).getOrElse("")
      )
    )
  }
}
