package com.tcs.transactions.movimientos.mapper;

import com.tcs.transactions.movimientos.dto.MovimientoDTO;
import com.tcs.transactions.movimientos.model.Movimiento;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class MovimientoMapper {

    public static MovimientoDTO toDTO(Movimiento movimiento) {
        return MovimientoDTO.builder()
                .fecha(movimiento.getFecha())
                .tipoMovimiento(movimiento.getTipoMovimiento())
                .valor(movimiento.getValor())
                .saldo(movimiento.getSaldo())
                .uuid(movimiento.getUuid())
                .build();
    }

    public static List<MovimientoDTO> toDTOList(List<Movimiento> movimientos) {
        return movimientos.stream().map(MovimientoMapper::toDTO).toList();
    }

    public static Movimiento toMovimiento(MovimientoDTO movimientoDTO) {
        return Movimiento.builder()
                .fecha(movimientoDTO.getFecha())
                .tipoMovimiento(movimientoDTO.getTipoMovimiento())
                .valor(movimientoDTO.getValor())
                .saldo(movimientoDTO.getSaldo())
                .uuid(movimientoDTO.getUuid())
                .build();
    }

}
