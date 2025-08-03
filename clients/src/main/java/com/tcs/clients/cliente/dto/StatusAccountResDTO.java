package com.tcs.clients.cliente.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class StatusAccountResDTO {
    private LocalDate fecha;
    private String cliente;
    private Long numCuenta;
    private String tipoCuenta;
    private String tipoMovimiento;
    private BigDecimal movimiento;
    private BigDecimal saldo;
    private BigDecimal saldoDisponible;
}
