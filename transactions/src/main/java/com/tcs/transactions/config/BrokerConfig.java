package com.tcs.transactions.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BrokerConfig {

  public static final String EXCHANGE_NAME = "bank-tcs";

  public static final String CLIENTE_CUENTA_QUEUE = "cliente-cuenta-queue";
  public static final String CLIENTE_CUENTA_ROUTING_KEY = "cliente.creado";

  public static final String BORRAR_CLIENTE_QUEUE = "borrar_cliente";
  public static final String BORRAR_CLIENTE_ROUTING_KEY = "cliente.borrado";

  public static final String ACCOUNT_STATUS_QUEUE = "account_status_queue";
  public static final String ACCOUNT_STATUS_ROUTING_KEY = "cuenta.estado.solicitado";

  @Bean
  public DirectExchange exchange() {
    return new DirectExchange(EXCHANGE_NAME);
  }

  @Bean
  public Queue clienteCuentaQueue() {
    return new Queue(CLIENTE_CUENTA_QUEUE, true);
  }

  @Bean
  public Queue borrarClienteQueue() {
    return new Queue(BORRAR_CLIENTE_QUEUE, true);
  }

  @Bean
  public Queue accountStatusQueue() {
    return new Queue(ACCOUNT_STATUS_QUEUE, true);
  }

  @Bean
  public Binding bindingClienteCuenta() {
    return BindingBuilder.bind(clienteCuentaQueue()).to(exchange()).with(CLIENTE_CUENTA_ROUTING_KEY);
  }

  @Bean
  public Binding bindingBorrarCliente() {
    return BindingBuilder.bind(borrarClienteQueue()).to(exchange()).with(BORRAR_CLIENTE_ROUTING_KEY);
  }

  @Bean
  public Binding bindingAccountStatus() {
    return BindingBuilder.bind(accountStatusQueue()).to(exchange()).with(ACCOUNT_STATUS_ROUTING_KEY);
  }

  @Bean
  public MessageConverter jsonConverter() {
    return new Jackson2JsonMessageConverter();
  }

  @Bean
  public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
    RabbitTemplate template = new RabbitTemplate(connectionFactory);
    template.setMessageConverter(jsonConverter());
    return template;
  }

}
