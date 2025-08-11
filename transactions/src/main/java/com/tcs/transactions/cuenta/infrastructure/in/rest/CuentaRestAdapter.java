package com.tcs.transactions.cuenta.infrastructure.in.rest;

import com.tcs.transactions.cuenta.application.port.in.CuentaUseCase;
import com.tcs.transactions.cuenta.infrastructure.in.rest.mapper.CuentaRestMapper;
import com.tcs.transactions.cuenta.infrastructure.in.rest.model.CuentaRequest;
import com.tcs.transactions.cuenta.infrastructure.in.rest.model.CuentaResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin("*")
@RestController
@RequestMapping("cuentas")
@RequiredArgsConstructor
public class CuentaRestAdapter {

    private final CuentaUseCase cuentaUseCase;
    private final CuentaRestMapper cuentaRestMapper;

    @GetMapping
    private List<CuentaResponse> findAll() {
        return cuentaRestMapper.toCuentaResponseList(cuentaUseCase.findAll());
    }

    @PostMapping("{identificacion}")
    private ResponseEntity<Map<String, String>> saveCuenta(
            @Valid @RequestBody CuentaRequest request,
            @PathVariable String identificacion
    ) {
        cuentaUseCase.save(cuentaRestMapper.toCuenta(request), identificacion);
        return ResponseEntity.status(201).body(Map.of("status", "Cuenta guardada exitosamente"));
    }

    @PutMapping
    private ResponseEntity<Map<String, String>> updateCuenta(@Valid @RequestBody CuentaRequest request) {
        cuentaUseCase.update(cuentaRestMapper.toCuenta(request));
        return ResponseEntity.status(202).body(Map.of("status", "Cuenta actualizada exitosamente"));
    }

    @DeleteMapping("{numCuenta}")
    private ResponseEntity<Map<String, String>> deleteCuenta(@PathVariable Long numCuenta) {
        cuentaUseCase.delete(numCuenta);
        return ResponseEntity.status(202).body(Map.of("status", "Cuenta eliminada exitosamente"));
    }

}
