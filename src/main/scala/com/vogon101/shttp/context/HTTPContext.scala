package com.vogon101.shttp.context

import com.vogon101.shttp.requests.HTTPRequest
import com.vogon101.shttp.response.HTTPResponse

import scala.collection.immutable.Stack

/**
  * Created by Freddie on 12/03/2016.
  */
class HTTPContext {

  private var _cookies: Map[String, String] = Map()

  private var _responseLog: Stack[HTTPResponse] = Stack()

  def log = _responseLog

  def cookies = _cookies

  def execute[R <: HTTPResponse](request: HTTPRequest[R]): R = {

    request.addCookies(_cookies)

    val res = request.execute()

    _responseLog = log.push(res)

    _cookies ++= res.cookies

    res

  }

}
