package com.escenaperu.notificaciones.domain;

import java.time.Instant;
import java.util.UUID;

public class Notificacion {

    private final UUID id;
    private final Integer ciudadId;
    private final TipoNotificacion tipo;
    private final UUID referenciaId;
    private final String contenido;
    private final boolean leido;
    private final Instant createdAt;

    public Notificacion(UUID id, Integer ciudadId, TipoNotificacion tipo, UUID referenciaId,
                         String contenido, boolean leido, Instant createdAt) {
        this.id = id;
        this.ciudadId = ciudadId;
        this.tipo = tipo;
        this.referenciaId = referenciaId;
        this.contenido = contenido;
        this.leido = leido;
        this.createdAt = createdAt;
    }

    public static Notificacion nuevaTocada(Integer ciudadId, UUID tocadaId, String titulo) {
        return new Notificacion(UUID.randomUUID(), ciudadId, TipoNotificacion.NUEVA_TOCADA,
                tocadaId, "Nueva tocada: " + titulo, false, Instant.now());
    }

    public UUID getId() { return id; }
    public Integer getCiudadId() { return ciudadId; }
    public TipoNotificacion getTipo() { return tipo; }
    public UUID getReferenciaId() { return referenciaId; }
    public String getContenido() { return contenido; }
    public boolean isLeido() { return leido; }
    public Instant getCreatedAt() { return createdAt; }
}
