package com.example

import org.json4s.{DefaultFormats, Formats}
import spray.httpx.Json4sSupport
;

object ResponseJsonMarshaller extends Json4sSupport {
  override implicit def json4sFormats: Formats = DefaultFormats

  case class Response(mensaje: String, status: Int);

}
