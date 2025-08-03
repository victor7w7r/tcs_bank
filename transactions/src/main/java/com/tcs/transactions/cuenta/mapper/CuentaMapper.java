package com.tcs.transactions.cuenta.mapper;

import com.tcs.transactions.cuenta.dto.CuentaDTO;
import com.tcs.transactions.cuenta.model.Cuenta;
import com.tcs.transactions.movimientos.mapper.MovimientoMapper;

import java.util.List;

public class CuentaMapper {

    public static CuentaDTO toDTO(Cuenta cuenta) {
        return CuentaDTO.builder()
                .numCuenta(cuenta.getNumCuenta())
                .tipoCuenta(cuenta.getTipoCuenta())
                .saldoInicial(cuenta.getSaldoInicial())
                .clienteRef(cuenta.getClienteRef())
                .estado(cuenta.getEstado())
                .movimientos(cuenta.getMovimientos().stream().map(MovimientoMapper::toDTO).toList())
                .build();
    }

    public static List<CuentaDTO> toDTOList(List<Cuenta> cuentas) {
        return cuentas.stream().map(CuentaMapper::toDTO).toList();
    }

    public static Cuenta toCuenta(CuentaDTO cuentaDTO) {
        return Cuenta.builder()
                .numCuenta(cuentaDTO.getNumCuenta())
                .tipoCuenta(cuentaDTO.getTipoCuenta())
                .saldoInicial(cuentaDTO.getSaldoInicial())
                .clienteRef(cuentaDTO.getClienteRef())
                .estado(cuentaDTO.getEstado())
                .build();
    }

}
