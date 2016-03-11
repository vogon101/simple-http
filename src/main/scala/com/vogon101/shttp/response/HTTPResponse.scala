package com.vogon101.shttp.response

import java.io.IOException

import org.apache.http.client.methods.CloseableHttpResponse
import org.apache.http.util.EntityUtils

/**
  * Class to store a response from an HTTPRequest {@see HTTPRequest}
  * @param response The response object from apache HTTPClient
  * @param preReadText If the stream has already been read, use this to prevent IOExceptions, passing the string in
  */
class HTTPResponse(val response: CloseableHttpResponse, preReadText:Option[String] = None) {

  /**
    * Error value to give when parsing the stream failed
    */
  val errorValue = "Error in parsing response"

  /**
    * Gives the body of a request as a string
    */
  lazy val bodyAsString =
    if (preReadText.isDefined)
      preReadText.get
    else
      try
        EntityUtils.toString(response.getEntity)
      catch{
        case e: Exception => errorValue
      }

  /**
    * Gets all of the Set-Cookie headers from the request
    * @return Map of the cookies in name->value form
    */
  def cookies:Map[String,String] = {
    var c = Map[String,String]()
    response.getAllHeaders.toList.foreach(H => {
      if (H.getName == "Set-Cookie") {
        c += H.getValue.split("=")(0) -> H.getValue.split("=")(1).split(";")(0)
      }
    })
    c
  }


}


object HTTPResponse {

  /**
    * Create a new HTTP response
    * @param r The CloseableHttpResponse to load from
    * @return A new HTTPResponse object
    */
  def apply(r: CloseableHttpResponse) = new HTTPResponse(r)

  /**
    * Create a new HTTP response
    * @param r The CloseableHttpResponse to load from
    * @return A new HTTPResponse object
    */
  def HTTPResponseFactory[R <: HTTPResponse](r: CloseableHttpResponse) = new HTTPResponse(r).asInstanceOf[R]

}