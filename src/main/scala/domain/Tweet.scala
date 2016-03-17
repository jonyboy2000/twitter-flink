package domain

case class Tweet (
                   timestamp: String,
                   text: String = null,
                   userName: String = null,
                   userLang: String = null,
                   city: String = null,
                   country : String = null
                 )
