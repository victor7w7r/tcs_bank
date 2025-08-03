package com.tcs.clients.cliente.repository;

import com.tcs.clients.cliente.model.Cliente;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
@ActiveProfiles("test")
public class ClienteRepositoryTest {

  @Autowired
  private ClienteRepository clienteRepository;

  private final Cliente cliente = Cliente.builder()
          .clienteId("1")
          .contrasena("victorContrasena")
          .nombre("Victor")
          .genero("Masculino")
          .edad(20)
          .identificacion("1725082786")
          .direccion("Call Segovia y Raices")
          .telefono("0984565509")
          .estado(true)
          .build();

  @AfterEach
  public void tearDown() {
    clienteRepository.deleteAll();
  }

  @Test
  public void testClienteRepository_saveAndRetrievesClienteSuccessfully() {
    clienteRepository.save(cliente);
    final var expected = clienteRepository.findAll().getFirst();
    assertEquals(cliente, expected);
  }

  @Test
  public void testClienteRepository_returnsEmptyListWhenNoClienteExist() {
      final var expected = clienteRepository.findAll();
      assertEquals(0, expected.size());
  }

  @Test
  @Transactional
  public void testClienteRepository_deleteClienteByIdentificacion() {
    clienteRepository.save(cliente);
    clienteRepository.deleteByIdentificacion(cliente.getIdentificacion());
    final var expected = clienteRepository
            .findByIdentificacion(cliente.getIdentificacion())
            .orElse(null);
    assertNull(expected);
  }

}
