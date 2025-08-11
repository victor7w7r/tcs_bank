package com.tcs.transactions.movimiento.infrastructure.out.persistence;

import com.tcs.transactions.movimiento.application.port.out.MovimientoPort;
import com.tcs.transactions.movimiento.domain.model.Movimiento;
import com.tcs.transactions.movimiento.infrastructure.out.persistence.mapper.MovimientoPersistenceMapper;
import com.tcs.transactions.movimiento.infrastructure.out.persistence.repository.MovimientoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MovimientoPersistenceAdapter implements MovimientoPort {

  private final MovimientoRepository movimientoRepository;
  private final MovimientoPersistenceMapper movimientoPersistenceMapper;

  @Override
  public List<Movimiento> findAll() {
    return movimientoPersistenceMapper.toMovimientoList(movimientoRepository.findAll());
  }

  @Override
  public Optional<Movimiento> findByUuid(String uuid) {
    return movimientoRepository.findByUuid(uuid)
            .map(movimientoPersistenceMapper::toMovimiento);
  }

  @Override
  @Transactional
  public void save(Movimiento movimiento) {
    movimientoRepository.save(movimientoPersistenceMapper.toMovimientoEntity(movimiento));
  }

  @Override
  @Transactional
  public void update(Movimiento movimiento) {
    final var movimientoFound = movimientoRepository.findByUuid(
            movimiento.getUuid()
    );

    if (movimientoFound.isPresent()) {
      final var movimientoEntity = movimientoFound.get();
      movimientoPersistenceMapper.update(movimiento, movimientoEntity);
      movimientoRepository.save(movimientoEntity);
    }

  }

  @Override
  @Transactional
  public void delete(String uuid) {
    movimientoRepository.deleteByUuid(uuid);
  }



}
