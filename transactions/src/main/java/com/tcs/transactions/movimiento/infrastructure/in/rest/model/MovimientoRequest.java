package com.tcs.transactions.movimiento.infrastructure.in.rest.model;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovimientoRequest {

  @NotNull
  private LocalDate fecha;
  private String tipoMovimiento;

  @NotNull
  private BigDecimal valor;
  private BigDecimal saldo;
  private String uuid;
}