package com.tcs.clients.infrastructure.in.rest.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClienteRequest {

  @NotBlank(message = "El nombre no puede estar vacío")
  private String nombre;

  @NotBlank(message = "El género no puede estar vacío")
  private String genero;

  @NotNull
  @Min(1)
  private Integer edad;

  @NotBlank(message = "La identificación no puede estar vacía")
  private String identificacion;

  @NotBlank(message = "La dirección no puede estar vacía")
  private String direccion;

  @NotBlank(message = "El teléfono no puede estar vacío")
  private String telefono;

  @NotBlank(message = "La contraseña no puede estar vacía")
  private String contrasena;
  private Boolean estado;

}
