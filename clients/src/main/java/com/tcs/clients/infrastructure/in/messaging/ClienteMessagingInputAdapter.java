package com.tcs.clients.infrastructure.in.messaging;

import com.tcs.clients.application.port.in.ClienteUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClienteMessagingInputAdapter {

    private final ClienteUseCase clienteUseCase;

    @RabbitListener(queues = "cliente-cuenta-queue")
    public Long envioClienteRef(String identificacion) {
        return clienteUseCase.envioClienteRef(identificacion);
    }
}
