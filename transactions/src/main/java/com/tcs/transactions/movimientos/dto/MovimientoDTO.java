package com.tcs.transactions.movimientos.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class MovimientoDTO {
    private LocalDate fecha;
    private String tipoMovimiento;

    @NonNull
    private BigDecimal valor;
    private BigDecimal saldo;
    private String uuid;
}