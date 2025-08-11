package com.tcs.transactions.cuenta.infrastructure.out.persistence.entity;

import com.tcs.transactions.movimiento.infrastructure.out.persistence.entity.MovimientoEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CuentaEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true)
  private Long numCuenta;
  private String tipoCuenta;
  private BigDecimal saldoInicial;
  private Boolean estado;
  private Long clienteRef;

  //TODO: Change to LAZY
  @OneToMany(
          fetch = FetchType.EAGER,
          cascade = CascadeType.REMOVE,
          orphanRemoval = true)
  @JoinColumn(name = "cuenta_id")
  private List<MovimientoEntity> movimientoEntities;
}