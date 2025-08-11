package com.tcs.transactions.movimiento.infrastructure.config;

import com.tcs.transactions.movimiento.infrastructure.out.persistence.MovimientoPersistenceAdapter;
import com.tcs.transactions.movimiento.infrastructure.out.persistence.mapper.MovimientoPersistenceMapper;
import com.tcs.transactions.movimiento.infrastructure.out.persistence.repository.MovimientoRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MovimientoBeansConfig {

  @Bean
  public MovimientoPersistenceAdapter movimientoPersistenceAdapter(
          MovimientoRepository movimientoRepository,
          MovimientoPersistenceMapper movimientoPersistenceMapper
  ) {
    return new MovimientoPersistenceAdapter(movimientoRepository, movimientoPersistenceMapper);
  }

}