package com.tcs.transactions.movimiento.application.service;

import com.tcs.transactions.cuenta.application.port.out.CuentaPort;
import com.tcs.transactions.movimiento.application.port.in.MovimientoUseCase;
import com.tcs.transactions.movimiento.application.port.out.MovimientoPort;
import com.tcs.transactions.common.exception.BadRequestException;
import com.tcs.transactions.movimiento.domain.model.Movimiento;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MovimientoService implements MovimientoUseCase {

  private final MovimientoPort movimientoPort;
  private final CuentaPort cuentaPort;

  public List<Movimiento> findAll() {
    return movimientoPort.findAll();
  }

  public void save(Movimiento movimiento, Long numCuenta) {

    final var movimientoFound = movimientoPort
            .findByUuid(movimiento.getUuid());
    if (movimientoFound.isPresent()) {
      throw new BadRequestException("ERROR: Esta transaccion ya existe");
    }

    final var cuentaFound = cuentaPort
            .findByNumCuenta(numCuenta)
            .orElseThrow(() -> new BadRequestException("ERROR: Cuenta no encontrada con ese número de cuenta"));

    final var movimientoValor = movimiento.getValor();
    final var saldoDisponible = cuentaFound.getSaldoInicial();
    final var saldoDiff = saldoDisponible.add(movimientoValor);

    if (saldoDiff.compareTo(BigDecimal.ZERO) < 0) {
      throw new BadRequestException("ERROR: Saldo insuficiente");
    }

    if (movimiento.getUuid() == null) {
      movimiento.setUuid(java.util.UUID.randomUUID().toString());
    }

    if (movimiento.getFecha() == null) {
      movimiento.setFecha(java.time.LocalDate.now());
    }

    if (movimientoValor.longValue() > 0) {
      movimiento.setTipoMovimiento("Deposito");
    } else {
      movimiento.setTipoMovimiento("Retiro");
    }

    cuentaFound.setSaldoInicial(saldoDiff);
    cuentaPort.saveOnly(cuentaFound);

    final var finalMovimiento = Movimiento.builder()
            .fecha(movimiento.getFecha())
            .tipoMovimiento(movimiento.getTipoMovimiento())
            .valor(movimientoValor)
            .saldo(saldoDiff)
            .uuid(movimiento.getUuid())
            .cuentaId(cuentaFound.getId().toString())
            .build();

    movimientoPort.save(finalMovimiento);
  }

  @Override
  public void update(Movimiento movimiento) {
    movimientoPort
            .findByUuid(movimiento.getUuid())
            .orElseThrow(() -> new BadRequestException("ERROR: transaccion no encontrada"));
    movimientoPort.update(movimiento);
  }

  @Override
  public void delete(String uuid) {
    movimientoPort.findByUuid(uuid)
            .orElseThrow(() -> new BadRequestException("ERROR: transaccion no encontrada"));
    movimientoPort.delete(uuid);
  }

}
