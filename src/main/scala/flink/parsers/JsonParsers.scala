package flink.parsers

import org.apache.flink.streaming.connectors.json.JSONParseFlatMap
import org.apache.flink.util.Collector

import scala.util.Try

class JsonCityParsers extends JSONParseFlatMap[String,String] {

  override def flatMap(value: String, out: Collector[String]): Unit = {
    if (Try(getString(value, "lang")).getOrElse("") == "en") {
      out.collect(Try(getString(value, "place.name")).getOrElse(""))
    }
  }
}

class JsonLanguageParsers extends JSONParseFlatMap[String,String] {

  override def flatMap(value: String, out: Collector[String]): Unit = {
      out.collect(Try(getString(value, "lang")).getOrElse(""))
  }
}