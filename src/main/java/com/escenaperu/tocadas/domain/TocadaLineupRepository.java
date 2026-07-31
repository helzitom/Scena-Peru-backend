package com.escenaperu.tocadas.domain;

import java.util.List;
import java.util.UUID;

public interface TocadaLineupRepository {
    TocadaLineup save(TocadaLineup lineup);
    List<TocadaLineup> findByTocadaId(UUID tocadaId);
}
