package com.vogon101.shttp.utils

import org.apache.http.client.config.RequestConfig

/**
  * Class to store configuration details for an HTTPRequest
  *
  * @param timeout Timeout time
  */
class HTTPRequestConfig( timeout: Int = 1000 ) {

  lazy val build = RequestConfig.custom( )
    .setSocketTimeout( timeout )
    .setConnectTimeout( timeout )
    .setConnectionRequestTimeout( timeout )
    .build( )

}


/**
  * Class for converting Apache's RequestConfig to an HTTPRequestConfig for simple-HTTP
  *
  * @param config The config to use
  */
class HTTPRequestConfigAdapter( config: RequestConfig ) extends HTTPRequestConfig {

  override lazy val build = config

  /**
    * Class for converting Apache's RequestConfig to an HTTPRequestConfig for simple-HTTP
    *
    * @param configBuilder The config builder to build from
    */
  def this( configBuilder: RequestConfig.Builder ) = this( configBuilder.build( ) )

}
