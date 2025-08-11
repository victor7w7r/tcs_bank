package com.tcs.transactions.cuenta.infrastructure.in.messaging;

import com.tcs.transactions.cuenta.application.port.in.CuentaUseCase;
import com.tcs.transactions.cuenta.infrastructure.in.messaging.entity.StatusAccountReceiveRes;
import com.tcs.transactions.cuenta.infrastructure.in.messaging.entity.StatusAccountSendReq;
import com.tcs.transactions.cuenta.infrastructure.in.messaging.mapper.CuentaMessagingInputMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CuentaMessagingInputAdapter {

    private final CuentaUseCase clienteUseCase;
    private final CuentaMessagingInputMapper cuentaMessagingInputMapper;

    @RabbitListener(queues = "borrar_cliente")
    public Long deleteCuentaPerQueue(Long clienteRef) {
        return clienteUseCase.deleteByClienteRef(clienteRef);
    }

    @RabbitListener(queues = "account_status_queue")
    public List<StatusAccountReceiveRes> requestEstadoCuenta(@Payload StatusAccountSendReq req) {
        return cuentaMessagingInputMapper.toStatusAccountReceiveResList(
                clienteUseCase.requestEstadoCuenta(cuentaMessagingInputMapper.toStatusAccountSend(req))
        );
    }

}
