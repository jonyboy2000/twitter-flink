# Twitter-flink

Streaming tweets via Flink

### Configuration

Twitter anthentication configuration must be stored in 
`config/twitter/twitter-auth.properties`

See https://ci.apache.org/projects/flink/flink-docs-master/apis/streaming/connectors/twitter.html
 
 curl -XPUT "http://localhost:9200/twitter/_mapping/popular-languages" -d'
 {
  "popular-languages" : {
    "properties" : {
    	"timestamp": {"type": "date"},
       "language": {"type": "string"},
       "cnt": {"type": "long"}
     }
  } 
 }'