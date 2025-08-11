package com.tcs.clients.infrastructure.in.rest;

import com.tcs.clients.application.port.in.ClienteUseCase;
import com.tcs.clients.domain.exception.BadRequestException;
import com.tcs.clients.domain.model.Cliente;
import com.tcs.clients.infrastructure.in.rest.mapper.ClienteRestMapper;
import com.tcs.clients.infrastructure.in.rest.model.ClienteResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

@WebMvcTest(ClienteRestAdapter.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters = false)
public class ClienteRestAdapterTest {

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private ClienteUseCase clienteUseCase;

  @MockitoBean
  private ClienteRestMapper clienteRestMapper;

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

  private final ClienteResponse clienteResponse = ClienteResponse.builder()
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
  public void clienteControllerTest_getAllClientesReturnsListOfClientes() throws Exception {
    when(clienteUseCase.findAll()).thenReturn(List.of(cliente));
    when(clienteRestMapper.toClienteResponseList(any())).thenReturn(List.of(clienteResponse));
    mockMvc
            .perform(get("/clientes"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0]").value(clienteResponse));

    verify(clienteUseCase, times(1)).findAll();
  }

  @Test
  public void clienteControllerTest_getEstadoCuentaReturnsList() throws Exception {
    when(clienteUseCase.requestEstadoCuenta(any(), any(), any())).thenReturn(List.of());
    mockMvc
            .perform(get("/clientes/reportes")
                    .param("fechaInicio", "2021-01-01")
                    .param("fechaFin", "2021-01-01")
                    .param("identificacion", "1725082786"))
            .andExpect(status().isOk());
    verify(clienteUseCase, times(1)).requestEstadoCuenta(any(), any(), any());
  }

  @Test
  public void clienteControllerTest_getEstadoCuentaReturnsBadRequest() throws Exception {
    when(clienteUseCase.requestEstadoCuenta(any(), any(), any())).thenThrow(new BadRequestException("ERROR: Cliente no encontrado"));
    mockMvc
            .perform(get("/clientes/reportes")
                    .param("fechaInicio", "2021-01-01")
                    .param("fechaFin", "2021-01-01")
                    .param("identificacion", "1725082786"))
            .andExpect(status().isBadRequest());
    verify(clienteUseCase, times(1)).requestEstadoCuenta(any(), any(), any());
  }

  @Test
  public void clienteControllerTest_saveClienteReturnsOk() throws Exception {
    doNothing().when(clienteUseCase).save(any());
    when(clienteRestMapper.toCliente(any())).thenReturn(cliente);
    mockMvc
            .perform(post("/clientes").content(
                    "{\"nombre\":\"Victor Jimenez\"," +
                            "\"genero\":\"masculino\"," +
                            "\"edad\":30," +
                            "\"identificacion\":\"1725082786\"," +
                            "\"direccion\":\"Amazonas y NNUU\"," +
                            "\"telefono\":\"0984565509\"," +
                            "\"contrasena\":\"tcstest\"}"
            ).contentType("application/json"))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.status").value("Cliente guardado exitosamente"));
    verify(clienteUseCase, times(1)).save(any());
  }

  @Test
  public void clienteControllerTest_saveClienteReturnsBadRequest() throws Exception {
    doThrow(new BadRequestException("ERROR: Cliente ya existe")).when(clienteUseCase).save(any());
    mockMvc
            .perform(post("/clientes").content(
                    "{\"nombre\":\"Victor Jimenez\"," +
                            "\"genero\":\"masculino\"," +
                            "\"edad\":30," +
                            "\"identificacion\":\"1725082786\"," +
                            "\"direccion\":\"Amazonas y NNUU\"," +
                            "\"telefono\":\"0984565509\"," +
                            "\"contrasena\":\"tcstest\"}"
            ).contentType("application/json"))
            .andExpect(status().isBadRequest());
    verify(clienteUseCase, times(1)).save(any());
  }

  @Test
  public void clienteControllerTest_updateClienteReturnsOk() throws Exception {
    doNothing().when(clienteUseCase).update(any());
    mockMvc
            .perform(put("/clientes").content(
                    "{\"nombre\":\"Victor Jimenez\"," +
                            "\"genero\":\"masculino\"," +
                            "\"edad\":30," +
                            "\"identificacion\":\"1725082786\"," +
                            "\"direccion\":\"Amazonas y NNUU\"," +
                            "\"telefono\":\"0984565509\"," +
                            "\"contrasena\":\"tcstest\"}"
            ).contentType("application/json"))
            .andExpect(status().isAccepted())
            .andExpect(jsonPath("$.status").value("Cliente actualizado exitosamente"));
    verify(clienteUseCase, times(1)).update(any());
  }

  @Test
  public void clienteControllerTest_updateClienteReturnsBadRequest() throws Exception {

    doThrow(new BadRequestException("ERROR: Cliente no encontrado")).when(clienteUseCase).update(any());
    mockMvc
            .perform(put("/clientes").content(
                    "{\"nombre\":\"Victor Jimenez\"," +
                            "\"genero\":\"masculino\"," +
                            "\"edad\":30," +
                            "\"identificacion\":\"1725082786\"," +
                            "\"direccion\":\"Amazonas y NNUU\"," +
                            "\"telefono\":\"0984565509\"," +
                            "\"contrasena\":\"tcstest\"}"
            ).contentType("application/json"))
            .andExpect(status().isBadRequest());
    verify(clienteUseCase, times(1)).update(any());
  }

  @Test
  public void clienteControllerTest_deleteClienteReturnsOk() throws Exception {
    doNothing().when(clienteUseCase).delete(any());
    mockMvc
            .perform(delete("/clientes/1725082786"))
            .andExpect(status().isAccepted())
            .andExpect(jsonPath("$.status").value("Cliente eliminado exitosamente"));
    verify(clienteUseCase, times(1)).delete(any());
  }

  @Test
  public void clienteControllerTest_deleteClienteReturnsBadRequest() throws Exception {
    doThrow(new BadRequestException("ERROR: Cliente no encontrado")).when(clienteUseCase).delete(any());
    mockMvc
            .perform(delete("/clientes/1725082786"))
            .andExpect(status().isBadRequest());
    verify(clienteUseCase, times(1)).delete(any());
  }

}
