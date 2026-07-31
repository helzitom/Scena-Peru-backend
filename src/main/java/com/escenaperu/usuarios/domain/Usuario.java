package com.escenaperu.usuarios.domain;

import java.time.Instant;
import java.util.UUID;

/**
 * Modelo de dominio puro: sin anotaciones de JPA ni de Spring.
 * La capa infrastructure lo mapea a/desde su propia entidad JPA.
 */
public class Usuario {

    private final UUID id;
    private final String email;
    private final String passwordHash;
    private final TipoUsuario tipo;
    private final Integer ciudadId;
    private final String nombreDisplay;
    private final String fotoPerfilUrl;
    private final boolean verificado;
    private final Instant createdAt;

    public Usuario(UUID id, String email, String passwordHash, TipoUsuario tipo,
                    Integer ciudadId, String nombreDisplay, String fotoPerfilUrl,
                    boolean verificado, Instant createdAt) {
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Email invalido");
        }
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

    public static Usuario registrar(String email, String passwordHash, TipoUsuario tipo,
                                     Integer ciudadId, String nombreDisplay) {
        return new Usuario(UUID.randomUUID(), email, passwordHash, tipo, ciudadId,
                nombreDisplay, null, false, Instant.now());
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
