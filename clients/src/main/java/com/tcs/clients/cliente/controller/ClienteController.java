package com.tcs.clients.cliente.controller;

import com.tcs.clients.cliente.dto.ClienteDTO;
import com.tcs.clients.cliente.service.ClienteService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

record StatusResponse(String status) {}

@CrossOrigin("*")
@RestController
@RequestMapping("clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    private ResponseEntity<List<ClienteDTO>> getAllClientes() {
        return ResponseEntity.ok(clienteService.getAllClientes());
    }

    @PostMapping
    private ResponseEntity<?> saveCliente(@RequestBody ClienteDTO cliente) throws BadRequestException  {
        clienteService.saveCliente(cliente);
        return ResponseEntity.ok(new StatusResponse("Cliente guardado exitosamente"));
    }

    @PutMapping
    private ResponseEntity<?> updateCliente(@RequestBody ClienteDTO cliente) throws BadRequestException {
        clienteService.updateCliente(cliente);
        return ResponseEntity.ok(new StatusResponse("Cliente actualizado exitosamente"));
    }

    @DeleteMapping("{id}")
    private ResponseEntity<?> deleteCliente(@PathVariable String id) throws BadRequestException {
        clienteService.deleteCliente(id);
        return ResponseEntity.ok(new StatusResponse("Cliente eliminado exitosamente"));
    }

}
