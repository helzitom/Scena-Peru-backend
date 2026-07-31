package com.escenaperu.recuerdos.domain;

import java.time.Instant;
import java.util.UUID;

public class RecuerdoFoto {
    private final UUID id;
    private final UUID tocadaId;
    private final UUID usuarioId;
    private final String fotoUrl;
    private final String caption;
    private final Instant createdAt;

    public RecuerdoFoto(UUID id, UUID tocadaId, UUID usuarioId, String fotoUrl,
                         String caption, Instant createdAt) {
        this.id = id;
        this.tocadaId = tocadaId;
        this.usuarioId = usuarioId;
        this.fotoUrl = fotoUrl;
        this.caption = caption;
        this.createdAt = createdAt;
    }

    public UUID getId() { return id; }
    public UUID getTocadaId() { return tocadaId; }
    public UUID getUsuarioId() { return usuarioId; }
    public String getFotoUrl() { return fotoUrl; }
    public String getCaption() { return caption; }
    public Instant getCreatedAt() { return createdAt; }
}
