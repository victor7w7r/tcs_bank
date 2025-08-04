package com.tcs.clients.cliente.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class StatusAccountResDTO {

    private String fecha;
    private String cliente;
    private Long numCuenta;
    private String tipoCuenta;
    private String tipoMovimiento;
    private BigDecimal movimiento;
    private BigDecimal saldo;
    private BigDecimal saldoDisponible;
}
