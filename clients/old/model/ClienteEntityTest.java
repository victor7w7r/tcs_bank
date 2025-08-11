package com.tcs.clients.old.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClienteEntityTest {

    @Test
    public void clienteTest_withValidProperties() {
        final var clienteId = "1";
        final var contrasena = "victorContrasena";
        final var genero = "Masculino";
        final var nombre = "Victor";
        final var estado = true;
        final var edad = 20;
        final var identificacion = "1725082786";
        final var direccion = "Call Segovia y Raices";
        final var telefono = "0984565509";

        final var cliente = Cliente.builder()
                .clienteId(clienteId)
                .contrasena(contrasena)
                .nombre(nombre)
                .genero(genero)
                .edad(edad)
                .identificacion(identificacion)
                .direccion(direccion)
                .telefono(telefono)
                .estado(estado)
                .build();

        assertEquals(clienteId, cliente.getClienteId());
        assertEquals(contrasena, cliente.getContrasena());
        assertEquals(estado, cliente.getEstado());
        assertEquals(nombre, cliente.getNombre());
        assertEquals(genero, cliente.getGenero());
        assertEquals(edad, cliente.getEdad());
        assertEquals(identificacion, cliente.getIdentificacion());
        assertEquals(direccion, cliente.getDireccion());
        assertEquals(telefono, cliente.getTelefono());
    }
}