package com.tcs.clients.domain.model;

import lombok.*;
import java.math.BigDecimal;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StatusAccountReceive {
    private String fecha;
    private String cliente;
    private Long numCuenta;
    private String tipoCuenta;
    private String tipoMovimiento;
    private BigDecimal movimiento;
    private BigDecimal saldoDisponible;
}
