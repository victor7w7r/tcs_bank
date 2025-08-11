package com.tcs.transactions.cuenta.application.port.out;

import com.tcs.transactions.cuenta.domain.model.Cuenta;

import java.util.List;
import java.util.Optional;

public interface CuentaPort {
  List<Cuenta> findAll();
  List<Cuenta> findAllByClienteRef(Long clienteRef);
  Optional<Cuenta> findByNumCuenta(Long numCuenta);
  void save(Cuenta cliente, Long clienteRef);
  void saveOnly(Cuenta cuenta);
  void update(Cuenta cliente);
  void deleteByNumCuenta(Long numCuenta);
  void deleteByClienteRef(Long clienteRef);
}
