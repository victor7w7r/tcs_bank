package com.tcs.clients.infrastructure.out.persistence.repository;

import com.tcs.clients.infrastructure.out.persistence.entity.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<ClienteEntity, Long> {
    Optional<ClienteEntity> findByIdentificacion(String identificacion);
    void deleteByIdentificacion(String identificacion);
}
