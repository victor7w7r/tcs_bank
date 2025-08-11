package com.tcs.transactions.cuenta.infrastructure.out.persistence;

import com.tcs.transactions.cuenta.application.port.out.CuentaPort;
import com.tcs.transactions.cuenta.domain.model.Cuenta;
import com.tcs.transactions.cuenta.infrastructure.out.persistence.mapper.CuentaPersistenceMapper;
import com.tcs.transactions.cuenta.infrastructure.out.persistence.repository.CuentaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CuentaPersistenceAdapter implements CuentaPort {

  private final CuentaRepository cuentaRepository;
  private final CuentaPersistenceMapper clientPersistenceMapper;

  @Override
  @Transactional
  public List<Cuenta> findAll() {
    return clientPersistenceMapper.toCuentaList(cuentaRepository.findAll());
  }

  @Override
  public List<Cuenta> findAllByClienteRef(Long clienteRef) {
    return clientPersistenceMapper.toCuentaList(cuentaRepository.findAllByClienteRef(clienteRef));
  }

  @Override
  public Optional<Cuenta> findByNumCuenta(Long numCuenta) {
    return cuentaRepository.findByNumCuenta(numCuenta)
            .map(clientPersistenceMapper::toCuenta);
  }

  @Override
  public void save(Cuenta cuenta , Long clienteRef) {
    cuenta.setClienteRef(clienteRef);
    cuentaRepository.save(clientPersistenceMapper.toCuentaEntity(cuenta));
  }

  @Override
  public void saveOnly(Cuenta cuenta) {
    final var cuentaFound = cuentaRepository.findByNumCuenta(
            cuenta.getNumCuenta()
    );

    if (cuentaFound.isPresent()) {
      final var cuentaEntity = cuentaFound.get();
      clientPersistenceMapper.updateWithoutNumCuenta(cuenta, cuentaEntity);
      cuentaRepository.save(cuentaEntity);
    }

  }

  @Override
  @Transactional
  public void update(Cuenta cuenta) {
    final var cuentaFound = cuentaRepository.findByNumCuenta(
            cuenta.getNumCuenta()
    );

    if (cuentaFound.isPresent()) {
      final var cuentaEntity = cuentaFound.get();
      clientPersistenceMapper.update(cuenta, cuentaEntity);
      cuentaRepository.save(cuentaEntity);
    }

  }

  @Override
  @Transactional
  public void deleteByNumCuenta(Long numCuenta) {
    cuentaRepository.deleteByNumCuenta(numCuenta);
  }

  @Override
  @Transactional
  public void deleteByClienteRef(Long clienteRef) {
    cuentaRepository.deleteByClienteRef(clienteRef);
  }

}
