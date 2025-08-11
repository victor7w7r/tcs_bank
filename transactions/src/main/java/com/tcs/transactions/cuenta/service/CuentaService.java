package com.tcs.transactions.cuenta.service;

import com.tcs.transactions.cuenta.dto.CuentaDTO;
import com.tcs.transactions.cuenta.dto.StatusAccountReqDTO;
import com.tcs.transactions.cuenta.dto.StatusAccountResDTO;
import com.tcs.transactions.cuenta.mapper.CuentaMapper;
import com.tcs.transactions.cuenta.mapper.StatusAccountResMapper;
import com.tcs.transactions.cuenta.repository.CuentaRepository;
import com.tcs.transactions.exception.BadRequestException;
import jakarta.transaction.Transactional;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CuentaService {

  public CuentaService(
          CuentaRepository cuentaRepository,
          CuentaMapper cuentaMapper,
          StatusAccountResMapper statusAccountReqMapper,
          RabbitTemplate rabbitTemplate
  ) {
    this.cuentaRepository = cuentaRepository;
    this.statusAccountReqMapper = statusAccountReqMapper;
    this.cuentaMapper = cuentaMapper;
    this.rabbitTemplate = rabbitTemplate;
  }

  private final CuentaRepository cuentaRepository;
  private final CuentaMapper cuentaMapper;
  private final StatusAccountResMapper statusAccountReqMapper;
  private final RabbitTemplate rabbitTemplate;

  public List<CuentaDTO> getAllCuentas() {
    return cuentaMapper.toDTOList(cuentaRepository.findAll());

  }

  @RabbitListener(queues = "account_status_queue")
  public List<StatusAccountResDTO> requestEstadoCuenta(@Payload StatusAccountReqDTO req) {
    final var requestData = cuentaRepository.findAllByClienteRef(req.getClienteRef());

    final var response = new ArrayList<StatusAccountResDTO>();

    for (final var cuenta : requestData) {
      for (final var movimiento : cuenta.getMovimientos()) {
        if ((
                movimiento.getFecha().isAfter(req.getFechaInicio())
                        || movimiento.getFecha().isEqual(req.getFechaInicio()))
                && (movimiento.getFecha().isBefore(req.getFechaFin())
                || movimiento.getFecha().isEqual(req.getFechaFin())
        )) {

          final var saldoInicial = movimiento.getSaldo();
          final var saldoDiff = saldoInicial.add(movimiento.getValor());

          var statusAccount = statusAccountReqMapper.toDto(
                  movimiento.getFecha().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                  req.getNombreCliente(),
                  cuenta.getNumCuenta(),
                  cuenta.getTipoCuenta(),
                  movimiento.getTipoMovimiento(),
                  movimiento.getValor(),
                  movimiento.getSaldo(),
                  saldoDiff
          );

          response.add(statusAccount);
        }
      }
    }

    return response;
  }

  @Transactional
  public void saveCuenta(CuentaDTO cuenta, String identificacion) {

    final var cuentaFound = cuentaRepository.findByNumCuenta(cuenta.getNumCuenta());

    if (cuentaFound.isPresent()) {
      throw new BadRequestException("ERROR: Esta cuenta ya existe");
    }

    rabbitTemplate.setReplyTimeout(2000);

    final var clienteRef = (Long) rabbitTemplate.convertSendAndReceive(
            "bank-tcs", "cliente.creado", identificacion
    );

    if (clienteRef == null) {
      throw new BadRequestException("ERROR: Cliente con dicha identificacion no encontrado");
    }

    if (cuenta.getEstado() == null) {
      cuenta.setEstado(true);
    }

    cuentaRepository.save(
            cuentaMapper.toCuenta(cuenta.toBuilder().clienteRef(clienteRef).build())
    );
  }

  @Transactional
  public void updateCuenta(CuentaDTO cuenta) {
    final var cuentaFound = cuentaRepository
            .findByNumCuenta(cuenta.getNumCuenta())
            .orElseThrow(() -> new BadRequestException("ERROR: Cuenta no encontrada"));

    cuentaMapper.updateFromDto(cuenta, cuentaFound);
    cuentaRepository.save(cuentaFound);
  }

  @RabbitListener(queues = "borrar_cliente")
  @Transactional
  public Long deleteCuentaPerQueue(Long clienteRef) {
    if (!cuentaRepository.findAllByClienteRef(clienteRef).isEmpty()) {
      cuentaRepository.deleteByClienteRef(clienteRef);
    }
    return 0L;
  }

  @Transactional
  public void deleteCuenta(Long numCuenta) {
    cuentaRepository.findByNumCuenta(numCuenta)
            .orElseThrow(() -> new BadRequestException("ERROR: Cuenta no encontrada"));
    cuentaRepository.deleteByNumCuenta(numCuenta);
  }

}
