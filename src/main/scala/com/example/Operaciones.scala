package com.example

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object Operaciones {

  def suma(x: Double, y: Double): Future[Double] = {
    Future {
      x + y
    }
  }

  def resta(x: Double, y: Double) = x - y

  def multiplicacion(x: Double, y: Double) = x * y

  def division(x: Double, y: Double): Future[Option[Double]] = Future {
    if (y == 0) Some(x / y) else None
  }


}
