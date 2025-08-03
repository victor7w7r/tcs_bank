package com.tcs.clients.cliente.dto;

import com.tcs.clients.persona.dto.PersonaDTO;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDTO extends PersonaDTO {

    private String clienteId;
    private String contrasena;
    private Boolean estado;
}
