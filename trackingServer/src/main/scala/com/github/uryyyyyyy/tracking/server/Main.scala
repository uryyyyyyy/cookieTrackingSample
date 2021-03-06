package com.github.uryyyyyyy.tracking.server

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.HttpHeader
import akka.http.scaladsl.model.headers.{HttpCookie, HttpOrigin, HttpOriginRange}
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer

import scala.concurrent.Future
import scala.util.Random
import akka.http.scaladsl.model.headers

object Main {
  def main(args: Array[String]): Unit = {

    val random = new Random()

    val port = 8080

    implicit val system = ActorSystem()
    implicit val materializer = ActorMaterializer()
    implicit val ec = system.dispatcher

    val route = path("") {
      get {
        complete("ok")
      }
    } ~ get {
      path("1stPartyTracking.js") {
        getFromResource("1stPartyTracking.js")
      } ~ path("3rdPartyTracking.img") {
        optionalCookie("3rdPartyTrackingKey"){ pair =>
          val trackingID = if (pair.isEmpty){
            val id = random.nextDouble().toString
            println("3rd party cookie. new user: " + id)
            id
          }else{
            val id = pair.get.value
            println("3rd party cookie. same user: " + id)
            id
          }
          setCookie(HttpCookie("3rdPartyTrackingKey", trackingID)){
            getFromResource("tracking.png")
          }
        }
      } ~ path("tracking") {
        parameter('trackingID) { trackingID =>
          println("1st party cookie" + trackingID)
          getFromResource("tracking.png")
        }
      }
    } ~ options {
      path("pre_flight") {
        println("pre flight request")
        val origin = HttpOrigin("http://uryyyyyyy.shake-freek.com")
        val header = headers.`Access-Control-Allow-Origin`(origin)
        val header2 = headers.`Access-Control-Allow-Headers`("Content-Type")
        respondWithHeaders(header, header2){
          complete("request done")
        }
      }
    } ~ post {
      path("pre_flight") {
        println("main request")
        val origin = HttpOrigin("http://uryyyyyyy.shake-freek.com")
        val header = headers.`Access-Control-Allow-Origin`(origin)
        val header2 = headers.`Access-Control-Allow-Headers`("Content-Type")
        respondWithHeaders(header, header2){
          complete("request done")
        }
      }
    }

    val bindingFuture: Future[Http.ServerBinding] = Http().bindAndHandle(route, "0.0.0.0", port)

    println(s"Server online at http://127.0.0.1:${port}/\nPress RETURN to stop...")
    scala.io.StdIn.readLine()

    bindingFuture
      .flatMap(_.unbind())
      .onComplete(_ => system.terminate())
  }
}