package com.example.infrastructure.drivenadapters

import com.example.Operaciones.ERROR_DIVISION
import com.example.domain.model.gateway.OperacionesGateway

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.Try

class OperacionesGatewayImpl extends OperacionesGateway {

  override def resta(x: Double, y: Double): Double = x - y

  override def suma(x: Double, y: Double): Future[Double] = {
    Future {
      x + y
    }
  }

  override def multiplicacion(x: Double, y: Double): Double = x * y

  override def division(x: Double, y: Double): Future[Option[Double]] =
    Future {
      if (y != 0) Some(x / y) else None
    }

  override def division2(x: Double, y: Double): Future[Any] =
    Future {
      if (y != 0) x / y else new ArithmeticException(ERROR_DIVISION)
    }

  override def division3(x: Int, y: Int): Try[Int] = {
    Try(x / y)
  }

  override def division4(x: Double, y: Double): Either[String, Double] = {
    if (y == 0) Left(ERROR_DIVISION)
    else Right(x / y)
  }
}
