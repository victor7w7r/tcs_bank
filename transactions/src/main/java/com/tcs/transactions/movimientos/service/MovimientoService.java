package com.tcs.transactions.movimientos.service;

import com.tcs.transactions.cuenta.repository.CuentaRepository;
import com.tcs.transactions.movimientos.dto.MovimientoDTO;
import com.tcs.transactions.movimientos.mapper.MovimientoMapper;
import com.tcs.transactions.movimientos.model.Movimiento;
import com.tcs.transactions.movimientos.repository.MovimientoRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.apache.coyote.BadRequestException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class MovimientoService {

    @Autowired
    private MovimientoRepository movimientoRepository;

    @Autowired
    private CuentaRepository cuentaRepository;

    public List<MovimientoDTO> getAllMovimientos() {
        return MovimientoMapper.toDTOList(movimientoRepository.findAll());
    }

    @Transactional
    public void saveMovimiento(MovimientoDTO movimiento, Long numCuenta) throws BadRequestException {

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

        final var finalMovimiento = Movimiento.builder()
                .fecha(movimiento.getFecha())
                .tipoMovimiento(movimiento.getTipoMovimiento())
                .valor(movimiento.getValor())
                .saldo(saldoDiff)
                .uuid(movimiento.getUuid())
                .cuenta(cuentaFound)
                .build();

        movimientoRepository.save(finalMovimiento);
    }

    @Transactional
    public void updateMovimiento(MovimientoDTO movimiento) throws BadRequestException {
        final var movimientoFound = movimientoRepository
                .findByUuid(movimiento.getUuid())
                .orElseThrow(() -> new BadRequestException("ERROR: transaccion no encontrada"))
                .toBuilder()
                .fecha(movimiento.getFecha())
                .tipoMovimiento(movimiento.getTipoMovimiento())
                .valor(movimiento.getValor())
                .saldo(movimiento.getSaldo())
                .build();

        movimientoRepository.save(movimientoFound);
    }

    @Transactional
    public void deleteMovimiento(String uuid) throws BadRequestException {
        movimientoRepository.findByUuid(uuid)
                .orElseThrow(() -> new BadRequestException("ERROR: transaccion no encontrada"));
        movimientoRepository.deleteByUuid(uuid);
    }

}
