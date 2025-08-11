package com.tcs.transactions.cuenta.mapper;

import com.tcs.transactions.cuenta.dto.CuentaDTO;
import com.tcs.transactions.cuenta.model.Cuenta;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CuentaMapper {
    List<CuentaDTO> toDTOList(List<Cuenta> cuentas);
    CuentaDTO map(Cuenta value);
    @Mapping(target = "id", ignore = true)
    Cuenta toCuenta(CuentaDTO cuentaDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "numCuenta", ignore = true)
    @Mapping(target = "clienteRef", ignore = true)
    void updateFromDto(CuentaDTO source, @MappingTarget Cuenta target);
}
