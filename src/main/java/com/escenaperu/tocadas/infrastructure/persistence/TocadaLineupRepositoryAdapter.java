package com.escenaperu.tocadas.infrastructure.persistence;

import com.escenaperu.tocadas.domain.TocadaLineup;
import com.escenaperu.tocadas.domain.TocadaLineupRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public class TocadaLineupRepositoryAdapter implements TocadaLineupRepository {

    private final TocadaLineupJpaRepository jpaRepository;

    public TocadaLineupRepositoryAdapter(TocadaLineupJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public TocadaLineup save(TocadaLineup lineup) {
        var entidad = new TocadaLineupJpaEntity(lineup.getId(), lineup.getTocadaId(), lineup.getBandaId(),
                lineup.getOrdenAparicion(), lineup.getEstadoInvitacion());
        var guardada = jpaRepository.save(entidad);
        return new TocadaLineup(guardada.getId(), guardada.getTocadaId(), guardada.getBandaId(),
                guardada.getOrdenAparicion(), guardada.getEstadoInvitacion());
    }

    @Override
    public List<TocadaLineup> findByTocadaId(UUID tocadaId) {
        return jpaRepository.findByTocadaId(tocadaId).stream()
                .map(e -> new TocadaLineup(e.getId(), e.getTocadaId(), e.getBandaId(),
                        e.getOrdenAparicion(), e.getEstadoInvitacion()))
                .toList();
    }
}
