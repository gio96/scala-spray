package com.example.domain.usecase

import com.example.domain.model.gateway.OperacionesGateway
import com.example.infrastructure.drivenadapters.OperacionesGatewayImpl
import com.google.inject.Provides
import javax.inject.{Inject, Named}

import scala.concurrent.Future
import scala.util.Try

/*@Named*/
class OperacionesUseCase @Inject()(operacionesGateway: OperacionesGateway) {

  //def resta(x: Double, y: Double): Double = new OperacionesGatewayImpl().resta(x, y)
  def resta(x: Double, y: Double): Double = operacionesGateway.resta(x, y)

  def suma(x: Double, y: Double): Future[Double] = operacionesGateway.suma(x, y)

  def multiplicacion(x: Double, y: Double): Double = operacionesGateway.multiplicacion(x, y)

  def division(x: Double, y: Double): Future[Option[Double]] = operacionesGateway.division(x, y)

  def division2(x: Double, y: Double): Future[Any] = operacionesGateway.division2(x, y)

  def division3(x: Int, y: Int): Try[Int] = operacionesGateway.division3(x, y)

  def division4(x: Double, y: Double): Either[String, Double] = operacionesGateway.division4(x, y)

}
