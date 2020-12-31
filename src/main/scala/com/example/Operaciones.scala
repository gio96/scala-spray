package com.example

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.Try

object Operaciones {

  val ERROR_DIVISION: String = "No se puede dividir entre 0";

  def suma(x: Double, y: Double): Future[Double] = {
    Future {
      x + y
    }
  }

  def resta(x: Double, y: Double) = x - y

  def multiplicacion(x: Double, y: Double) = x * y

  def division(x: Double, y: Double): Future[Option[Double]] = Future {
    if (y != 0) Some(x / y) else None
  }

  def division2(x: Double, y: Double): Future[Any] = Future {
    if (y != 0) x / y else new ArithmeticException(ERROR_DIVISION)
  }

  //Mensaje solo funcion con Int
  def division3(x: Int, y: Int): Try[Int] = {
    Try(x / y)
  }

  def division4(x: Double, y: Double): Either[String, Double] = {
    if (y == 0) Left(ERROR_DIVISION)
    else Right(x / y)
  }


}
