package com.vogon101.shttp.requests

import com.vogon101.shttp.response.HTTPResponse
import com.vogon101.shttp.utils.HTTPRequestConfig
import org.apache.http.client.methods.{CloseableHttpResponse, HttpGet}
import org.apache.http.impl.client.DefaultHttpClient

/**
  * Class for a GET request to an HTTP Host
  *
  * @param url     The url to send the request to
  * @param rf      A factory to create the response objects with
  * @param config  The config object for the request
  * @param cookies Map of cookies to send with the request
  * @param headers Map of headers to send with the request
  * @tparam R The type of response object
  */
case class HTTPGetRequest[R <: HTTPResponse]
(
  override val url: String,
  rf: (CloseableHttpResponse) => R = HTTPResponse.HTTPResponseFactory[R] _,
  config: HTTPRequestConfig = new HTTPRequestConfig( ),
  cookies: Map[String, String] = Map( ),
  headers: Map[String, String] = Map( )
) extends HTTPRequest( url, rf ) {

  //TODO: GetResource
  //TODO: Stateful context

  /**
    * Execute the request
    *
    * @return The response from the server of the specified type
    */
  def execute( ): R = {
    //Build a get request
    val get = new HttpGet( url )
    //Set the config
    get.setConfig( config.build )
    //Set the headers (Uses the implicit map2HeaderList)
    get.setHeaders( headers )
    //Create the client
    val client = new DefaultHttpClient( )
    //Set the cookies (Uses the implicit map2CookieStore)
    client.setCookieStore( cookies )
    //Sent the request and create a response from it
    responseFactory( client.execute( get ) )
  }


}
