package com.tcs.transactions.movimiento.domain.model;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Movimiento {
    private LocalDate fecha;
    private String tipoMovimiento;
    private BigDecimal valor;
    private BigDecimal saldo;
    private String uuid;
    private String cuentaId;
}