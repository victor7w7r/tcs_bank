package com.tcs.transactions.cuenta.repository;

import com.tcs.transactions.cuenta.model.Cuenta;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CuentaRepository extends JpaRepository<Cuenta, Long> {
    Optional<Cuenta> findByNumCuenta(Long numCuenta);
    List<Cuenta> findAllByClienteRef(Long clienteRef);
    void deleteByClienteRef(Long clienteRef);
    void deleteByNumCuenta(Long numCuenta);
}
