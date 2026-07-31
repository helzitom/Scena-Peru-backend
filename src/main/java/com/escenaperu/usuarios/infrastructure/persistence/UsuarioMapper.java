package com.escenaperu.usuarios.infrastructure.persistence;

import com.escenaperu.usuarios.domain.Usuario;

public final class UsuarioMapper {

    private UsuarioMapper() { }

    public static Usuario toDomain(UsuarioJpaEntity e) {
        return new Usuario(e.getId(), e.getEmail(), e.getPasswordHash(), e.getTipo(),
                e.getCiudadId(), e.getNombreDisplay(), e.getFotoPerfilUrl(),
                e.isVerificado(), e.getCreatedAt());
    }

    public static UsuarioJpaEntity toEntity(Usuario u) {
        return new UsuarioJpaEntity(u.getId(), u.getEmail(), u.getPasswordHash(), u.getTipo(),
                u.getCiudadId(), u.getNombreDisplay(), u.getFotoPerfilUrl(),
                u.isVerificado(), u.getCreatedAt());
    }
}
