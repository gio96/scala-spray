package com.example.domain.model.gateway

import scala.concurrent.Future
import scala.util.Try

trait OperacionesGateway {
  def suma(x: Double, y: Double): Future[Double]

  def resta(x: Double, y: Double): Double

  def multiplicacion(x: Double, y: Double): Double

  def division(x: Double, y: Double): Future[Option[Double]]

  def division2(x: Double, y: Double): Future[Any]

  def division3(x: Int, y: Int): Try[Int]

  def division4(x: Double, y: Double): Either[String, Double]
}
