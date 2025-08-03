package com.tcs.clients.cliente.mapper;

import com.tcs.clients.cliente.dto.ClienteDTO;
import com.tcs.clients.cliente.model.Cliente;

import java.util.List;

public class ClienteMapper {

    public static ClienteDTO toDTO(Cliente cliente) {
        return ClienteDTO.builder()
            .nombre(cliente.getNombre())
            .genero(cliente.getGenero())
            .edad(cliente.getEdad())
            .identificacion(cliente.getIdentificacion())
            .direccion(cliente.getDireccion())
            .telefono(cliente.getTelefono())
            .clienteId(cliente.getClienteId())
            .contrasena(cliente.getContrasena())
            .estado(cliente.getEstado())
            .build();
    }

    public static List<ClienteDTO> toDTOList(List<Cliente> clientes) {
        return clientes.stream().map(ClienteMapper::toDTO).toList();
    }

    public static Cliente toCliente(ClienteDTO clienteDTO) {
        return Cliente.builder()
            .nombre(clienteDTO.getNombre())
            .genero(clienteDTO.getGenero())
            .edad(clienteDTO.getEdad())
            .identificacion(clienteDTO.getIdentificacion())
            .direccion(clienteDTO.getDireccion())
            .telefono(clienteDTO.getTelefono())
            .clienteId(clienteDTO.getClienteId())
            .contrasena(clienteDTO.getContrasena())
            .estado(clienteDTO.getEstado())
            .build();
    }

}
