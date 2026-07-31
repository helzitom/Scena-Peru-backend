package com.escenaperu.tocadas.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface TocadaLineupJpaRepository extends JpaRepository<TocadaLineupJpaEntity, Long> {
    List<TocadaLineupJpaEntity> findByTocadaId(UUID tocadaId);
}
