package com.tcs.clients.cliente.service;

import com.tcs.clients.cliente.mapper.ClienteMapper;
import com.tcs.clients.cliente.dto.ClienteDTO;
import com.tcs.clients.cliente.repository.ClienteRepository;
import com.tcs.clients.persona.model.Persona;
import jakarta.transaction.Transactional;
import org.apache.coyote.BadRequestException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public List<ClienteDTO> getAllClientes() {
        return ClienteMapper.toDTOList(clienteRepository.findAll());
    }

    @Transactional
    public void saveCliente(ClienteDTO cliente) throws BadRequestException {
        final var clienteFound = clienteRepository
                .findByIdentificacion(cliente.getIdentificacion());

        if (clienteFound.isPresent()) {
            throw new BadRequestException("ERROR: Cliente ya existe");
        }

        if (cliente.getClienteId() == null) {
            cliente.setClienteId(UUID.randomUUID().toString());
        }
        cliente.setEstado(true);
        clienteRepository.save(ClienteMapper.toCliente(cliente));
    }

    @RabbitListener(queues = "cilente_cuenta_queue")
    public Long envioClienteRef(String identificacion) {
        final var cliente = clienteRepository.findByIdentificacion(identificacion);
        return cliente.map(Persona::getId).orElse(null);
    }

    @Transactional
    public void updateCliente(ClienteDTO cliente) throws BadRequestException {
        final var clienteFound = clienteRepository
                .findByIdentificacion(cliente.getIdentificacion())
                .orElseThrow(() -> new BadRequestException("ERROR: Cliente no encontrado"))
                .toBuilder()
                .nombre(cliente.getNombre())
                .genero(cliente.getGenero())
                .edad(cliente.getEdad())
                .direccion(cliente.getDireccion())
                .telefono(cliente.getTelefono())
                .contrasena(cliente.getContrasena())
                .estado(cliente.getEstado())
                .build();

        clienteRepository.save(clienteFound);
    }

    @Transactional
    public void deleteCliente(String identificacion) throws BadRequestException {
        final var clienteFound = clienteRepository.findByIdentificacion(identificacion)
                .orElseThrow(() -> new BadRequestException("ERROR: Cliente no encontrado"));

        rabbitTemplate.setReplyTimeout(2000);

        final var response = (Long) rabbitTemplate.convertSendAndReceive(
                "borrar_cliente", clienteFound.getId()
        );

        if (response == null) {
            throw new BadRequestException("ERROR: No se pudo eliminar el cliente");
        }

        clienteRepository.deleteByIdentificacion(identificacion);
    }

}
