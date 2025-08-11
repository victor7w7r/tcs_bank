package com.tcs.clients.infrastructure.out.messaging;

import com.tcs.clients.domain.model.Cliente;
import com.tcs.clients.domain.model.StatusAccountReceive;
import com.tcs.clients.infrastructure.out.messaging.entity.StatusAccountSendReq;
import com.tcs.clients.infrastructure.out.messaging.mapper.ClienteMessagingOutputMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteMessagingOutputAdapterTest {

  @Mock
  private RabbitTemplate rabbitTemplate;

  @Mock
  private ClienteMessagingOutputMapper clienteMessagingOutputMapper;

  @InjectMocks
  private ClienteMessagingOutputAdapter clienteMessagingOutputAdapter;

  private LocalDate fechaInicio;
  private LocalDate fechaFin;

  private final Cliente cliente = Cliente.builder()
          .id(1L)
          .nombre("Victor")
          .genero("Masculino")
          .edad(20)
          .identificacion("1725082786")
          .direccion("Call Segovia y Raices")
          .telefono("0984565509")
          .contrasena("victorContrasena")
          .estado(true)
          .build();

  private final StatusAccountSendReq requestMock = StatusAccountSendReq.builder()
          .fechaInicio(fechaInicio)
          .fechaFin(fechaFin)
          .clienteRef(cliente.getId())
          .nombreCliente(cliente.getNombre())
          .build();

  @BeforeEach
  void setUp() {
    fechaInicio = LocalDate.of(2023, 1, 1);
    fechaFin = LocalDate.of(2023, 12, 31);
  }

  @Test
  void requestEstadoCuenta_shouldSendMessageAndReturnList() {
    List<StatusAccountReceive> expectedList = List.of(mock(StatusAccountReceive.class));
    when(clienteMessagingOutputMapper.toStatusAccountSendReq(fechaInicio, fechaFin, cliente.getId(), cliente.getNombre())).thenReturn(requestMock);
    when(rabbitTemplate.convertSendAndReceive(anyString(), anyString(), eq(requestMock))).thenReturn(expectedList);

    List<StatusAccountReceive> result = clienteMessagingOutputAdapter.requestEstadoCuenta(fechaInicio, fechaFin, "123", cliente);

    assertNotNull(result);
    assertEquals(1, result.size());
    verify(rabbitTemplate).setReplyTimeout(8000);
    verify(rabbitTemplate).convertSendAndReceive(eq("bank-tcs"), eq("cuenta.estado.solicitado"), eq(requestMock));
    verify(clienteMessagingOutputMapper).toStatusAccountSendReq(fechaInicio, fechaFin, cliente.getId(), cliente.getNombre());
  }

  @Test
  void requestEstadoCuenta_shouldHandleNullResponse() {
    when(clienteMessagingOutputMapper.toStatusAccountSendReq(any(), any(), any(), any())).thenReturn(requestMock);
    when(rabbitTemplate.convertSendAndReceive(anyString(), anyString(), eq(requestMock))).thenReturn(null);

    List<StatusAccountReceive> result = clienteMessagingOutputAdapter.requestEstadoCuenta(fechaInicio, fechaFin, "123", cliente);
    assertNull(result);
  }
}
