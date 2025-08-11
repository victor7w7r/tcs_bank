package com.tcs.clients.application.port.in;

import com.tcs.clients.domain.model.Cliente;
import com.tcs.clients.domain.model.StatusAccountReceive;

import java.time.LocalDate;
import java.util.List;

public interface ClienteUseCase {
    List<StatusAccountReceive> requestEstadoCuenta(
            LocalDate fechaInicio,
            LocalDate fechaFin,
            String identificacion
    );
    Long envioClienteRef(String identificacion);
    List<Cliente> findAll();
    void save(Cliente cliente);
    void update(Cliente cliente);
    void delete(String identificacion);
}
