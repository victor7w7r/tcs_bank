package com.tcs.clients.infrastructure.out.persistence;

import com.tcs.clients.domain.model.Cliente;
import com.tcs.clients.infrastructure.out.persistence.mapper.ClientPersistenceMapper;
import com.tcs.clients.infrastructure.out.persistence.repository.ClienteRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import com.tcs.clients.application.port.out.ClientePort;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ClientePersistenceAdapter implements ClientePort {

  private final ClienteRepository clienteRepository;
  private final ClientPersistenceMapper clientPersistenceMapper;

  @Override
  public List<Cliente> findAll() {
    return clientPersistenceMapper.toClienteList(clienteRepository.findAll());
  }

  @Override
  public Optional<Cliente> findByIdentificacion(String identificacion) {
    return clienteRepository.findByIdentificacion(identificacion)
            .map(clientPersistenceMapper::toCliente);
  }

  @Override
  public void save(Cliente cliente) {
    clienteRepository.save(clientPersistenceMapper.toClienteEntity(cliente));
  }

  @Override
  @Transactional
  public void update(Cliente cliente) {
    final var clienteFound = clienteRepository.findByIdentificacion(
            cliente.getIdentificacion()
    );

    if (clienteFound.isPresent()) {
      final var clienteEntity = clienteFound.get();
      clientPersistenceMapper.update(cliente, clienteEntity);
      clienteRepository.save(clienteEntity);
    }

  }

  @Override
  @Transactional
  public void delete(String identificacion) {
    clienteRepository.deleteByIdentificacion(identificacion);
  }

}
