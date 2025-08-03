package com.tcs.transactions.cuenta.model;

import com.tcs.transactions.movimientos.model.Movimiento;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Builder(toBuilder = true)
@Data
@Getter
@Setter
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

    @Column(unique = true)
    private Long clienteRef;

    @OneToMany(
            fetch = FetchType.EAGER,
            mappedBy = "cuenta",
            cascade = CascadeType.REMOVE,
            orphanRemoval = true)
    private List<Movimiento> movimientos;
}