package com.vogon101.shttp.response

import org.apache.http.client.methods.CloseableHttpResponse

import scala.xml.{Elem, XML}

/**
  * Class to store a response from an HTTPRequest {@see HTTPRequest} whose body data is XML
  *
  * @param response    The response object from apache HTTPClient
  * @param preReadText If the stream has already been read, use this to prevent IOExceptions, passing the string in
  */
class XMLResponse( response: CloseableHttpResponse, preReadText: Option[String] = None )
  extends HTTPResponse( response, preReadText ) {

  /**
    * Error value to give when parsing the stream failed
    */
  override val errorValue = <error>Error when parsing stream or XML</error>.toString

  /**
    * Gives the body as an XML object (Uses scala.xml)
    */
  lazy val bodyAsXML: Elem =
    try
      XML.loadString( bodyAsString )
    catch {
      case e: Exception => XML.loadString( errorValue )
    }

}


object XMLResponse {

  /**
    * Create a new XMLResponse
    *
    * @param r The CloseableHttpResponse to load from
    * @return A new HTTPResponse object
    */
  def apply( r: CloseableHttpResponse ) = XMLResponseFactory( r )

  /**
    * Create a new XMLResponse
    *
    * @param r The CloseableHttpResponse to load from
    * @return A new HTTPResponse object
    */
  def XMLResponseFactory( r: CloseableHttpResponse ) = new XMLResponse( r )

}
