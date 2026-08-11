package com.escenaperu.ubicaciones.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CiudadJpaRepository extends JpaRepository<CiudadJpaEntity, Integer> {
    List<CiudadJpaEntity> findByActivaTrueOrderByNombreAsc();
}