package com.vogon101.shttp.response

import net.liftweb.json
import org.apache.http.client.methods.CloseableHttpResponse

/**
  * Class to store a response from an HTTPRequest {@see HTTPRequest} whose body data is JSON
  *
  * @param response    The response object from apache HTTPClient
  * @param preReadText If the stream has already been read, use this to prevent IOExceptions, passing the string in
  */
class JSONResponse( response: CloseableHttpResponse, preReadText: Option[String] = None )
  extends HTTPResponse( response, preReadText ) {

  /**
    * Error value to give when parsing the stream failed
    */
  override val errorValue =
    """{"error": "Error when parsing stream or JSON"}"""

  /**
    * Gives the body as a JSON object (Uses lift framework)
    */
  lazy val bodyAsJSON = try {
    json.parse( bodyAsString )
  }
  catch {
    case e: Exception => json.parse( errorValue )
  }

}


object JSONResponse {

  /**
    * Create a new JSONResponse
    *
    * @param r The CloseableHttpResponse to load from
    * @return A new HTTPResponse object
    */
  def apply( r: CloseableHttpResponse ) = JSONResponseFactory( r )

  /**
    * Create a new JSONResponse
    *
    * @param r The CloseableHttpResponse to load from
    * @return A new HTTPResponse object
    */
  def JSONResponseFactory( r: CloseableHttpResponse ) = new JSONResponse( r )

}
