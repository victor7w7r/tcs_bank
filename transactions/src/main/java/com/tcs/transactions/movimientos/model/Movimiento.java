package com.tcs.transactions.movimientos.model;

import com.tcs.transactions.cuenta.model.Cuenta;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@Builder(toBuilder = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Movimiento {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private LocalDate fecha;
    private String tipoMovimiento;

    @NonNull
    private BigDecimal valor;
    private BigDecimal saldo;
    private String uuid;

    @ManyToOne
    @JoinColumn(name = "movimiento_cuenta")
    private Cuenta cuenta;
}