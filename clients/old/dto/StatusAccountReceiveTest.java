package com.tcs.clients.old.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDate;

public class StatusAccountReceiveTest {

    @Test
    public void statusAccountResDTOTest_withValidProperties() {
        final var fecha = LocalDate.now().format(
                java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd")
        );
        final var cliente = "Victor";
        final var numCuenta = 1L;
        final var tipoCuenta = "Ahorro";
        final var tipoMovimiento = "Deposito";
        final var movimiento = new BigDecimal(1000);
        final var saldo = new BigDecimal(1000);
        final var saldoDisponible = new BigDecimal(1000);
        final var statusAccountResDTO = StatusAccountResDTO.builder()
                .fecha(fecha)
                .cliente(cliente)
                .numCuenta(numCuenta)
                .tipoCuenta(tipoCuenta)
                .tipoMovimiento(tipoMovimiento)
                .movimiento(movimiento)
                .saldo(saldo)
                .saldoDisponible(saldoDisponible)
                .build();

        assertEquals(fecha, statusAccountResDTO.getFecha());
        assertEquals(cliente, statusAccountResDTO.getCliente());
        assertEquals(numCuenta, statusAccountResDTO.getNumCuenta());
        assertEquals(tipoCuenta, statusAccountResDTO.getTipoCuenta());
        assertEquals(tipoMovimiento, statusAccountResDTO.getTipoMovimiento());
        assertEquals(movimiento, statusAccountResDTO.getMovimiento());
        assertEquals(saldo, statusAccountResDTO.getSaldo());
        assertEquals(saldoDisponible, statusAccountResDTO.getSaldoDisponible());
    }
}
