package com.tcs.clients.application.service;

import com.tcs.clients.application.port.out.ClientePort;
import com.tcs.clients.application.port.in.ClienteUseCase;
import com.tcs.clients.application.port.out.ClienteStatusAccountPort;
import com.tcs.clients.domain.exception.BadRequestException;
import com.tcs.clients.domain.model.Cliente;
import com.tcs.clients.domain.model.StatusAccountReceive;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteService implements ClienteUseCase {

  private final ClientePort clientPort;
  private final ClienteStatusAccountPort clienteStatusAccountPort;


  @Override
  public List<Cliente> findAll() {
    return clientPort.findAll();
  }

  @Override
  public Long envioClienteRef(String identificacion) {
    final var cliente = clientPort
            .findByIdentificacion(identificacion)
            .orElseThrow(() -> new BadRequestException("ERROR: Cliente no encontrado"));
    return cliente.getId();
  }

  @Override
  public List<StatusAccountReceive> requestEstadoCuenta(
          LocalDate fechaInicio,
          LocalDate fechaFin,
          String identificacion
  ) {
    final var clienteFound = clientPort
            .findByIdentificacion(identificacion)
            .orElseThrow(() -> new BadRequestException("ERROR: Cliente no encontrado"));

    return clienteStatusAccountPort.requestEstadoCuenta(fechaInicio, fechaFin, identificacion, clienteFound);
  }

  @Override
  public void save(Cliente cliente) {
    final var clienteFound = clientPort.findByIdentificacion(cliente.getIdentificacion());
    if (clienteFound.isPresent()) {
      throw new BadRequestException("ERROR: Cliente ya existe");
    }
    cliente.setEstado(true);
    clientPort.save(cliente);
  }

  @Override
  public void update(Cliente cliente) {
    clientPort.findByIdentificacion(cliente.getIdentificacion())
            .orElseThrow(() -> new BadRequestException("ERROR: Cliente no encontrado"));
    clientPort.update(cliente);
  }

  @Override
  public void delete(String identificacion) {
    clientPort.findByIdentificacion(identificacion)
            .orElseThrow(() -> new BadRequestException("ERROR: Cliente no encontrado"));
    clientPort.delete(identificacion);
  }
}
