package com.tcs.transactions.cuenta.infrastructure.in.messaging.entity;

import lombok.*;

import java.time.LocalDate;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StatusAccountSendReq {
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private Long clienteRef;
    private String nombreCliente;
}
