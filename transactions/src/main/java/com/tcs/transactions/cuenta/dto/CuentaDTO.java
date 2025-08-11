package com.tcs.transactions.cuenta.dto;

import com.tcs.transactions.movimientos.dto.MovimientoDTO;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Builder(toBuilder = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CuentaDTO {

    @NonNull
    private Long numCuenta;

    @NonNull
    private String tipoCuenta;

    @NonNull
    private BigDecimal saldoInicial;

    private Long clienteRef;
    private Boolean estado;

    private List<MovimientoDTO> movimientos;
}
