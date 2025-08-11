package com.tcs.clients.application.service;

import com.tcs.clients.application.port.out.ClientePort;
import com.tcs.clients.application.port.out.ClienteStatusAccountPort;
import com.tcs.clients.domain.exception.BadRequestException;
import com.tcs.clients.domain.model.Cliente;
import com.tcs.clients.domain.model.StatusAccountReceive;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClienteServiceTest {
    @Mock
    private ClientePort clientePort;

    @Mock
    private ClienteStatusAccountPort clienteStatusAccountPort;

    @InjectMocks
    private ClienteService clienteService;

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

    @Test
    void findAll_shouldReturnClientes() {
        List<Cliente> clientes = List.of(cliente);
        when(clientePort.findAll()).thenReturn(clientes);
        assertEquals(clientes, clienteService.findAll());
    }

    @Test
    void envioClienteRef_shouldReturnId() {
        when(clientePort.findByIdentificacion("1725082786")).thenReturn(Optional.of(cliente));
        assertEquals(1L, clienteService.envioClienteRef("1725082786"));
    }

    @Test
    void envioClienteRef_shouldThrowIfNotFound() {
        when(clientePort.findByIdentificacion("1725082786")).thenReturn(Optional.empty());
        assertThrows(BadRequestException.class, () -> clienteService.envioClienteRef("1725082786"));
    }

    @Test
    void requestEstadoCuenta_shouldReturnList() {
        LocalDate inicio = LocalDate.now();
        LocalDate fin = LocalDate.now();
        List<StatusAccountReceive> estados = List.of(mock(StatusAccountReceive.class));
        when(clientePort.findByIdentificacion("1725082786")).thenReturn(Optional.of(cliente));
        when(clienteStatusAccountPort.requestEstadoCuenta(inicio, fin, "1725082786", cliente)).thenReturn(estados);
        assertEquals(estados, clienteService.requestEstadoCuenta(inicio, fin, "1725082786"));
    }

    @Test
    void requestEstadoCuenta_shouldThrowIfNotFound() {
        when(clientePort.findByIdentificacion("1725082786")).thenReturn(Optional.empty());
        assertThrows(BadRequestException.class, () -> clienteService.requestEstadoCuenta(LocalDate.now(), LocalDate.now(), "1725082786"));
    }

    @Test
    void save_shouldSaveIfNotExists() {
        when(clientePort.findByIdentificacion("1725082786")).thenReturn(Optional.empty());
        clienteService.save(cliente);
        assertTrue(cliente.getEstado());
        verify(clientePort).save(cliente);
    }

    @Test
    void save_shouldThrowIfExists() {
        when(clientePort.findByIdentificacion("1725082786")).thenReturn(Optional.of(cliente));
        assertThrows(BadRequestException.class, () -> clienteService.save(cliente));
    }

    @Test
    void update_shouldUpdateIfExists() {
        when(clientePort.findByIdentificacion("1725082786")).thenReturn(Optional.of(cliente));
        clienteService.update(cliente);
        verify(clientePort).update(cliente);
    }

    @Test
    void update_shouldThrowIfNotFound() {
        when(clientePort.findByIdentificacion("1725082786")).thenReturn(Optional.empty());
        assertThrows(BadRequestException.class, () -> clienteService.update(cliente));
    }

    @Test
    void delete_shouldDeleteIfExists() {
        when(clientePort.findByIdentificacion("1725082786")).thenReturn(Optional.of(cliente));
        clienteService.delete("1725082786");
        verify(clientePort).delete("1725082786");
    }

    @Test
    void delete_shouldThrowIfNotFound() {
        when(clientePort.findByIdentificacion("1725082786")).thenReturn(Optional.empty());
        assertThrows(BadRequestException.class, () -> clienteService.delete("1725082786"));
    }
}
