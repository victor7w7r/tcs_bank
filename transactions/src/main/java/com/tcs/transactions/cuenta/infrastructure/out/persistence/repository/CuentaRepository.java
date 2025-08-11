package com.tcs.transactions.cuenta.infrastructure.out.persistence.repository;

import com.tcs.transactions.cuenta.infrastructure.out.persistence.entity.CuentaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CuentaRepository extends JpaRepository<CuentaEntity, Long> {
    Optional<CuentaEntity> findByNumCuenta(Long numCuenta);
    List<CuentaEntity> findAllByClienteRef(Long clienteRef);
    void deleteByClienteRef(Long clienteRef);
    void deleteByNumCuenta(Long numCuenta);
}
