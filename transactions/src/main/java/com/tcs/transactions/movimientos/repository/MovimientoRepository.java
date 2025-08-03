package com.tcs.transactions.movimientos.repository;

import com.tcs.transactions.cuenta.model.Cuenta;
import com.tcs.transactions.movimientos.model.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {

    Optional<Movimiento> findByUuid(String uuid);
    void deleteByUuid(String uuid);

}
