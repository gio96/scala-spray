package com.example

import akka.actor.Actor
import net.liftweb.json.DefaultFormats
import net.liftweb.json.Serialization.{read, write}
import spray.http.MediaTypes._
import spray.http.StatusCodes.InternalServerError
import spray.routing._
import spray.util.LoggingContext

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

  implicit val formats = DefaultFormats
  val p = Persona("hola", "prueba");
  /*val algo: Persona = read("""{"nombre":"mary", "apellido":89} """)
  println("sou algo"+ algo)*/
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
    path("json") {
      get {
        respondWithMediaType(`application/json`) {
          //complete("""{"name":"mary", "age":89} """)
          complete(write(p))
        }
      }
    }
    /*path("posta") {
      post {
        entity(read(Persona)) { quiz =>
        }
      }
    }*/
  }
}