package com.tcs.transactions.cuenta.mapper;

import com.tcs.transactions.cuenta.dto.StatusAccountResDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigDecimal;

@Mapper(componentModel = "spring")
public interface StatusAccountResMapper {

  StatusAccountResDTO toDto(
        String fecha,
        String cliente,
        Long numCuenta,
        String tipoCuenta,
        String tipoMovimiento,
        BigDecimal movimiento,
        BigDecimal saldo,
        BigDecimal saldoDisponible
  );

}
