package com.tcs.transactions.cuenta.infrastructure.out.persistence.mapper;

import com.tcs.transactions.cuenta.domain.model.Cuenta;
import com.tcs.transactions.cuenta.infrastructure.out.persistence.entity.CuentaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CuentaPersistenceMapper {

    CuentaEntity toCuentaEntity(Cuenta cuenta);
    Cuenta toCuenta(CuentaEntity value);
    List<Cuenta> toCuentaList(List<CuentaEntity> cuentaEntities);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "numCuenta", ignore = true)
    @Mapping(target = "clienteRef", ignore = true)
    void update(Cuenta source, @MappingTarget CuentaEntity target);

    @Mapping(target = "numCuenta", ignore = true)
    void updateWithoutNumCuenta(Cuenta source, @MappingTarget CuentaEntity target);
}
