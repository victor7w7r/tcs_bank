package com.tcs.transactions.movimiento.infrastructure.in.rest;

import com.tcs.transactions.movimiento.application.port.in.MovimientoUseCase;
import com.tcs.transactions.movimiento.infrastructure.in.rest.mapper.MovimientoRestMapper;
import com.tcs.transactions.movimiento.infrastructure.in.rest.model.MovimientoRequest;
import com.tcs.transactions.movimiento.infrastructure.in.rest.model.MovimientoResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin("*")
@RestController
@RequestMapping("movimientos")
@RequiredArgsConstructor
public class MovimientoRestAdapter {

  private final MovimientoRestMapper movimientoRestMapper;
  private final MovimientoUseCase movimientoUseCase;

  @GetMapping
  private List<MovimientoResponse> findAll() {
    return movimientoRestMapper.toMovimientoResponseList(movimientoUseCase.findAll());
  }

  @PostMapping("{numCuenta}")
  private ResponseEntity<Map<String, String>> saveMovimiento(
          @RequestBody @Valid MovimientoRequest req,
          @PathVariable Long numCuenta
  ) {
    movimientoUseCase.save(movimientoRestMapper.toMovimiento(req), numCuenta);
    return ResponseEntity.status(201).body(Map.of("status", "Transaccion guardada exitosamente"));
  }

  @PutMapping
  private ResponseEntity<Map<String, String>> updateMovimiento(@RequestBody @Valid MovimientoRequest req) {
    movimientoUseCase.update(movimientoRestMapper.toMovimiento(req));
    return ResponseEntity.status(202).body(Map.of("status", "Transaccion actualizada exitosamente"));
  }

  @DeleteMapping("{uuid}")
  private ResponseEntity<Map<String, String>> deleteMovimiento(@PathVariable String uuid) {
    movimientoUseCase.delete(uuid);
    return ResponseEntity.status(202).body(Map.of("status", "Transaccion eliminada exitosamente"));
  }

}
