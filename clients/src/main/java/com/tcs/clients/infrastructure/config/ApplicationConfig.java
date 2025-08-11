package com.tcs.clients.infrastructure.config;

import com.tcs.clients.application.port.in.ClienteUseCase;
import com.tcs.clients.infrastructure.in.messaging.ClienteMessagingInputAdapter;
import com.tcs.clients.infrastructure.in.rest.ClienteRestAdapter;
import com.tcs.clients.infrastructure.in.rest.mapper.ClienteRestMapper;
import com.tcs.clients.infrastructure.out.messaging.ClienteMessagingOutputAdapter;
import com.tcs.clients.infrastructure.out.messaging.mapper.ClienteMessagingOutputMapper;
import com.tcs.clients.infrastructure.out.persistence.ClientePersistenceAdapter;
import com.tcs.clients.infrastructure.out.persistence.mapper.ClientPersistenceMapper;
import com.tcs.clients.infrastructure.out.persistence.repository.ClienteRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

  @Bean
  public ClientePersistenceAdapter clientePersistenceAdapter(
          ClienteRepository clienteRepository,
          ClientPersistenceMapper clientPersistenceMapper
  ) {
    return new ClientePersistenceAdapter(clienteRepository, clientPersistenceMapper);
  }

  @Bean
  public ClienteMessagingInputAdapter clienteMessagingInputAdapter(
          ClienteUseCase clienteUseCase
  ) {
    return new ClienteMessagingInputAdapter(clienteUseCase);
  }

  @Bean
  public ClienteMessagingOutputAdapter clienteMessagingOutputAdapter(
          RabbitTemplate rabbitTemplate,
          ClienteMessagingOutputMapper clienteMessagingOutputMapper
  ) {
    return new ClienteMessagingOutputAdapter(rabbitTemplate, clienteMessagingOutputMapper);
  }

}
