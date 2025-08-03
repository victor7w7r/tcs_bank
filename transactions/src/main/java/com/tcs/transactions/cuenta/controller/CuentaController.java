package com.tcs.transactions.cuenta.controller;

import com.tcs.transactions.cuenta.dto.CuentaDTO;
import com.tcs.transactions.cuenta.service.CuentaService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

record StatusResponse(String status) {
}

@CrossOrigin("*")
@RestController
@RequestMapping("cuentas")
public class CuentaController {

    @Autowired
    private CuentaService cuentaService;

    @GetMapping
    private ResponseEntity<List<CuentaDTO>> getAllCuentas() {
        return ResponseEntity.ok(cuentaService.getAllCuentas());
    }

    @PostMapping("{identificacion}")
    private ResponseEntity<?> saveCuenta(
            @RequestBody CuentaDTO cuenta,
            @PathVariable String identificacion
    ) throws BadRequestException {
        cuentaService.saveCuenta(cuenta, identificacion);
        return ResponseEntity.ok(new StatusResponse("Cuenta guardada exitosamente"));
    }

    @PutMapping
    private ResponseEntity<?> updateCuenta(@RequestBody CuentaDTO cuenta) throws BadRequestException {
        cuentaService.updateCuenta(cuenta);
        return ResponseEntity.ok(new StatusResponse("Cuenta actualizada exitosamente"));
    }

    @DeleteMapping("{numCuenta}")
    private ResponseEntity<?> deleteCuenta(@PathVariable Long numCuenta) throws BadRequestException {
        cuentaService.deleteCuenta(numCuenta);
        return ResponseEntity.ok(new StatusResponse("Cuenta eliminada exitosamente"));
    }

}
