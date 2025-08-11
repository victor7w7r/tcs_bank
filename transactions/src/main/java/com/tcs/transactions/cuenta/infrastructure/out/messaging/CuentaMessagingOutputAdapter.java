package com.tcs.transactions.cuenta.infrastructure.out.messaging;

import com.tcs.transactions.cuenta.application.port.out.CuentaMessagingPort;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CuentaMessagingOutputAdapter implements CuentaMessagingPort {

  private final RabbitTemplate rabbitTemplate;

  @Override
  public Long sendIdReceiveRef(String identificacion) {
    rabbitTemplate.setReplyTimeout(2000);
    return (Long) rabbitTemplate.convertSendAndReceive(
            "bank-tcs", "cliente.creado", identificacion
    );
  }
}
