package com.tcs.transactions.movimiento.application.port.out;

import com.tcs.transactions.movimiento.domain.model.Movimiento;

import java.util.List;
import java.util.Optional;

public interface MovimientoPort {
  List<Movimiento> findAll();
  Optional<Movimiento> findByUuid(String uuid);
  void save(Movimiento cliente);
  void update(Movimiento cliente);
  void delete(String uuid);
}
