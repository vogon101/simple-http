name := "simple-http"

version := "1.0"

scalaVersion := "2.11.8"

mainClass in(Compile, run) := Some( "com.vogon101.shttp.test.MainTest" )

libraryDependencies += "org.apache.httpcomponents" % "httpclient" % "4.5.2"
libraryDependencies += "org.scala-lang.modules" %% "scala-xml" % "1.0.3"
libraryDependencies += "net.liftweb" %% "lift-json" % "2.6.3"
//libraryDependencies += "org.scala-lang.modules" %% "scala-parser-combinators" % "1.0.3"