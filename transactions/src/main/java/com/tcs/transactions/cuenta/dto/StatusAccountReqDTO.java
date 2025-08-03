package com.tcs.transactions.cuenta.dto;

import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StatusAccountReqDTO {
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private Long clienteRef;
    private String nombreCliente;
}
