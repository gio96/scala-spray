package com.example.application

import akka.actor.{ActorSystem, Props}
import akka.io.IO
import akka.pattern.ask
import akka.util.Timeout
import com.example.entrypoints.controllers.{MultiplicacionActor, MyServiceActor}
import spray.can.Http

import scala.concurrent.duration.DurationInt

object Boot extends App {

  /*val p = Persona("hola", "prueba");
  val jsonString = write(p)
  println(jsonString)*/

  // we need an ActorSystem to host our application in
  implicit val system = ActorSystem("on-spray-can")

  // create and start our service actor
  val service = system.actorOf(Props[MyServiceActor], "demo-service")

  val serviceMultiplicacion = system.actorOf(Props[MultiplicacionActor], "demo-multipliacion")

  implicit val timeout = Timeout(5.seconds)
  // start a new HTTP server on port 8080 with our service actor as the handler
  IO(Http) ? Http.Bind(service, interface = "localhost", port = 8080)


}
