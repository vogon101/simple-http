package com.vogon101.shttp.test

import com.vogon101.shttp.requests.{HTTPFormGetRequest, HTTPFormPostRequest, HTTPPostRequest, HTTPGetRequest}
import com.vogon101.shttp.response.HTTPResponse
import org.apache.http.NameValuePair
import org.apache.http.message.BasicNameValuePair

/**
  * Created by Freddie on 10/03/2016.
  */
object MainTest extends App {

  var response:HTTPResponse = HTTPGetRequest(
    "http://vps.vogonjeltz.com/tester/data.php"
  )!

  println(response.bodyAsString)

  response = HTTPFormGetRequest (
    "http://vps.vogonjeltz.com/tester/get.php",
    params=Map("q"->"hello", "other"->"thing")
  )!

  println(response.bodyAsString)


  response = HTTPFormPostRequest(
    "http://vps.vogonjeltz.com/tester/post.php",
    params = Map("test"->"Test data")
  )!

  println(response.bodyAsString)

  response = HTTPPostRequest(
    "http://vps.vogonjeltz.com/tester/post_body.php",
    body = "Test data"
  )!

  println(response.bodyAsString)

}
