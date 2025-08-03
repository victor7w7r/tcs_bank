package com.tcs.clients.cliente.model;

import com.tcs.clients.persona.model.Persona;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Cliente extends Persona {

    @Column(unique = true)
    private String clienteId;

    private String contrasena;
    private Boolean estado;
}