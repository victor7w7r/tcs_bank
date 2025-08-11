package com.tcs.transactions.cuenta.application.service;

import com.tcs.transactions.cuenta.application.port.in.CuentaUseCase;
import com.tcs.transactions.cuenta.application.port.out.CuentaMessagingPort;
import com.tcs.transactions.cuenta.application.port.out.CuentaPort;
import com.tcs.transactions.cuenta.domain.model.Cuenta;
import com.tcs.transactions.common.exception.BadRequestException;
import com.tcs.transactions.cuenta.domain.model.StatusAccountReceive;
import com.tcs.transactions.cuenta.domain.model.StatusAccountSend;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CuentaService implements CuentaUseCase {

  private final CuentaPort cuentaPort;
  private final CuentaMessagingPort cuentaMessagingPort;

  @Override
  public List<Cuenta> findAll() {
    return cuentaPort.findAll();
  }

  @Override
  public void update(Cuenta cuenta) {
    cuentaPort.findByNumCuenta(cuenta.getNumCuenta())
            .orElseThrow(() -> new BadRequestException("ERROR: Cuenta no encontrada"));
    cuentaPort.update(cuenta);
  }

  @Override
  public void delete(Long numCuenta) {
    cuentaPort.findByNumCuenta(numCuenta)
            .orElseThrow(() -> new BadRequestException("ERROR: Cuenta no encontrada"));
    cuentaPort.deleteByNumCuenta(numCuenta);
  }

  @Override
  public void save(Cuenta cuenta, String identificacion) {

    final var cuentaFound = cuentaPort.findByNumCuenta(cuenta.getNumCuenta());

    if (cuentaFound.isPresent()) {
      throw new BadRequestException("ERROR: Esta cuenta ya existe");
    }

    final var clienteRef = cuentaMessagingPort.sendIdReceiveRef(identificacion);

    if (clienteRef == null) {
      throw new BadRequestException("ERROR: Cliente con dicha identificacion no encontrado");
    }

    if (cuenta.getEstado() == null) {
      cuenta.setEstado(true);
    }

    cuentaPort.save(cuenta, clienteRef);
  }

  @Override
  public Long deleteByClienteRef(Long clienteRef) {
    if (!cuentaPort.findAllByClienteRef(clienteRef).isEmpty()) {
      cuentaPort.deleteByClienteRef(clienteRef);
    }
    return 0L;
  }

  @Override
  public List<StatusAccountReceive> requestEstadoCuenta(StatusAccountSend req) {
    final var requestData = cuentaPort.findAllByClienteRef(req.getClienteRef());

    final var response = new ArrayList<StatusAccountReceive>();

    for (final var cuenta : requestData) {
      for (final var movimiento : cuenta.getMovimientoEntities()) {
        if ((
                movimiento.getFecha().isAfter(req.getFechaInicio())
                        || movimiento.getFecha().isEqual(req.getFechaInicio()))
                && (movimiento.getFecha().isBefore(req.getFechaFin())
                || movimiento.getFecha().isEqual(req.getFechaFin())
        )) {

          final var saldoInicial = movimiento.getSaldo();
          final var saldoDiff = saldoInicial.add(movimiento.getValor());

          var statusAccount = StatusAccountReceive.builder().
                  fecha(movimiento.getFecha().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy"))).
                  cliente(req.getNombreCliente()).
                  numCuenta(cuenta.getNumCuenta()).
                  tipoCuenta(cuenta.getTipoCuenta()).
                  tipoMovimiento(movimiento.getTipoMovimiento()).
                  movimiento(movimiento.getValor()).
                  saldo(movimiento.getSaldo()).
                  saldoDisponible(saldoDiff).
                  build();

          response.add(statusAccount);
        }
      }
    }
    return response;
  }
}
