package com.tcs.transactions.movimiento.infrastructure.out.persistence.repository;

import com.tcs.transactions.movimiento.infrastructure.out.persistence.entity.MovimientoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MovimientoRepository extends JpaRepository<MovimientoEntity, Long> {
    Optional<MovimientoEntity> findByUuid(String uuid);
    void deleteByUuid(String uuid);
}
