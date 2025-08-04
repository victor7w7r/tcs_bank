package com.tcs.transactions.movimientos;

import com.tcs.transactions.cuenta.model.Cuenta;
import com.tcs.transactions.cuenta.repository.CuentaRepository;
import com.tcs.transactions.movimientos.repository.MovimientoRepository;
import com.tcs.transactions.movimientos.service.MovimientoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc()
@ActiveProfiles("test")
public class SaveMovimientoIntegrationTest {

  private static final String numCuenta = "5578664578";

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private MovimientoRepository movimientoRepository;

  @Autowired
  private CuentaRepository cuentaRepository;

  @Autowired
  private MovimientoService movimientoService;

  @Test
  void saveMovimientoTestAndVerifyValor() throws Exception {

    cuentaRepository.save(
            Cuenta.builder()
                    .numCuenta(Long.valueOf(numCuenta))
                    .tipoCuenta("Ahorros")
                    .saldoInicial(new java.math.BigDecimal(500))
                    .estado(true)
                    .build()
    );

    mockMvc
            .perform(post("/movimientos/{numCuenta}", numCuenta).content(
                    """
                            {
                              "valor": 1000
                            }
                            """
            ));

    final var movimiento = movimientoService.getAllMovimientos().getFirst();

    assertEquals(BigDecimal.valueOf(1500L), movimiento.getSaldo());
  }

}
