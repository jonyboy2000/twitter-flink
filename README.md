# Twitter-flink

Streaming tweets via Flink

### Twitter Configuration

Twitter anthentication configuration must be stored in 
`config/twitter/twitter-auth.properties`  with the following format : 

```
consumerKey=*****
consumerSecret=*****
token=*****
secret=*****
```

See https://ci.apache.org/projects/flink/flink-docs-master/apis/streaming/connectors/twitter.html
 
 
### Elasticsearch Configuration 
 
###### Index creation

```
 curl -XPUT 'http://localhost:9200/twitter/'
```
 
###### Mapping creation

```
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
```