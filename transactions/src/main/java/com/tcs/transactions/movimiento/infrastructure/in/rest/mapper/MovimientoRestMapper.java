package com.tcs.transactions.movimiento.infrastructure.in.rest.mapper;

import com.tcs.transactions.movimiento.domain.model.Movimiento;
import com.tcs.transactions.movimiento.infrastructure.in.rest.model.MovimientoRequest;
import com.tcs.transactions.movimiento.infrastructure.in.rest.model.MovimientoResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface MovimientoRestMapper {
  Movimiento toMovimiento(MovimientoRequest cuentaRequest);
  MovimientoResponse toMovimientoResponse(Movimiento cuenta);
  List<MovimientoResponse> toMovimientoResponseList(List<Movimiento> cuentaList);
}
