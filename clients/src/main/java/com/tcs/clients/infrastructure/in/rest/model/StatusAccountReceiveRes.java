package com.tcs.clients.infrastructure.in.rest.model;

import lombok.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StatusAccountReceiveRes {
  private String fecha;
  private String cliente;
  private Long numCuenta;
  private String tipoCuenta;
  private String tipoMovimiento;
  private BigDecimal movimiento;
  private BigDecimal saldoDisponible;
}
