package com.vogon101.shttp.requests

import com.vogon101.shttp.response.HTTPResponse
import com.vogon101.shttp.utils.HTTPRequestConfig
import org.apache.http.client.methods.CloseableHttpResponse

/**
  * Created by Freddie on 11/03/2016.
  */
class HTTPFormGetRequest[R <: HTTPResponse](
  url: String,
  override val rf: (CloseableHttpResponse) => R = HTTPResponse.HTTPResponseFactory[R] _,
  override val config: HTTPRequestConfig = new HTTPRequestConfig( ),
  override val cookies: Map[String, String] = Map( ),
  override val headers: Map[String, String] = Map( ),
  params: Map[String, String] = Map( )
) extends HTTPGetRequest(
  url + "?" + params.map {
    case (n, v) => s"$n=$v&"
  }.mkString( "" ),
  rf,
  config,
  cookies,
  headers
) {}


object HTTPFormGetRequest {

  def apply[R <: HTTPResponse](
    url: String,
    rf: (CloseableHttpResponse) => R = HTTPResponse.HTTPResponseFactory[R] _,
    config: HTTPRequestConfig = new HTTPRequestConfig( ),
    cookies: Map[String, String] = Map( ),
    headers: Map[String, String] = Map( ),
    params: Map[String, String] = Map( )
  ) = new HTTPFormGetRequest[R]( url, rf, config, cookies, headers, params )

}
