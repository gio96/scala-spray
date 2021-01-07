package com.example.infrastructure.drivenadapters

import com.example.domain.model.gateway.OperacionesGateway

class OperacionesGatewayImpl extends OperacionesGateway {
  override def resta(x: Double, y: Double): Double = x - y
}
