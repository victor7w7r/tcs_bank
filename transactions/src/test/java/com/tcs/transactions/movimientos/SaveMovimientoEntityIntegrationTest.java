package com.tcs.transactions.movimientos;

import com.tcs.transactions.cuenta.domain.model.Cuenta;
import com.tcs.transactions.cuenta.infrastructure.out.persistence.CuentaPersistenceAdapter;
import com.tcs.transactions.cuenta.infrastructure.out.persistence.entity.CuentaEntity;
import com.tcs.transactions.cuenta.infrastructure.out.persistence.repository.CuentaRepository;
import com.tcs.transactions.movimiento.application.port.in.MovimientoUseCase;
import com.tcs.transactions.movimiento.application.port.out.MovimientoPort;
import com.tcs.transactions.movimiento.infrastructure.in.rest.MovimientoRestAdapter;
import com.tcs.transactions.movimiento.infrastructure.out.persistence.MovimientoPersistenceAdapter;
import com.tcs.transactions.movimiento.infrastructure.out.persistence.entity.MovimientoEntity;
import com.tcs.transactions.movimiento.infrastructure.out.persistence.repository.MovimientoRepository;
import com.tcs.transactions.movimiento.application.service.MovimientoService;
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
public class SaveMovimientoEntityIntegrationTest {

  private static final String numCuenta = "5578664578";

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private MovimientoService movimientoService;

  @Autowired
  private CuentaPersistenceAdapter cuentaPersistenceAdapter;

  @Test
  void saveMovimientoTestAndVerifyValor() throws Exception {

    cuentaPersistenceAdapter.saveOnly(
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

    final var movimiento = movimientoService.findAll().getFirst();

    assertEquals(BigDecimal.valueOf(1500L), movimiento.getSaldo());
  }

}
