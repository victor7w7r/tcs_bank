package com.tcs.transactions.cuenta.infrastructure.config;

import com.tcs.transactions.cuenta.application.port.in.CuentaUseCase;
import com.tcs.transactions.cuenta.infrastructure.in.messaging.CuentaMessagingInputAdapter;
import com.tcs.transactions.cuenta.infrastructure.in.messaging.mapper.CuentaMessagingInputMapper;
import com.tcs.transactions.cuenta.infrastructure.out.persistence.CuentaPersistenceAdapter;
import com.tcs.transactions.cuenta.infrastructure.out.persistence.mapper.CuentaPersistenceMapper;
import com.tcs.transactions.cuenta.infrastructure.out.persistence.repository.CuentaRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CuentaBeansConfig {

  @Bean
  public CuentaPersistenceAdapter cuentaPersistenceAdapter(
          CuentaRepository cuentaRepository,
          CuentaPersistenceMapper cuentaPersistenceMapper
  ) {
    return new CuentaPersistenceAdapter(cuentaRepository, cuentaPersistenceMapper);
  }

  @Bean
  public CuentaMessagingInputAdapter cuentaMessagingInputAdapter(
          CuentaUseCase cuentaUseCase,
          CuentaMessagingInputMapper cuentaMessagingInputMapper
  ) {
    return new CuentaMessagingInputAdapter(cuentaUseCase, cuentaMessagingInputMapper);
  }

}