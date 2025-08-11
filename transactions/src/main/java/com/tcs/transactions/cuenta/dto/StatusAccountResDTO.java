package com.tcs.transactions.cuenta.dto;

import lombok.*;

import java.math.BigDecimal;

@Builder(toBuilder = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatusAccountResDTO {

  @NonNull
  private String fecha;

  @NonNull
  private String cliente;

  @NonNull
  private Long numCuenta;

  @NonNull
  private String tipoCuenta;

  @NonNull
  private String tipoMovimiento;

  @NonNull
  private BigDecimal movimiento;

  @NonNull
  private BigDecimal saldo;

  @NonNull
  private BigDecimal saldoDisponible;
}
