package com.example

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object Operaciones {

  def suma(x: Int, y: Int): Future[Int] = {
    Future {
      x + y
    }
  }

}
