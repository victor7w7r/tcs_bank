package com.tcs.transactions.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BrokerConfig {

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(new Jackson2JsonMessageConverter());
        return template;
    }

    @Bean
    public DirectExchange stdExchage() {
        return new DirectExchange("bank.exchange");
    }

    @Bean
    public Queue clienteCuentaQueue() {
        return new Queue("cilente_cuenta_queue");
    }

    @Bean
    public Queue borrarClienteQueue() {
        return new Queue("borrar_cliente");
    }

    @Bean
    public Queue accountStatusQueue() {
        return new Queue("account_status_queue");
    }

    @Bean
    public Binding bindingClienteCuenta(Queue clienteCuentaQueue, DirectExchange stdExchage) {
        return BindingBuilder.bind(clienteCuentaQueue).to(stdExchage).with("cliente.creado");
    }

    @Bean
    public Binding bindingBorrarCliente(Queue borrarClienteQueue, DirectExchange stdExchage) {
        return BindingBuilder.bind(borrarClienteQueue).to(stdExchage).with("cliente.borrado");
    }

    @Bean
    public Binding bindingAccountStatus(Queue accountStatusQueue, DirectExchange stdExchage) {
        return BindingBuilder.bind(accountStatusQueue).to(stdExchage).with("cuenta.estado.solicitado");
    }
}


