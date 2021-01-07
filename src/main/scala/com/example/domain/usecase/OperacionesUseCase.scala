package com.example.domain.usecase

import com.example.domain.model.gateway.OperacionesGateway
import com.example.infrastructure.drivenadapters.OperacionesGatewayImpl

import javax.inject.{Inject, Named}

/*@Named*/
class OperacionesUseCase @Inject()(prueba: OperacionesGateway) {
//class OperacionesUseCase () {

  //def resta(x: Double, y: Double): Double = new OperacionesGatewayImpl().resta(x, y)
  def resta(x: Double, y: Double): Double = prueba.resta(x, y)

  //OperacionesGateway().resta(x,y)

}
