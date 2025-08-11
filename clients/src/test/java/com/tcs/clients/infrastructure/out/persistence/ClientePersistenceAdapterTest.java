package com.tcs.clients.infrastructure.out.persistence;

import com.tcs.clients.domain.model.Cliente;
import com.tcs.clients.infrastructure.out.persistence.entity.ClienteEntity;
import com.tcs.clients.infrastructure.out.persistence.mapper.ClientPersistenceMapper;
import com.tcs.clients.infrastructure.out.persistence.repository.ClienteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClientePersistenceAdapterTest {
  @Mock
  private ClienteRepository clienteRepository;

  @Mock
  private ClientPersistenceMapper clientPersistenceMapper;

  @InjectMocks
  private ClientePersistenceAdapter clientePersistenceAdapter;

  private final Cliente cliente = Cliente.builder()
          .id(1L)
          .nombre("Victor")
          .genero("Masculino")
          .edad(20)
          .identificacion("1725082786")
          .direccion("Call Segovia y Raices")
          .telefono("0984565509")
          .contrasena("victorContrasena")
          .estado(true)
          .build();

  private final ClienteEntity clienteEntity = ClienteEntity.builder()
          .nombre("Victor")
          .genero("Masculino")
          .edad(20)
          .identificacion("1725082786")
          .direccion("Call Segovia y Raices")
          .telefono("0984565509")
          .contrasena("victorContrasena")
          .estado(true)
          .build();

  @Test
  void findAll_shouldReturnClienteList() {
    var entityList = List.of(clienteEntity);
    var clienteList = List.of(cliente);
    when(clienteRepository.findAll()).thenReturn(entityList);
    when(clientPersistenceMapper.toClienteList(entityList)).thenReturn(clienteList);
    var result = clientePersistenceAdapter.findAll();
    assertEquals(clienteList, result);
  }

  @Test
  void findByIdentificacion_shouldReturnCliente() {
    when(clienteRepository.findByIdentificacion("1725082786")).thenReturn(Optional.of(clienteEntity));
    when(clientPersistenceMapper.toCliente(clienteEntity)).thenReturn(cliente);
    var result = clientePersistenceAdapter.findByIdentificacion("1725082786");
    assertTrue(result.isPresent());
    assertEquals(cliente, result.get());
  }

  @Test
  void findByIdentificacion_shouldReturnEmpty() {
    when(clienteRepository.findByIdentificacion("1725082786")).thenReturn(Optional.empty());
    var result = clientePersistenceAdapter.findByIdentificacion("1725082786");
    assertTrue(result.isEmpty());
  }

  @Test
  void save_shouldCallRepositorySave() {
    var entity = new Object();
    when(clientPersistenceMapper.toClienteEntity(cliente)).thenReturn(clienteEntity);
    clientePersistenceAdapter.save(cliente);
    verify(clienteRepository).save(clienteEntity);
  }

  @Test
  void update_shouldUpdateAndSaveIfFound() {
    when(clienteRepository.findByIdentificacion("1725082786")).thenReturn(Optional.of(clienteEntity));
    doNothing().when(clientPersistenceMapper).update(cliente, clienteEntity);
    clientePersistenceAdapter.update(cliente);
    verify(clientPersistenceMapper).update(cliente, clienteEntity);
    verify(clienteRepository).save(clienteEntity);
  }

  @Test
  void update_shouldDoNothingIfNotFound() {
    when(clienteRepository.findByIdentificacion("1725082786")).thenReturn(Optional.empty());
    clientePersistenceAdapter.update(cliente);
    verify(clientPersistenceMapper, never()).update(any(), any());
    verify(clienteRepository, never()).save(any());
  }

  @Test
  void delete_shouldCallRepositoryDelete() {
    clientePersistenceAdapter.delete("123");
    verify(clienteRepository).deleteByIdentificacion("123");
  }
}
