package com.escenaperu.tocadas.domain;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TocadaRepository {
    Tocada save(Tocada tocada);
    Optional<Tocada> findById(UUID id);
    List<Tocada> findByCiudadAndFechaDesde(Integer ciudadId, LocalDate desde);
}
