package com.tcs.transactions.movimientos.mapper;

import com.tcs.transactions.cuenta.model.Cuenta;
import com.tcs.transactions.movimientos.dto.MovimientoDTO;
import com.tcs.transactions.movimientos.model.Movimiento;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Mapper(componentModel = "spring")
public interface MovimientoMapper {
    List<MovimientoDTO> toDTOList(List<Movimiento> movimientos);
    MovimientoDTO map(Movimiento movimiento);

    Movimiento toMovimiento(
            LocalDate fecha,
            String tipoMovimiento,
            BigDecimal valor,
            BigDecimal saldo,
            String uuid,
            Long cuentaId
    );

    @Mapping(target = "uuid", ignore = true)
    void updateFromDto(MovimientoDTO source, @MappingTarget Movimiento target);
}
