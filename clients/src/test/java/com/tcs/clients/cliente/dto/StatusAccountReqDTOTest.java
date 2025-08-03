package com.tcs.clients.cliente.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

public class StatusAccountReqDTOTest {

  @Test
  public void statusAccountReqDTOTest_withValidProperties() {
    final var fechaInicio = LocalDate.now();
    final var fechaFin = LocalDate.now();
    final var clienteRef = 1L;
    final var nombreCliente = "Victor";

    final var statusAccountReqDTO = StatusAccountReqDTO.builder()
            .fechaInicio(fechaInicio)
            .fechaFin(fechaFin)
            .clienteRef(clienteRef)
            .nombreCliente(nombreCliente)
            .build();

    assertEquals(fechaInicio, statusAccountReqDTO.getFechaInicio());
    assertEquals(fechaFin, statusAccountReqDTO.getFechaFin());
    assertEquals(clienteRef, statusAccountReqDTO.getClienteRef());
    assertEquals(nombreCliente, statusAccountReqDTO.getNombreCliente());
  }

}
