package com.tcs.clients.cliente.repository;

import com.tcs.clients.cliente.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Optional<Cliente> findByIdentificacion(String identificacion);
    void deleteByIdentificacion(String identificacion);
}
