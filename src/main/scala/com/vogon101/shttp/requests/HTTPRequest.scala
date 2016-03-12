package com.vogon101.shttp.requests

import java.net.URI

import com.vogon101.shttp.response.HTTPResponse
import org.apache.http.Header
import org.apache.http.client.CookieStore
import org.apache.http.client.methods.CloseableHttpResponse
import org.apache.http.impl.client.BasicCookieStore
import org.apache.http.impl.cookie.BasicClientCookie
import org.apache.http.message.BasicHeader

/**
  * Base class for an HTTP request to a server
  *
  * @param url             The url to send the request to
  * @param responseFactory A factory to create the response objects with
  * @tparam R The type of response object
  */
abstract class HTTPRequest[R <: HTTPResponse]( val url: String, val responseFactory: (CloseableHttpResponse) => R, private var  _cookies: Map[String, String]) {

  /**
    * Execute the request
    *
    * @return The response from the server of the specified type
    */
  def ! : R = execute( )

  /**
    * Execute the request
    *
    * @return The response from the server of the specified type
    */
  def execute( ): R

  /**
    * Turns a String->String Map into a CookieStore for use with the http library
    *
    * @param cookies name->value Map of cookies
    * @return
    */
  implicit def map2CookieStore( cookies: Map[String, String] ): CookieStore = {
    val cookieStore = new BasicCookieStore( )
    if( cookies.nonEmpty ) {
      cookies.foreach {
        case (n, v) => {
          //println(s"$n=$v")
          val c = new BasicClientCookie( n, v )
          c.setDomain( new URI( url ).getHost )
          cookieStore.addCookie( c )
        }
      }
    }
    cookieStore
  }

  /**
    * Turns a String->String Map into a Array[Header] for use with the http library
    *
    * @param headers name->value Map of headers
    * @return
    */
  implicit def map2HeaderList( headers: Map[String, String] ): Array[Header] = {
    headers.map {
      case (name, value) => new BasicHeader( name, value )
    }.toList.toArray
  }

  def cookies = _cookies

  def addCookies(cs: Map[String, String]) = _cookies ++= cs

}
