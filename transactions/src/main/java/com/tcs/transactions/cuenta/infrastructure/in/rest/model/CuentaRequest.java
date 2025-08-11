package com.tcs.transactions.cuenta.infrastructure.in.rest.model;

import com.tcs.transactions.movimiento.domain.model.Movimiento;
import lombok.*;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CuentaRequest {

    @NotNull
    private Long numCuenta;

    @NotNull
    private String tipoCuenta;

    @NotNull
    private BigDecimal saldoInicial;

    private Long clienteRef;
    private Boolean estado;

    private List<Movimiento> movimientos;
}
