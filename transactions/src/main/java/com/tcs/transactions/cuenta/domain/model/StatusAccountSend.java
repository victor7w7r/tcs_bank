package com.tcs.transactions.cuenta.domain.model;

import lombok.*;

import java.time.LocalDate;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StatusAccountSend {
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private Long clienteRef;
    private String nombreCliente;
}
