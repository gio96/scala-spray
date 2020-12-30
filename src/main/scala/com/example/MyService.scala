package com.example

import akka.actor.Actor
import com.example.PersonaJsonMarshaller.Persona
import spray.http.MediaTypes._
import spray.http.StatusCodes.InternalServerError
import spray.routing._
import spray.util.LoggingContext

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}


// we don't implement our route structure directly in the service actor because
// we want to be able to test it independently, without having to spin up an actor


class MyServiceActor extends Actor with MyService {


  implicit def myExceptionHandler(implicit log: LoggingContext) =
    ExceptionHandler {
      case e: ArithmeticException =>
        requestUri { uri =>
          log.warning("Request to {} could not be handled normally", uri)
          complete(InternalServerError, "Bad numbers, bad result!!!")
        }
    }


  //compact(render(Persona("","")))

  // the HttpService trait defines only one abstract member, which
  // connects the services environment to the enclosing actor or test
  def actorRefFactory = context

  // this actor only runs our route, but you could add
  // other things here, like request stream processing
  // or timeout handling
  def receive = runRoute(myRoute)
}


// this trait defines our service behavior independently from the service actor
trait MyService extends HttpService {

  val myRoute = {
    path("") {
      get {
        respondWithMediaType(`application/json`) { // XML is marshalled to `text/xml` by default, so we simply override here
          /*complete {
            <html>
              <body>
                <h1>Say hello to <i>spray-routing</i> on <i>spray-can</i>!</h1>
              </body>
            </html>
          }*/
          complete("hi")

        }
      }
    }
    //(path("test") & parameters('color, 'backgroundColor)) { (color, backgroundColor) =>
    (path("test") & parameters('color.as[String], 'backgroundColor.as[String])) { (color, backgroundColor) =>
      get {
        respondWithMediaType(`application/json`) {
          complete(s"test $color $backgroundColor")
        }
      }
    }
    path("posta") {
      post {
        entity(as[Persona]) { data =>
          println(s"${data.nombre} y ${data.apellido}")
          //MongoDriverPersisentece.dbFromConnection()
          // .flatMap(algo => MongoDriverPersisentece.simpleInsert(algo))
          /*val mongoDriverPersisentece = new MongoDriverPersisentece
           mongoDriverPersisentece.connection7()
            .flatMap(value => mongoDriverPersisentece.dbFromConnection2(value))
            .flatMap(valor => mongoDriverPersisentece.simpleInsert(valor))
            .onComplete { v =>
              println(s"The original future has returned:")
            }*/
          //complete(Response("MElo", 200))
          //complete(Operaciones.suma(5, 7).map(_.toString))
          /*val operacion = Operaciones.suma(5, 7).onComplete {
            case Success(value) => value
            case Failure(cause) => Failure(new IllegalStateException(cause))
          }*/
          //Operaciones.suma(5, 7).map(valor => complete(valor.toString))
          //complete("")
          //ctx => Operaciones.suma(5, 7).map(valor => ctx.complete(valor.toString))
          ctx =>
            Operaciones.suma(5, 8).onComplete {
              case Success(value) => ctx.complete(value.toString)
              case Failure(cause) => Failure(new IllegalStateException(cause))
            }

        }
      }
    }
  }
}