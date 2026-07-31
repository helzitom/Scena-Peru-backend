package com.escenaperu.tocadas.infrastructure.persistence;

import com.escenaperu.tocadas.domain.Tocada;

public final class TocadaMapper {

    private TocadaMapper() { }

    public static Tocada toDomain(TocadaJpaEntity e) {
        return new Tocada(e.getId(), e.getTitulo(), e.getDescripcion(), e.getCiudadId(), e.getVenueId(),
                e.getUbicacionManual(), e.getFecha(), e.getHoraInicio(), e.getCreadorTipo(), e.getCreadorId(),
                e.getEstado(), e.getPrecioEntrada(), e.getLinkEntradas(), e.getImagenFlyerUrl(), e.getCreatedAt());
    }

    public static TocadaJpaEntity toEntity(Tocada t) {
        return new TocadaJpaEntity(t.getId(), t.getTitulo(), t.getDescripcion(), t.getCiudadId(), t.getVenueId(),
                t.getUbicacionManual(), t.getFecha(), t.getHoraInicio(), t.getCreadorTipo(), t.getCreadorId(),
                t.getEstado(), t.getPrecioEntrada(), t.getLinkEntradas(), t.getImagenFlyerUrl(), t.getCreatedAt());
    }
}
