package com.tcs.transactions.cuenta.application.port.in;

import com.tcs.transactions.cuenta.domain.model.Cuenta;
import com.tcs.transactions.cuenta.domain.model.StatusAccountReceive;
import com.tcs.transactions.cuenta.domain.model.StatusAccountSend;

import java.util.List;

public interface CuentaUseCase {
    List<Cuenta> findAll();
    void save(Cuenta cliente, String identificacion);
    void update(Cuenta cliente);
    void delete(Long numCuenta);
    Long deleteByClienteRef(Long clienteRef);
    List<StatusAccountReceive> requestEstadoCuenta(StatusAccountSend req);
}
