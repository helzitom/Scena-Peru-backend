package com.escenaperu.feed.domain;

import java.time.LocalDate;
import java.util.UUID;

public class Lanzamiento {
    public enum Tipo { SINGLE, EP, ALBUM, VIDEO }

    private final UUID id;
    private final UUID bandaId;
    private final String titulo;
    private final Tipo tipo;
    private final String urlStreaming;
    private final LocalDate fechaLanzamiento;

    public Lanzamiento(UUID id, UUID bandaId, String titulo, Tipo tipo,
                        String urlStreaming, LocalDate fechaLanzamiento) {
        this.id = id;
        this.bandaId = bandaId;
        this.titulo = titulo;
        this.tipo = tipo;
        this.urlStreaming = urlStreaming;
        this.fechaLanzamiento = fechaLanzamiento;
    }

    public UUID getId() { return id; }
    public UUID getBandaId() { return bandaId; }
    public String getTitulo() { return titulo; }
    public Tipo getTipo() { return tipo; }
    public String getUrlStreaming() { return urlStreaming; }
    public LocalDate getFechaLanzamiento() { return fechaLanzamiento; }
}
