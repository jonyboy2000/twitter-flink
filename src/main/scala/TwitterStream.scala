import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.connectors.twitter.TwitterSource


object TwitterStream {
  def main(args: Array[String]) {

    val env = StreamExecutionEnvironment.getExecutionEnvironment

    val streamSource = env.addSource(new TwitterSource("config/twitter/twitter-auth.properties"))

    val tweets = streamSource
      .flatMap(new JsonLanguageParsers).name("Filtering languages")
      .filter(_.nonEmpty)
      .map((_,1))
      .keyBy(0)
      .sum(1)


    tweets.print


    env.execute("Twitter Stream")
  }
}