package com.tcs.transactions.cuenta.infrastructure.in.rest.mapper;

import com.tcs.transactions.cuenta.domain.model.Cuenta;
import com.tcs.transactions.cuenta.infrastructure.in.rest.model.CuentaRequest;
import com.tcs.transactions.cuenta.infrastructure.in.rest.model.CuentaResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface CuentaRestMapper {
  Cuenta toCuenta(CuentaRequest cuentaRequest);
  CuentaResponse toCuentaResponse(Cuenta cuenta);
  List<CuentaResponse> toCuentaResponseList(List<Cuenta> cuentaList);
}
