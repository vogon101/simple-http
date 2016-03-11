package com.vogon101.shttp.requests

import java.net.URI
import com.vogon101.shttp.response.HTTPResponse
import com.vogon101.shttp.utils.HTTPRequestConfig
import org.apache.http.client.methods.{CloseableHttpResponse, HttpPost}
import org.apache.http.entity.StringEntity
import org.apache.http.impl.client.{BasicCookieStore, DefaultHttpClient}
import org.apache.http.impl.cookie.BasicClientCookie

/**
  * Class for a POST request to an HTTP host
  * @param url The url to send the request to
  * @param rf A factory to create the response objects with
  * @param config The config object for the request
  * @param body The body of the request as a string
  * @param bodyMIMEType The MIMEType of the body
  * @param cookies Map of cookies to send with the request
  * @param headers Map of headers to send with the request
  * @tparam R The type of response object
  */
case class HTTPPostRequest[R <: HTTPResponse]
(
  override val url: String,
  rf: (CloseableHttpResponse) => R = HTTPResponse.HTTPResponseFactory[R] _,
  config: HTTPRequestConfig = new HTTPRequestConfig(),
  body: String = "",
  bodyMIMEType: String = "application/text",
  cookies: Map[String, String] = Map(),
  headers: Map[String, String] = Map()
) extends HTTPRequest(url, rf) {

  /**
    * Execute the request
    * @return The response from the server of the specified type
    */
  def execute():R = {
    //Build a post request
    val post = new HttpPost(url)
    //Set the config
    post.setConfig(config.build)
    //Set the application type
    post.setHeader("Content-type", bodyMIMEType)
    //Set the headers (Uses the implicit map2HeaderList)
    post.setHeaders(headers)
    //Set the main body
    post.setEntity(new StringEntity(body))
    //Create the client
    val client = new DefaultHttpClient()
    //Set the cookies (Uses the implicit map2CookieStore)
    client.setCookieStore(cookies)
    //Sent the request and create a response from it
    responseFactory(client.execute(post))
  }


}
