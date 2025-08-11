package com.tcs.transactions.cuenta.infrastructure.in.rest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.tcs.transactions.movimiento.domain.model.Movimiento;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CuentaResponse {
  private Long numCuenta;
  private String tipoCuenta;
  private BigDecimal saldoInicial;
  private Boolean estado;
  private List<Movimiento> movimientoEntities;
}
