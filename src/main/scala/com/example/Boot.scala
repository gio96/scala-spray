package com.example

import akka.actor.{ActorSystem, Props}
import akka.io.IO
import spray.can.Http
import akka.pattern.ask
import akka.util.CompactByteString.empty.compact
import akka.util.Timeout
import net.liftweb.json._
import net.liftweb.json.Serialization.write

import scala.concurrent.duration._

object Boot extends App {

  implicit val formats = DefaultFormats

  /*val p = Persona("hola", "prueba");
  val jsonString = write(p)
  println(jsonString)*/

  // we need an ActorSystem to host our application in
  implicit val system = ActorSystem("on-spray-can")

  // create and start our service actor
  val service = system.actorOf(Props[MyServiceActor], "demo-service")

  implicit val timeout = Timeout(5.seconds)
  // start a new HTTP server on port 8080 with our service actor as the handler
  IO(Http) ? Http.Bind(service, interface = "localhost", port = 8080)



}
