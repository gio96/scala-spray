package com.example.application

import akka.actor.{ActorSystem, Props}
import akka.io.IO
import akka.pattern.ask
import akka.util.Timeout
import com.example.infrastructure.entrypoints.controllers.{MultiplicacionActor, MyServiceActor}
import com.google.inject.{Guice, Injector}
import spray.can.Http

import scala.concurrent.duration.DurationInt
import akka.actor.ActorSystem
import com.example.domain.usecase.OperacionesUseCase
//import akka.http.scaladsl.Http
import com.example.domain.model.gateway.OperacionesGateway
import com.example.infrastructure.drivenadapters.OperacionesGatewayImpl
import com.google.inject.{AbstractModule, Guice}

object Boot extends App {

  implicit val injector: Injector = Guice.createInjector(new AbstractModule() {
    override def configure() {
      //bind(classOf[BankRepository]).to(classOf[BankRepositoryImpl])
      bind(classOf[OperacionesGateway]).to(classOf[OperacionesGatewayImpl])
      //bind(classOf[OperacionesGateway]).to(classOf[OperacionesGatewayImpl])
    }
  })

  //private val bankService = injector.getInstance(classOf[OperacionesUseCase])

  //val injector = Guice.createInjector(new GuiceDemo())

  val prueba = injector.getInstance(classOf[OperacionesUseCase])
  private val operacionService = injector.getInstance(classOf[MyServiceActor])

  println(prueba.resta(8,5))

  // we need an ActorSystem to host our application in
  implicit val system = ActorSystem("on-spray-can")

  // create and start our service actor
  val service = system.actorOf(Props[MyServiceActor], "demo-service")

  val serviceMultiplicacion = system.actorOf(Props[MultiplicacionActor], "demo-multipliacion")

  //val injector = Guice.createInjector(new MyModule())



  implicit val timeout = Timeout(5.seconds)
  // start a new HTTP server on port 8080 with our service actor as the handler
  IO(Http) ? Http.Bind(service, interface = "localhost", port = 8080)


}
