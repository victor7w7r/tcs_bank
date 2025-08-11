package com.tcs.transactions.movimientos.model;

import com.tcs.transactions.cuenta.model.Cuenta;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@Builder(toBuilder = true)
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

    @Column(name = "cuenta_id")
    private Long cuentaId;

}