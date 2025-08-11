package com.tcs.clients.application.port.out;

import com.tcs.clients.domain.model.Cliente;
import com.tcs.clients.domain.model.StatusAccountReceive;

import java.time.LocalDate;
import java.util.List;

public interface ClienteStatusAccountPort {
  List<StatusAccountReceive> requestEstadoCuenta(
          LocalDate fechaInicio,
          LocalDate fechaFin,
          String identificacion,
          Cliente cliente
  );
}
