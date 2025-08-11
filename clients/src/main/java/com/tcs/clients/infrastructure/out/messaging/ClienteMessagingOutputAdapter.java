package com.tcs.clients.infrastructure.out.messaging;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tcs.clients.application.port.out.ClienteStatusAccountPort;
import com.tcs.clients.domain.model.Cliente;
import com.tcs.clients.domain.model.StatusAccountReceive;
import com.tcs.clients.infrastructure.out.messaging.mapper.ClienteMessagingOutputMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ClienteMessagingOutputAdapter implements ClienteStatusAccountPort {

  private final RabbitTemplate rabbitTemplate;
  private final ClienteMessagingOutputMapper clienteMessagingOutputMapper;

  @Override
  public List<StatusAccountReceive> requestEstadoCuenta(
          LocalDate fechaInicio,
          LocalDate fechaFin,
          String identificacion,
          Cliente cliente
  ) {
    rabbitTemplate.setReplyTimeout(8000);

    final var response = rabbitTemplate.convertSendAndReceive(
            "bank-tcs", "cuenta.estado.solicitado",
            clienteMessagingOutputMapper.toStatusAccountSendReq(
                    fechaInicio,
                    fechaFin,
                    cliente.getId(),
                    cliente.getNombre()
            )
    );

    return new ObjectMapper().convertValue(response, new TypeReference<>() {
    });
  }

}
