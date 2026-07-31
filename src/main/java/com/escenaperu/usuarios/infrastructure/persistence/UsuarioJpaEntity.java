package com.escenaperu.usuarios.infrastructure.persistence;

import com.escenaperu.usuarios.domain.TipoUsuario;
import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "usuarios")
public class UsuarioJpaEntity {

    @Id
    private UUID id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoUsuario tipo;

    @Column(name = "ciudad_id")
    private Integer ciudadId;

    @Column(name = "nombre_display", nullable = false)
    private String nombreDisplay;

    @Column(name = "foto_perfil_url")
    private String fotoPerfilUrl;

    private boolean verificado;

    @Column(name = "created_at")
    private Instant createdAt;

    protected UsuarioJpaEntity() { }

    public UsuarioJpaEntity(UUID id, String email, String passwordHash, TipoUsuario tipo,
                             Integer ciudadId, String nombreDisplay, String fotoPerfilUrl,
                             boolean verificado, Instant createdAt) {
        this.id = id;
        this.email = email;
        this.passwordHash = passwordHash;
        this.tipo = tipo;
        this.ciudadId = ciudadId;
        this.nombreDisplay = nombreDisplay;
        this.fotoPerfilUrl = fotoPerfilUrl;
        this.verificado = verificado;
        this.createdAt = createdAt;
    }

    public UUID getId() { return id; }
    public String getEmail() { return email; }
    public String getPasswordHash() { return passwordHash; }
    public TipoUsuario getTipo() { return tipo; }
    public Integer getCiudadId() { return ciudadId; }
    public String getNombreDisplay() { return nombreDisplay; }
    public String getFotoPerfilUrl() { return fotoPerfilUrl; }
    public boolean isVerificado() { return verificado; }
    public Instant getCreatedAt() { return createdAt; }
}
