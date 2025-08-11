package com.tcs.transactions.cuenta.dto;

import lombok.*;

import java.time.LocalDate;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatusAccountReqDTO {

    @NonNull
    private LocalDate fechaInicio;

    @NonNull
    private LocalDate fechaFin;

    @NonNull
    private Long clienteRef;

    @NonNull
    private String nombreCliente;
}
