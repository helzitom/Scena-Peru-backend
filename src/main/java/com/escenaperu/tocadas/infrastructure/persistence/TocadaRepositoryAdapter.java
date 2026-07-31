package com.escenaperu.tocadas.infrastructure.persistence;

import com.escenaperu.tocadas.domain.Tocada;
import com.escenaperu.tocadas.domain.TocadaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class TocadaRepositoryAdapter implements TocadaRepository {

    private final TocadaJpaRepository jpaRepository;

    public TocadaRepositoryAdapter(TocadaJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Tocada save(Tocada tocada) {
        return TocadaMapper.toDomain(jpaRepository.save(TocadaMapper.toEntity(tocada)));
    }

    @Override
    public Optional<Tocada> findById(UUID id) {
        return jpaRepository.findById(id).map(TocadaMapper::toDomain);
    }

    @Override
    public List<Tocada> findByCiudadAndFechaDesde(Integer ciudadId, LocalDate desde) {
        return jpaRepository.buscarPorCiudadDesde(ciudadId, desde).stream()
                .map(TocadaMapper::toDomain).toList();
    }
}
