package com.tcs.clients.cliente.controller;

import com.tcs.clients.cliente.dto.ClienteDTO;
import com.tcs.clients.cliente.service.ClienteService;
import org.apache.coyote.BadRequestException;
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

@WebMvcTest(ClienteController.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters = false)
public class ClienteControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private ClienteService clienteService;

  final ClienteDTO clienteDTO = ClienteDTO.builder()
          .clienteId("1L")
          .contrasena("victorContrasena")
          .nombre("Victor")
          .genero("Masculino")
          .edad(20)
          .identificacion("1725082786")
          .direccion("Call Segovia y Raices")
          .telefono("0984565509")
          .estado(true)
          .build();

  @Test
  public void clienteControllerTest_getAllClientesReturnsListOfClientes() throws Exception {
    when(clienteService.getAllClientes()).thenReturn(List.of(clienteDTO));
    mockMvc
            .perform(get("/clientes"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0]").value(clienteDTO));

    verify(clienteService, times(1)).getAllClientes();
  }

  @Test
  public void clienteControllerTest_getEstadoCuentaReturnsList() throws Exception {
    when(clienteService.requestEstadoCuenta(any(), any(), any())).thenReturn(List.of());
    mockMvc
            .perform(get("/clientes/reportes")
                    .param("fechaInicio", "2021-01-01")
                    .param("fechaFin", "2021-01-01")
                    .param("identificacion", "1725082786"))
            .andExpect(status().isOk());
    verify(clienteService, times(1)).requestEstadoCuenta(any(), any(), any());
  }

  @Test
  public void clienteControllerTest_getEstadoCuentaReturnsBadRequest() throws Exception {
    when(clienteService.requestEstadoCuenta(any(), any(), any())).thenThrow(new BadRequestException("ERROR: Cliente no encontrado"));
    mockMvc
            .perform(get("/clientes/reportes")
                    .param("fechaInicio", "2021-01-01")
                    .param("fechaFin", "2021-01-01")
                    .param("identificacion", "1725082786"))
            .andExpect(status().isBadRequest());
    verify(clienteService, times(1)).requestEstadoCuenta(any(), any(), any());
  }

  @Test
  public void clienteControllerTest_saveClienteReturnsOk() throws Exception {
    doNothing().when(clienteService).saveCliente(any());
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
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.status").value("Cliente guardado exitosamente"));
    verify(clienteService, times(1)).saveCliente(any());
  }

  @Test
  public void clienteControllerTest_saveClienteReturnsBadRequest() throws Exception {
    doThrow(new BadRequestException("ERROR: Cliente ya existe")).when(clienteService).saveCliente(any());
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
    verify(clienteService, times(1)).saveCliente(any());
  }

  @Test
  public void clienteControllerTest_updateClienteReturnsOk() throws Exception {
    doNothing().when(clienteService).updateCliente(any());
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
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.status").value("Cliente actualizado exitosamente"));
    verify(clienteService, times(1)).updateCliente(any());
  }

  @Test
  public void clienteControllerTest_updateClienteReturnsBadRequest() throws Exception {

    doThrow(new BadRequestException("ERROR: Cliente no encontrado")).when(clienteService).updateCliente(any());
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
    verify(clienteService, times(1)).updateCliente(any());
  }

  @Test
  public void clienteControllerTest_deleteClienteReturnsOk() throws Exception {
    doNothing().when(clienteService).deleteCliente(any());
    mockMvc
            .perform(delete("/clientes/1725082786"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.status").value("Cliente eliminado exitosamente"));
    verify(clienteService, times(1)).deleteCliente(any());
  }

  @Test
  public void clienteControllerTest_deleteClienteReturnsBadRequest() throws Exception {
    doThrow(new BadRequestException("ERROR: Cliente no encontrado")).when(clienteService).deleteCliente(any());
    mockMvc
            .perform(delete("/clientes/1725082786"))
            .andExpect(status().isBadRequest());
    verify(clienteService, times(1)).deleteCliente(any());
  }

}
