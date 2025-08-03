package com.tcs.transactions.movimientos.controller;

import com.tcs.transactions.movimientos.service.MovimientoService;
import com.tcs.transactions.movimientos.dto.MovimientoDTO;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

record StatusResponse(String status) {}

@CrossOrigin("*")
@RestController
@RequestMapping("movimientos")
public class MovimientoController {

    @Autowired
    private MovimientoService movimientoService;

    @GetMapping
    private ResponseEntity<List<MovimientoDTO>> getAllMovimientos() {
        return ResponseEntity.ok(movimientoService.getAllMovimientos());
    }

    @PostMapping("{numCuenta}")
    private ResponseEntity<?> saveMovimiento(@RequestBody MovimientoDTO movimiento, @PathVariable Long numCuenta) throws BadRequestException {
        movimientoService.saveMovimiento(movimiento, numCuenta);
        return ResponseEntity.ok(new StatusResponse("Transaccion guardada exitosamente"));
    }

    @PutMapping
    private ResponseEntity<?> updateMovimiento(@RequestBody MovimientoDTO movimiento) throws BadRequestException {
        movimientoService.updateMovimiento(movimiento);
        return ResponseEntity.ok(new StatusResponse("Transaccion actualizada exitosamente"));
    }

    @DeleteMapping("{id}")
    private ResponseEntity<?> deleteMovimiento(@PathVariable String uuid) throws BadRequestException {
        movimientoService.deleteMovimiento(uuid);
        return ResponseEntity.ok(new StatusResponse("Transaccion eliminada exitosamente"));
    }

}
