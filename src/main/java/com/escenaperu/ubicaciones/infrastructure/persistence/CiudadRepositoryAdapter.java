package com.escenaperu.ubicaciones.infrastructure.persistence;

import com.escenaperu.ubicaciones.domain.Ciudad;
import com.escenaperu.ubicaciones.domain.CiudadRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CiudadRepositoryAdapter implements CiudadRepository {

    private final CiudadJpaRepository jpaRepository;

    public CiudadRepositoryAdapter(CiudadJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public List<Ciudad> findAllActivas() {
        return jpaRepository.findByActivaTrueOrderByNombreAsc().stream()
                .map(e -> new Ciudad(e.getId(), e.getNombre(), e.getDepartamento(), e.isActiva()))
                .toList();
    }
}