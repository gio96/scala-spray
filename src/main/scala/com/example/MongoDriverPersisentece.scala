package com.example

import reactivemongo.api.bson.{BSONDocument, nameValueOrdering}
import reactivemongo.api.bson.collection.BSONCollection
import reactivemongo.api.commands.WriteResult
import reactivemongo.api.{AsyncDriver, MongoConnection}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success}

class MongoDriverPersisentece {

  val driver1 = new reactivemongo.api.AsyncDriver

  //val connection3: Future[MongoConnection] = driver1.connect(List("localhost"))
  //val connection3: Future[MongoConnection] = driver1.connect(List("mongodb+srv://admin:correrias123@cluster0.kivos.mongodb.net/test?authSource=admin&replicaSet=atlas-jmf1fv-shard-0&readPreference=primary&appname=MongoDB%20Compass&ssl=true"))
  val connection3: Future[MongoConnection] = driver1.connect(
    List("mongodb+srv://admin:correrias123@cluster0.kivos.mongodb.net/test?authSource=admin&replicaSet=atlas-jmf1fv-shard-0&readPreference=primary&appname=MongoDB%20Compass&ssl=true"))

  def dbFromConnection2(connection: MongoConnection): Future[BSONCollection] =
    connection.database("Correrias").
      map(_.collection("Clientes"))

  val uri = "mongodb+srv://admin:correrias123@cluster0.kivos.mongodb.net/test?authSource=admin&replicaSet=atlas-jmf1fv-shard-0&readPreference=primary&appname=MongoDB%20Compass&ssl=true"

  /*def connection7(driver: AsyncDriver): Future[MongoConnection] = for {
    parsedUri <- MongoConnection.fromString(uri)
    con <- driver.connect(parsedUri)
  } yield con*/

  def connection7(): Future[MongoConnection] = for {
    parsedUri <- MongoConnection.fromString(uri)
    con <- driver1.connect(parsedUri)
  } yield con

  def dbFromConnection(): Future[BSONCollection] = {
    connection3.flatMap(nameValueOrdering => {
      nameValueOrdering.database("Correrias").
        map(_.collection("Clientes"))
    }
    )
  }


  val document1 = BSONDocument(
    "nombreCompleto" -> "Stephane",
    "ciudad" -> "Godbillon",
    "telefono" -> "29",
    "correoCliente" -> "correo")

  def simpleInsert(coll: BSONCollection): Future[Unit] = {
    val writeRes: Future[WriteResult] = coll.insert.one(document1)

    writeRes.onComplete { // Dummy callbacks
      case Failure(e) => e.printStackTrace()
      case Success(writeResult) =>
        println(s"successfully inserted document with result: $writeResult")
    }

    writeRes.map(_ => {}) // in this example, do nothing with the success
  }
}
