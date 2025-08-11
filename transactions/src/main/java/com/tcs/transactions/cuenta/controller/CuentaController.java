package com.tcs.transactions.cuenta.controller;

import com.tcs.transactions.cuenta.dto.CuentaDTO;
import com.tcs.transactions.cuenta.service.CuentaService;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin("*")
@RestController
@RequestMapping("cuentas")
public class CuentaController {

    public CuentaController(CuentaService cuentaService) {
        this.cuentaService = cuentaService;
    }

    private final CuentaService cuentaService;

    @GetMapping
    private ResponseEntity<List<CuentaDTO>> getAllCuentas() {
        return ResponseEntity.ok(cuentaService.getAllCuentas());
    }

    @PostMapping("{identificacion}")
    private ResponseEntity<Map<String, String>> saveCuenta(
            @RequestBody CuentaDTO cuenta,
            @PathVariable String identificacion
    ) throws BadRequestException {
        cuentaService.saveCuenta(cuenta, identificacion);
        return ResponseEntity.status(201).body(Map.of("status", "Cuenta guardada exitosamente"));
    }

    @PutMapping
    private ResponseEntity<Map<String, String>> updateCuenta(@RequestBody CuentaDTO cuenta) {
        cuentaService.updateCuenta(cuenta);
        return ResponseEntity.status(202).body(Map.of("status", "Cuenta actualizada exitosamente"));
    }

    @DeleteMapping("{numCuenta}")
    private ResponseEntity<Map<String, String>> deleteCuenta(@PathVariable Long numCuenta) {
        cuentaService.deleteCuenta(numCuenta);
        return ResponseEntity.status(202).body(Map.of("status", "Cuenta eliminada exitosamente"));
    }

}
