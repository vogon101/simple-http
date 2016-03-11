package com.vogon101.shttp.test

import com.vogon101.shttp.requests.{HTTPFormPostRequest, HTTPPostRequest, HTTPGetRequest}
import com.vogon101.shttp.response.HTTPResponse
import org.apache.http.NameValuePair
import org.apache.http.message.BasicNameValuePair

/**
  * Created by Freddie on 10/03/2016.
  */
object MainTest extends App {

  val response:HTTPResponse = HTTPGetRequest(
    "http://google.com"
  )!

  println(response)

  val respose: HTTPResponse = HTTPFormPostRequest(
    "http://posttestserver.com/post.php/",
    params = List(new BasicNameValuePair("test", "test data"))
  )!

  println(respose.bodyAsString)

}
