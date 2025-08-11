package com.tcs.transactions.movimiento.application.port.in;

import com.tcs.transactions.movimiento.domain.model.Movimiento;

import java.util.List;

public interface MovimientoUseCase {
    List<Movimiento> findAll();
    void save(Movimiento cliente, Long numCuenta);
    void update(Movimiento cliente);
    void delete(String uuid);
}
