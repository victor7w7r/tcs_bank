package com.tcs.clients.application.port.out;

import com.tcs.clients.domain.model.Cliente;

import java.util.List;
import java.util.Optional;

public interface ClientePort {
  List<Cliente> findAll();
  Optional<Cliente> findByIdentificacion(String identificacion);
  void save(Cliente cliente);
  void update(Cliente cliente);
  void delete(String identificacion);
}
