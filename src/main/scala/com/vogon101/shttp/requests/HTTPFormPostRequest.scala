package com.vogon101.shttp.requests

import com.vogon101.shttp.response.HTTPResponse
import com.vogon101.shttp.utils.HTTPRequestConfig
import org.apache.http.Consts
import org.apache.http.client.entity.UrlEncodedFormEntity
import org.apache.http.client.methods.{CloseableHttpResponse, HttpPost}
import org.apache.http.impl.client.DefaultHttpClient
import org.apache.http.message.BasicNameValuePair

import scala.collection.JavaConversions

/**
  * Class for the POST of a form to an HTTP Host
  *
  * @param url     The url to send the request to
  * @param rf      A factory to create the response objects with
  * @param params  The parameters of the form in name->value form
  * @param config  The config object for the request
  * @param cookies Map of cookies to send with the request
  * @param headers Map of headers to send with the request
  * @tparam R The type of response object
  */
case class HTTPFormPostRequest[R <: HTTPResponse]
(
  override val url: String,
  rf: (CloseableHttpResponse) => R = HTTPResponse.HTTPResponseFactory[R] _,
  params: Map[String, String],
  config: HTTPRequestConfig = new HTTPRequestConfig( ),
  cookies: Map[String, String] = Map( ),
  headers: Map[String, String] = Map( )
) extends HTTPRequest( url, rf ) {

  /**
    * Execute the request
    *
    * @return The response from the server of the specified type
    */
  def execute( ) = {
    //Build a post request
    val post = new HttpPost( url )
    //Set the config
    post.setConfig( config.build )
    //Set the application type
    post.setHeader( "Content-type", "application/x-www-form-urlencoded" )
    //Set the headers (Uses the implicit map2HeaderList)
    post.setHeaders( headers )
    //Set the main body to an HTTP form
    post.setEntity(
      new UrlEncodedFormEntity(
        JavaConversions.asJavaCollection(
          params.map {
            case (n, v) => new BasicNameValuePair( n, v )
          }
        ),
        Consts.UTF_8
      )
    )
    //Create the client
    val client = new DefaultHttpClient( )
    //Set the cookies (Uses the implicit map2CookieStore)
    client.setCookieStore( cookies )
    //Sent the request and create a response from it
    responseFactory( client.execute( post ) )
  }

}
