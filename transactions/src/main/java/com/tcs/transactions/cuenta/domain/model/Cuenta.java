package com.tcs.transactions.cuenta.domain.model;

import com.tcs.transactions.movimiento.domain.model.Movimiento;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cuenta {
    private Long id;
    private Long numCuenta;
    private String tipoCuenta;
    private BigDecimal saldoInicial;
    private Long clienteRef;
    private Boolean estado;
    private List<Movimiento> movimientoEntities;
}
