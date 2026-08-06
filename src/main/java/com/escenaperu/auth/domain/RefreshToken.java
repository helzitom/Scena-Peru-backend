package com.escenaperu.auth.domain;

import java.time.Instant;
import java.util.UUID;

public class RefreshToken {
    private final UUID id;
    private final UUID usuarioId;
    private final String tokenHash;
    private final Instant expiraEn;
    private final boolean revocado;
    private final Instant createdAt;

    public RefreshToken(UUID id, UUID usuarioId, String tokenHash, Instant expiraEn,
                        boolean revocado, Instant createdAt) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.tokenHash = tokenHash;
        this.expiraEn = expiraEn;
        this.revocado = revocado;
        this.createdAt = createdAt;
    }

    public static RefreshToken emitir(UUID usuarioId, String tokenHash, Instant expiraEn) {
        return new RefreshToken(UUID.randomUUID(), usuarioId, tokenHash, expiraEn, false, Instant.now());
    }

    public boolean esValido() {
        return !revocado && expiraEn.isAfter(Instant.now());
    }

    public UUID getId() { return id; }
    public UUID getUsuarioId() { return usuarioId; }
    public String getTokenHash() { return tokenHash; }
    public Instant getExpiraEn() { return expiraEn; }
    public boolean isRevocado() { return revocado; }
    public Instant getCreatedAt() { return createdAt; }
}