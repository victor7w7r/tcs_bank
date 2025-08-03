package com.tcs.clients.cliente.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClienteDTOTest {

  @Test
  public void clienteDTOTest_withValidProperties() {
    final var clienteId = "1";
    final var contrasena = "victorContrasena";
    final var genero = "M";
    final var nombre = "Victor";
    final var estado = true;
    final var edad = 20;
    final var identificacion = "1725082786";
    final var direccion = "Call Segovia y Raices";
    final var telefono = "0984565509";

    final var clienteDTO = ClienteDTO.builder()
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

    assertEquals(clienteId, clienteDTO.getClienteId());
    assertEquals(contrasena, clienteDTO.getContrasena());
    assertEquals(estado, clienteDTO.getEstado());
    assertEquals(nombre, clienteDTO.getNombre());
    assertEquals(genero, clienteDTO.getGenero());
    assertEquals(edad, clienteDTO.getEdad());
    assertEquals(identificacion, clienteDTO.getIdentificacion());
    assertEquals(direccion, clienteDTO.getDireccion());
    assertEquals(telefono, clienteDTO.getTelefono());
  }
}
