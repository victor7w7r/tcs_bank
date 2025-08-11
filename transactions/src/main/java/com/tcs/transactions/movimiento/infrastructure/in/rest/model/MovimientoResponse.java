package com.tcs.transactions.movimiento.infrastructure.in.rest.model;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovimientoResponse {
  private LocalDate fecha;
  private String tipoMovimiento;
  private BigDecimal valor;
  private BigDecimal saldo;
  private String uuid;
}
