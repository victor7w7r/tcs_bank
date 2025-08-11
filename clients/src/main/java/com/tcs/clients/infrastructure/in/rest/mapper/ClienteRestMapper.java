package com.tcs.clients.infrastructure.in.rest.mapper;

import com.tcs.clients.domain.model.Cliente;
import com.tcs.clients.domain.model.StatusAccountReceive;
import com.tcs.clients.infrastructure.in.rest.model.ClienteRequest;
import com.tcs.clients.infrastructure.in.rest.model.ClienteResponse;
import com.tcs.clients.infrastructure.in.rest.model.StatusAccountReceiveRes;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ClienteRestMapper {
  Cliente toCliente(ClienteRequest clienteRequest);
  ClienteResponse toClienteResponse(Cliente cliente);
  List<ClienteResponse> toClienteResponseList(List<Cliente> clienteList);
  StatusAccountReceiveRes toStatusAccountReceiveRes(StatusAccountReceive statusAccountReceive);
  List<StatusAccountReceiveRes> toStatusAccountReceiveResList(
          List<StatusAccountReceive> statusAccountReceiveList
  );
}
