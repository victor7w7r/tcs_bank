package com.tcs.clients.infrastructure.out.persistence.mapper;

import com.tcs.clients.domain.model.Cliente;
import com.tcs.clients.infrastructure.out.persistence.entity.ClienteEntity;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClientPersistenceMapper {

  ClienteEntity toClienteEntity(Cliente cliente);

  @Mapping(target = "id", ignore = true)
  Cliente toCliente(ClienteEntity entity);
  List<Cliente> toClienteList(List<ClienteEntity> entityList);

  @BeanMapping(ignoreByDefault = true)
  @Mapping(source = "nombre", target = "nombre")
  @Mapping(source = "genero", target = "genero")
  @Mapping(source = "edad", target = "edad")
  @Mapping(source = "direccion", target = "direccion")
  @Mapping(source = "telefono", target = "telefono")
  @Mapping(source = "contrasena", target = "contrasena")
  @Mapping(source = "estado", target = "estado")
  void update(Cliente source, @MappingTarget ClienteEntity target);

}
