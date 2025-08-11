package com.tcs.transactions.movimiento.infrastructure.out.persistence.mapper;

import com.tcs.transactions.movimiento.domain.model.Movimiento;
import com.tcs.transactions.movimiento.infrastructure.out.persistence.entity.MovimientoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MovimientoPersistenceMapper {

    List<Movimiento> toMovimientoList(List<MovimientoEntity> movimientoEntities);
    Movimiento toMovimiento(MovimientoEntity movimientoEntity);

    MovimientoEntity toMovimientoEntity(Movimiento movimiento);

    @Mapping(target = "uuid", ignore = true)
    void update(Movimiento source, @MappingTarget MovimientoEntity target);
}
