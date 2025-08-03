package com.tcs.transactions.cuenta.service;

import com.tcs.transactions.cuenta.dto.CuentaDTO;
import com.tcs.transactions.cuenta.dto.StatusAccountReqDTO;
import com.tcs.transactions.cuenta.dto.StatusAccountResDTO;
import com.tcs.transactions.cuenta.mapper.CuentaMapper;
import com.tcs.transactions.cuenta.repository.CuentaRepository;
import jakarta.transaction.Transactional;
import org.apache.coyote.BadRequestException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CuentaService {

  @Autowired
  private CuentaRepository cuentaRepository;

  @Autowired
  private RabbitTemplate rabbitTemplate;

  public List<CuentaDTO> getAllCuentas() {
    return CuentaMapper.toDTOList(cuentaRepository.findAll());
  }

  @RabbitListener(queues = "account_status_queue")
  public List<StatusAccountResDTO> requestEstadoCuenta(StatusAccountReqDTO req) {
    final var requestData = cuentaRepository.findAllByClienteRef(req.getClienteRef());

    final var response = new ArrayList<StatusAccountResDTO>();

    for (final var cuenta : requestData) {
      for (final var movimiento : cuenta.getMovimientos()) {
        if (movimiento.getFecha().isAfter(req.getFechaInicio()) && movimiento.getFecha().isBefore(req.getFechaFin())) {
          final var statusAccount = StatusAccountResDTO.builder()
                  .fecha(movimiento.getFecha())
                  .cliente(req.getNombreCliente())
                  .numCuenta(cuenta.getNumCuenta())
                  .tipoCuenta(cuenta.getTipoCuenta())
                  .tipoMovimiento(movimiento.getTipoMovimiento())
                  .movimiento(movimiento.getValor())
                  .saldo(movimiento.getSaldo())
                  .saldoDisponible(cuenta.getSaldoInicial())
                  .build();
          response.add(statusAccount);
        }
      }
    }

    return response;
  }

  @Transactional
  public void saveCuenta(CuentaDTO cuenta, String identificacion) throws BadRequestException {

    final var cuentaFound = cuentaRepository.findByNumCuenta(cuenta.getNumCuenta());

    if (cuentaFound.isPresent()) {
      throw new BadRequestException("ERROR: Esta cuenta ya existe");
    }

    rabbitTemplate.setReplyTimeout(2000);

    final var clienteRef = (Long) rabbitTemplate.convertSendAndReceive(
            "cilente_cuenta_queue", identificacion
    );

    if (clienteRef == null) {
      throw new BadRequestException("ERROR: Cliente con dicha identificacion no encontrado");
    }

    if (cuenta.getEstado() == null) {
      cuenta.setEstado(true);
    }

    cuentaRepository.save(
            CuentaMapper.toCuenta(cuenta.toBuilder().clienteRef(clienteRef).build())
    );
  }

  @Transactional
  public void updateCuenta(CuentaDTO cuenta) throws BadRequestException {
    final var cuentaFound = cuentaRepository
            .findByNumCuenta(cuenta.getNumCuenta())
            .orElseThrow(() -> new BadRequestException("ERROR: Cuenta no encontrada"))
            .toBuilder()
            .tipoCuenta(cuenta.getTipoCuenta())
            .saldoInicial(cuenta.getSaldoInicial())
            .estado(cuenta.getEstado())
            .build();

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
  public void deleteCuenta(Long numCuenta) throws BadRequestException {
    cuentaRepository.findByNumCuenta(numCuenta)
            .orElseThrow(() -> new BadRequestException("ERROR: Cuenta no encontrada"));
    cuentaRepository.deleteByNumCuenta(numCuenta);
  }

}
