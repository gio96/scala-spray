package com.example

import com.example.Operaciones.{ERROR_DIVISION, division2}
import org.mockito.Mockito
import org.scalatest.Matchers.{a, convertToAnyShouldWrapper}
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.{AsyncFlatSpec, FunSuite, fixture}
import org.scalatestplus.mockito.MockitoSugar

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success}

class OperacionesTest extends FunSuite with ScalaFutures with MockitoSugar {

  test("Resta") {
    assert(Operaciones.resta(7, 3) === 4)
  }

  test("Division2") {
    whenReady(Operaciones.division2(10, 2)) { result =>
      result shouldBe 5
    }
  }

  /*test("Division2 Error") {
    whenReady(Operaciones.division2(10, 0).failed) { result =>
      result.getMessage shouldBe ERROR_DIVISION
    }
  }*/

  test("Suma") {
    whenReady(Operaciones.suma(10, 2)) { result =>
      result shouldBe 12
    }
  }

  test("Multiplicacion") {
    assert(Operaciones.multiplicacion(3, 2) === 6)
  }

  test("Division Some") {
    whenReady(Operaciones.division(10, 2)) { result =>
      result.get shouldBe 5
    }
  }

  test("Division None") {
    whenReady(Operaciones.division(10, 0)) { result =>
      result shouldBe None
    }
  }

  test("Division 3") {
    assert(Operaciones.division3(10, 2) === Success(5))
  }

  /*test("Division 3 Error") {
    assert(Operaciones.division3(10, 0) === Failure(new ArithmeticException("/ by zero")))
  }*/

  test("Division 4 Right") {
    assert(Operaciones.division4(10, 2).right.get === 5 )
  }

  test("Division 4 left") {
    assert(Operaciones.division4(10, 0).left.get === ERROR_DIVISION )
  }


}
