package com.escenaperu.auth.infrastructure.persistence;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "refresh_tokens")
public class RefreshTokenJpaEntity {

    @Id
    private UUID id;

    @Column(name = "usuario_id", nullable = false)
    private UUID usuarioId;

    @Column(name = "token_hash", nullable = false, unique = true)
    private String tokenHash;

    @Column(name = "expira_en", nullable = false)
    private Instant expiraEn;

    private boolean revocado;

    @Column(name = "created_at")
    private Instant createdAt;

    protected RefreshTokenJpaEntity() { }

    public RefreshTokenJpaEntity(UUID id, UUID usuarioId, String tokenHash, Instant expiraEn,
                                 boolean revocado, Instant createdAt) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.tokenHash = tokenHash;
        this.expiraEn = expiraEn;
        this.revocado = revocado;
        this.createdAt = createdAt;
    }

    public UUID getId() { return id; }
    public UUID getUsuarioId() { return usuarioId; }
    public String getTokenHash() { return tokenHash; }
    public Instant getExpiraEn() { return expiraEn; }
    public boolean isRevocado() { return revocado; }
    public Instant getCreatedAt() { return createdAt; }
}