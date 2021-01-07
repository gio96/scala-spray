package com.example.infrastructure.entrypoints.controllers

import akka.actor.Actor
import akka.pattern.ask
import akka.util.Timeout
import com.example.application.Boot.serviceMultiplicacion
import com.example.Operaciones
import com.example.domain.model.entities.SumaJsonMarshaller.Suma
import com.example.domain.usecase.OperacionesUseCase
import spray.http.MediaTypes._
import spray.http.StatusCodes.InternalServerError
import spray.routing._
import spray.util.LoggingContext

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.DurationInt
import scala.util.{Failure, Success}


// we don't implement our route structure directly in the service actor because
// we want to be able to test it independently, without having to spin up an actor


class MyServiceActor extends Actor with MyService {


  //TODO manejador de Excepciones
  implicit def myExceptionHandler(implicit log: LoggingContext) =
    ExceptionHandler {
      case e: ArithmeticException =>
        requestUri { uri =>
          log.warning("Request to {} could not be handled normally", uri)
          complete(InternalServerError, "Bad numbers, bad result!!!")
        }
    }


  // the HttpService trait defines only one abstract member, which
  // connects the services environment to the enclosing actor or test
  def actorRefFactory = context

  // this actor only runs our route, but you could add
  // other things here, like request stream processing
  // or timeout handling
  def receive = runRoute(myRoute)
}

/*class MultiplicacionActor(value1:Double, value2:Double) extends Actor {
  override def receive: Receive = {
    case Int => sender ! 10
    case ctx: RequestContext => ctx.complete(Operaciones.multiplicacion(3, 5).toString)
  }
}*/

/*class MultiplicacionActor() extends Actor {
  override def receive: Receive = {
    case Int => sender ! 10
    case ctx: RequestContext => ctx.complete(Operaciones.multiplicacion(3, 5).toString)
  }
}*/

class MultiplicacionActor() extends Actor {
  override def receive: Receive = {
    case (numero1: Double, numero2: Double) => sender ! Operaciones.multiplicacion(numero1, numero2)
  }
}


// this trait defines our service behavior independently from the service actor
trait MyService extends HttpService {

  implicit val timeout = Timeout(5.seconds)

  val ERROR_DIVISION: String = "No se puede dividir entre 0";

  val myRoute = {
    path("") {
      get {
        respondWithMediaType(`application/json`) {
          complete("hi")

        }
      }
    } ~
      (path("multiplicacion") & parameters('numero1.as[Double], 'numero2.as[Double])) { (numero1, numero2) =>
        get {
          respondWithMediaType(`application/json`) {
            complete {
              (serviceMultiplicacion ? (numero1, numero2)).map(resultado => resultado.toString)
            }
          }
        }
      } ~
      //(path("test") & parameters('color, 'backgroundColor)) { (color, backgroundColor) =>
      (path("resta") & parameters('numero1.as[Double], 'numero2.as[Double])) { (numero1, numero2) =>
        get {
          respondWithMediaType(`application/json`) {
            complete(new OperacionesUseCase().resta(numero1, numero2).toString)
          }
        }
      } ~
      path("suma") {
        post {
          entity(as[Suma]) { data =>
            //println(s"${data.numero1} y ${data.numero2}")
            ctx =>
              Operaciones.suma(data.numero1, data.numero2).onComplete {
                case Success(value) => ctx.complete(value.toString)
                //case Failure(cause) => Failure(ctx.(new IllegalStateException(cause)))
                case Failure(cause) => ctx.failWith(new IllegalStateException(cause))
              }
          }
        }
      } ~
      (path("division") & parameters('numero1.as[Double], 'numero2.as[Double])) { (numero1, numero2) =>
        get {
          respondWithMediaType(`application/json`) {
            ctx =>
              Operaciones.division(numero1, numero2).onComplete {
                case Success(value) => ctx.complete(value.getOrElse(ERROR_DIVISION).toString)
                case Failure(cause) => ctx.failWith(new IllegalStateException(cause))
              }
          }
        }
      } ~
      (path("division2") & parameters('numero1.as[Double], 'numero2.as[Double])) { (numero1, numero2) =>
        get {
          respondWithMediaType(`application/json`) {
            ctx =>
              Operaciones.division4(numero1, numero2) match {
                case Right(value) => ctx.complete(value.toString)
                case Left(cause) => ctx.failWith(new ArithmeticException(cause))
              }
          }
        }
      }
  }
}