package com.tcs.clients.config;

import com.rabbitmq.client.AMQP;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BrokerConfig {

    @Bean
    public Queue clienteCuentaQueue() {
        return new Queue("cilente_cuenta_queue");
    }

    @Bean
    public Queue borrarClienteQueue() {
        return new Queue("borrar_cliente");
    }
}
