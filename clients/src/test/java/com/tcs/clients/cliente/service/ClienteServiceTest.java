package com.tcs.clients.cliente.service;

import com.tcs.clients.cliente.mapper.ClienteMapper;
import com.tcs.clients.cliente.model.Cliente;
import com.tcs.clients.cliente.repository.ClienteRepository;
import org.apache.coyote.BadRequestException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static java.util.Optional.empty;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClienteServiceTest {

  @Mock
  private ClienteRepository clienteRepository;

  @Mock
  private RabbitTemplate rabbitTemplate;

  @InjectMocks
  private ClienteService clienteService;

  private final Cliente cliente = Cliente.builder()
          .clienteId("1L")
          .contrasena("victorContrasena")
          .nombre("Victor")
          .genero("Masculino")
          .edad(20)
          .identificacion("1725082786")
          .direccion("Call Segovia y Raices")
          .telefono("0984565509")
          .estado(true)
          .build();

  @Test
  public void clienteServiceTest_getAllClientesReturnsListOfClientes() {
    when(clienteRepository.findAll()).thenReturn(List.of(cliente));
    final var result = clienteService.getAllClientes();
    assertEquals(List.of(ClienteMapper.toDTO(cliente)), result);
  }

  @Test
  public void clienteServiceTest_envioClienteRefReturnsClienteId() {
    ReflectionTestUtils.setField(cliente, "id", 1L);

    when(clienteRepository.findByIdentificacion("1725082786")).thenReturn(java.util.Optional.of(cliente));
    final var result = clienteService.envioClienteRef("1725082786");
    assertEquals(1L, result);
    verify(clienteRepository, times(1)).findByIdentificacion("1725082786");
  }

  @Test
  public void clienteServiceTest_envioClienteRefReturnsNullWhenClienteDoesNotExist() {
    when(clienteRepository.findByIdentificacion("1725082786")).thenReturn(empty());
    final var result = clienteService.envioClienteRef("1725082786");
    assertEquals(null, result);
    verify(clienteRepository, times(1)).findByIdentificacion("1725082786");
  }

  @Test
  public void clienteServiceTest_requestEstadoCuentaReturnsList() throws BadRequestException {
    final var fechaInicio = java.time.LocalDate.now();
    final var fechaFin = java.time.LocalDate.now().plusDays(1L);
    final var identificacion = "1725082786";

    when(clienteRepository.findByIdentificacion(identificacion)).thenReturn(java.util.Optional.of(cliente));
    when(rabbitTemplate.convertSendAndReceive(anyString(), Optional.ofNullable(any()))).thenReturn(List.of());

    final var result = clienteService.requestEstadoCuenta(fechaInicio, fechaFin, identificacion);
    assertEquals(List.of(), result);
    verify(rabbitTemplate, times(1)).convertSendAndReceive(anyString(), Optional.ofNullable(any()));
    verify(rabbitTemplate, times(1)).setReplyTimeout(5000);
    verify(clienteRepository, times(1)).findByIdentificacion(identificacion);
  }

  @Test
  public void clienteServiceTest_requestEstadoCuentaThrowsBadRequestExceptionWhenClienteDoesNotExist() {
    final var fechaInicio = java.time.LocalDate.now();
    final var fechaFin = java.time.LocalDate.now().plusDays(1L);
    final var identificacion = "1725082786";

    assertThrows(BadRequestException.class, () -> clienteService.requestEstadoCuenta(fechaInicio, fechaFin, identificacion));

    verify(rabbitTemplate, never()).convertSendAndReceive(anyString(), Optional.ofNullable(any()));
    verify(rabbitTemplate, never()).setReplyTimeout(5000);
    verify(clienteRepository, times(1)).findByIdentificacion(identificacion);
  }

  @Test
  public void clienteServiceTest_saveClienteSavesCliente() throws BadRequestException {
    when(clienteRepository.findByIdentificacion("1725082786")).thenReturn(empty());
    clienteService.saveCliente(ClienteMapper.toDTO(cliente));
    verify(clienteRepository, times(1)).save(cliente);
  }

  @Test
  public void clienteServiceTest_saveClienteThrowsBadRequestExceptionWhenClienteAlreadyExists() {
    when(clienteRepository.findByIdentificacion("1725082786")).thenReturn(java.util.Optional.of(cliente));
    assertThrows(BadRequestException.class, () -> clienteService.saveCliente(ClienteMapper.toDTO(cliente)));
    verify(clienteRepository, never()).save(cliente);
  }

  @Test
  public void clienteServiceTest_updateClienteUpdatesCliente() throws BadRequestException {
    when(clienteRepository.findByIdentificacion("1725082786")).thenReturn(java.util.Optional.of(cliente));
      clienteService.updateCliente(ClienteMapper.toDTO(cliente));
      verify(clienteRepository, times(1)).save(cliente);
  }

  @Test
  public void clienteServiceTest_updateClienteThrowsBadRequestExceptionWhenClienteDoesNotExist() {
    when(clienteRepository.findByIdentificacion("1725082786")).thenReturn(empty());
    assertThrows(BadRequestException.class, () -> clienteService.updateCliente(ClienteMapper.toDTO(cliente)));
    verify(clienteRepository, never()).save(cliente);
  }

  @Test
  public void clienteServiceTest_deleteClienteDeletesCliente() throws BadRequestException {
    when(clienteRepository.findByIdentificacion("1725082786")).thenReturn(java.util.Optional.of(cliente));
    when(rabbitTemplate.convertSendAndReceive("borrar_cliente", cliente.getId())).thenReturn(1L);
    clienteService.deleteCliente("1725082786");
    verify(clienteRepository, times(1)).deleteByIdentificacion("1725082786");
    verify(rabbitTemplate, times(1)).convertSendAndReceive("borrar_cliente", cliente.getId());
  }

  @Test
  public void clienteServiceTest_deleteClienteThrowsBadRequestExceptionWhenClienteDoesNotExist() {
    when(clienteRepository.findByIdentificacion("1725082786")).thenReturn(empty());
    assertThrows(BadRequestException.class, () -> clienteService.deleteCliente("1725082786"));
    verify(clienteRepository, never()).deleteByIdentificacion("1725082786");
  }
}
