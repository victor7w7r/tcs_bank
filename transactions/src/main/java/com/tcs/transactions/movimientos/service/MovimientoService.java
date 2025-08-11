package com.tcs.transactions.movimientos.service;

import com.tcs.transactions.cuenta.repository.CuentaRepository;
import com.tcs.transactions.exception.BadRequestException;
import com.tcs.transactions.movimientos.dto.MovimientoDTO;
import com.tcs.transactions.movimientos.mapper.MovimientoMapper;
import com.tcs.transactions.movimientos.repository.MovimientoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class MovimientoService {

  public MovimientoService(
          MovimientoRepository movimientoRepository,
          MovimientoMapper movimientoMapper,
          CuentaRepository cuentaRepository
  ) {
    this.movimientoRepository = movimientoRepository;
    this.movimientoMapper = movimientoMapper;
    this.cuentaRepository = cuentaRepository;
  }

  private final MovimientoRepository movimientoRepository;
  private final MovimientoMapper movimientoMapper;
  private final CuentaRepository cuentaRepository;

  public List<MovimientoDTO> getAllMovimientos() {
    return movimientoMapper.toDTOList(movimientoRepository.findAll());
  }

  @Transactional
  public void saveMovimiento(MovimientoDTO movimiento, Long numCuenta) {

    final var movimientoFound = movimientoRepository
            .findByUuid(movimiento.getUuid());
    if (movimientoFound.isPresent()) {
      throw new BadRequestException("ERROR: Esta transaccion ya existe");
    }

    final var cuentaFound = cuentaRepository
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
    cuentaRepository.save(cuentaFound);

    final var finalMovimiento = movimientoMapper.toMovimiento(
            movimiento.getFecha(),
            movimiento.getTipoMovimiento(),
            movimientoValor,
            saldoDiff,
            movimiento.getUuid(),
            cuentaFound.getId()
    );

    movimientoRepository.save(finalMovimiento);
  }

  @Transactional
  public void updateMovimiento(MovimientoDTO movimiento) {
    final var movimientoFound = movimientoRepository
            .findByUuid(movimiento.getUuid())
            .orElseThrow(() -> new BadRequestException("ERROR: transaccion no encontrada"));

    movimientoMapper.updateFromDto(movimiento, movimientoFound);
    movimientoRepository.save(movimientoFound);
  }

  @Transactional
  public void deleteMovimiento(String uuid) {
    movimientoRepository.findByUuid(uuid)
            .orElseThrow(() -> new BadRequestException("ERROR: transaccion no encontrada"));
    movimientoRepository.deleteByUuid(uuid);
  }

}
