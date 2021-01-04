package com.example

import com.example.Operaciones.division2
import org.scalatest.Matchers.convertToAnyShouldWrapper
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.{AsyncFlatSpec, FunSuite}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.Success

class OperacionesTest extends FunSuite with ScalaFutures {

  test("Resta") {
    assert(Operaciones.resta(7, 3) === 4)
  }

  test("Division futuro") {
    val division2 = Operaciones.division2(10,2)
    division2.map(division => assert(division === 6))
  }

  test("futuro"){
    whenReady(Operaciones.division2(10,2)) { result =>
      result shouldBe 5
    }
  }

}
