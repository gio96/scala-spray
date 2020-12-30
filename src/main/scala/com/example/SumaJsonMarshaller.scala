package com.example

import org.json4s.{DefaultFormats, Formats}
import spray.httpx.Json4sSupport
;

object SumaJsonMarshaller extends Json4sSupport {
  override implicit def json4sFormats: Formats = DefaultFormats

  case class Suma(numero1: Double, numero2: Double);

}
