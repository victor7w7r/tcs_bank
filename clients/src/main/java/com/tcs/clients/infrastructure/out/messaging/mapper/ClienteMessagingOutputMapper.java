package com.tcs.clients.infrastructure.out.messaging.mapper;

import com.tcs.clients.infrastructure.out.messaging.entity.StatusAccountSendReq;
import org.mapstruct.Mapper;

import java.time.LocalDate;

@Mapper(componentModel = "spring")
public interface ClienteMessagingOutputMapper {

  StatusAccountSendReq toStatusAccountSendReq(
          LocalDate fechaInicio,
          LocalDate fechaFin,
          Long clienteRef,
          String nombreCliente
  );

}
