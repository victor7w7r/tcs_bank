package com.tcs.transactions.movimientos.controller;

import com.tcs.transactions.movimientos.service.MovimientoService;
import com.tcs.transactions.movimientos.dto.MovimientoDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin("*")
@RestController
@RequestMapping("movimientos")
public class MovimientoController {

  public MovimientoController(MovimientoService movimientoService) {
    this.movimientoService = movimientoService;
  }

  private final MovimientoService movimientoService;

  @GetMapping
  private ResponseEntity<List<MovimientoDTO>> getAllMovimientos() {
    return ResponseEntity.ok(movimientoService.getAllMovimientos());
  }

  @PostMapping("{numCuenta}")
  private ResponseEntity<Map<String, String>> saveMovimiento(@RequestBody MovimientoDTO movimiento, @PathVariable Long numCuenta) {
    movimientoService.saveMovimiento(movimiento, numCuenta);
    return ResponseEntity.status(201).body(Map.of("status", "Transaccion guardada exitosamente"));
  }

  @PutMapping
  private ResponseEntity<Map<String, String>> updateMovimiento(@RequestBody MovimientoDTO movimiento) {
    movimientoService.updateMovimiento(movimiento);
    return ResponseEntity.status(202).body(Map.of("status", "Transaccion actualizada exitosamente"));
  }

  @DeleteMapping("{id}")
  private ResponseEntity<Map<String, String>> deleteMovimiento(@PathVariable String uuid) {
    movimientoService.deleteMovimiento(uuid);
    return ResponseEntity.status(202).body(Map.of("status", "Transaccion eliminada exitosamente"));
  }

}
