package com.tcs.transactions.cuenta.model;

import com.tcs.transactions.movimientos.model.Movimiento;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Cuenta {
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  private Long id;

  @Column(unique = true)
  private Long numCuenta;
  private String tipoCuenta;
  private BigDecimal saldoInicial;
  private Boolean estado;
  private Long clienteRef;

  @OneToMany(
          fetch = FetchType.LAZY,
          cascade = CascadeType.REMOVE,
          orphanRemoval = true)
  @JoinColumn(name = "cuenta_id")
  private List<Movimiento> movimientos;
}