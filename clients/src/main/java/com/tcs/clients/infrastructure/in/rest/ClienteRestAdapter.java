package com.tcs.clients.infrastructure.in.rest;

import com.tcs.clients.application.port.in.ClienteUseCase;
import com.tcs.clients.infrastructure.in.rest.mapper.ClienteRestMapper;
import com.tcs.clients.infrastructure.in.rest.model.ClienteRequest;
import com.tcs.clients.infrastructure.in.rest.model.ClienteResponse;
import com.tcs.clients.infrastructure.in.rest.model.StatusAccountReceiveRes;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("*")
@RequestMapping("clientes")
@RequiredArgsConstructor
public class ClienteRestAdapter {

  private final ClienteUseCase clienteUseCase;
  private final ClienteRestMapper clienteRestMapper;

  @GetMapping
  public List<ClienteResponse> getAllClientes() {
    return clienteRestMapper.toClienteResponseList(clienteUseCase.findAll());
  }

  @GetMapping("reportes")
  public List<StatusAccountReceiveRes> getEstadoCuenta(
          @RequestParam LocalDate fechaInicio,
          @RequestParam LocalDate fechaFin,
          @RequestParam String identificacion
  ) {
    return clienteRestMapper.toStatusAccountReceiveResList(
            clienteUseCase.requestEstadoCuenta(
                    fechaInicio,
                    fechaFin,
                    identificacion
            )
    );
  }

  @PostMapping
  public ResponseEntity<Map<String, String>> saveCliente(
          @Valid @RequestBody ClienteRequest request
  ) {
    clienteUseCase.save(clienteRestMapper.toCliente(request));
    return ResponseEntity.status(201).body(Map.of("status", "Cliente guardado exitosamente"));
  }

  @PutMapping
  public ResponseEntity<Map<String, String>> updateCliente(
          @Valid @RequestBody ClienteRequest request
  ) {
    clienteUseCase.update(clienteRestMapper.toCliente(request));
    return ResponseEntity.status(202).body(Map.of("status", "Cliente actualizado exitosamente"));
  }

  @DeleteMapping("{id}")
  public ResponseEntity<Map<String, String>> deleteCliente(@PathVariable String id) {
    clienteUseCase.delete(id);
    return ResponseEntity.status(202).body(Map.of("status", "Cliente eliminado exitosamente"));
  }
}
