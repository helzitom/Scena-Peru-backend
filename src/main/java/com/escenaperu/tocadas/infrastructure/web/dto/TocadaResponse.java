package com.escenaperu.tocadas.infrastructure.web.dto;

import com.escenaperu.tocadas.domain.EstadoTocada;
import com.escenaperu.tocadas.domain.Tocada;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public record TocadaResponse(
        UUID id, String titulo, Integer ciudadId, LocalDate fecha,
        LocalTime horaInicio, EstadoTocada estado, String imagenFlyerUrl
) {
    public static TocadaResponse desde(Tocada t) {
        return new TocadaResponse(t.getId(), t.getTitulo(), t.getCiudadId(), t.getFecha(),
                t.getHoraInicio(), t.getEstado(), t.getImagenFlyerUrl());
    }
}